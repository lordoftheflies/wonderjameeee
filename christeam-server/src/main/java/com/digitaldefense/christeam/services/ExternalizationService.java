/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.services;

import com.digitaldefense.christeam.dal.AccountRepository;
import com.digitaldefense.christeam.dal.NetworkTreeRepository;
import com.digitaldefense.christeam.entities.AccountEntity;
import com.digitaldefense.christeam.entities.NetworkNodeEntity;
import java.util.UUID;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lordoftheflies
 */
@RequestMapping(path = "/data-seed",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ExternalizationService {

    @Autowired
    private NetworkTreeRepository networkTreeRepository;
    @Autowired
    private AccountRepository accountRepository;

    private void buildTree(NetworkDto dto, NetworkNodeEntity parentEntity) {
        AccountEntity account = accountRepository.save(new AccountEntity(dto.getContact().getEmail(), dto.getContact().getName()));
        NetworkNodeEntity networkNodeEntity = new NetworkNodeEntity(account, parentEntity);
        account.setNode(networkTreeRepository.save(networkNodeEntity));
        accountRepository.save(account);
        dto.getChildren().forEach(n -> buildTree(n, networkNodeEntity));
    }

    private NetworkDto buildTree(NetworkNodeEntity entity, NetworkDto parentDto) {
        AccountEntity account = accountRepository.findByNetwork(entity.getId());

        NetworkDto dto = new NetworkDto(new ContactDto(account.getId(), account.getName(), account.getEmail()));
        if (parentDto != null) {
            parentDto.getChildren().add(dto);
        }

        networkTreeRepository.findChildren(account.getId()).forEach(n -> buildTree(n, dto));
        return dto;
    }

    @RequestMapping(path = "/import",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public void importJson(@RequestParam(value = "accountId", required = false) String accountId, @RequestBody ChristeamDto dto) {
        this.buildTree(dto.getNetwork(), null);
    }

    @RequestMapping(path = "/{accountId}/export",
            method = RequestMethod.GET)
    public ChristeamDto exportJson(@PathVariable("accountId") String accountId) throws AccountNotExistException {
        ChristeamDto dto = new ChristeamDto();

        final UUID id = UUID.fromString(accountId);
        if (accountRepository.exists(id)) {
            dto.setNetwork(this.buildTree(networkTreeRepository.findByAccount(id), null));
        } else {
            throw new AccountNotExistException(accountId);
        }
        return dto;

    }
}
