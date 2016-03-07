/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.services;

import com.digitaldefense.christeam.model.ContactDto;
import com.digitaldefense.christeam.exceptions.AccountNotExistException;
import com.digitaldefense.christeam.dal.AccountRepository;
import com.digitaldefense.christeam.dal.MailBoxRepository;
import com.digitaldefense.christeam.dal.NetworkTreeRepository;
import com.digitaldefense.christeam.entities.AccountEntity;
import com.digitaldefense.christeam.entities.MailBoxEntity;
import com.digitaldefense.christeam.entities.NetworkNodeEntity;
import com.digitaldefense.christeam.model.TransactionDto;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lordoftheflies
 */
@RequestMapping(path = "/addressbook",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AddressBookService {

    @Autowired
    private NetworkTreeRepository networkTreeRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MailBoxRepository mbRepository;

    @RequestMapping(path = "/children",
            method = RequestMethod.GET)
    public List<ContactDto> getChildren(@RequestParam(name = "parentId", required = true) String parentId) throws AccountNotExistException {
        final UUID id = UUID.fromString(parentId);
        if (accountRepository.exists(id)) {
            return networkTreeRepository.findContactsOfChildNodes(id).stream()
                    .map((AccountEntity a) -> new ContactDto(a.getId(), a.getName(), a.getEmail()))
                    .collect(Collectors.toList());
        } else {
            throw new AccountNotExistException(parentId);
        }
    }

    @RequestMapping(path = "/save",
            method = RequestMethod.POST)
    public void save(ContactDto dto) throws AccountNotExistException {
        if (!accountRepository.exists(dto.getId())) {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setEmail(dto.getEmail());
            accountEntity = accountRepository.save(accountEntity);

            NetworkNodeEntity nodeEntity = new NetworkNodeEntity();
            nodeEntity.setActive(true);
            nodeEntity.setContact(accountEntity);
            networkTreeRepository.save(nodeEntity);

            MailBoxEntity mbEntity = new MailBoxEntity();
            mbEntity.setOwner(nodeEntity);
            mbRepository.save(mbEntity);
        }
    }

    @RequestMapping(path = "/send",
            method = RequestMethod.POST)
    public void sendCodes(TransactionDto dto) throws InsufficientCodesException {
        if (accountRepository.exists(dto.getFrom()) && accountRepository.exists(dto.getTo())) {
            NetworkNodeEntity source = networkTreeRepository.findByAccount(dto.getFrom());
            if (source.getCodes() < dto.getCount() && !networkTreeRepository.isRoot(dto.getFrom())) {
                throw new InsufficientCodesException();
            } else {
                NetworkNodeEntity destination = networkTreeRepository.findByAccount(dto.getTo());

                source.setCodes(source.getCodes() - dto.getCount());
                networkTreeRepository.save(source);

                destination.setCodes(destination.getCodes() + dto.getCount());
                networkTreeRepository.save(destination);
            }
        }
    }
}
