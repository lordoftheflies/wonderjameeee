/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.services;

import hu.cherubits.wonderjam.model.NetworkDto;
import hu.cherubits.wonderjam.exceptions.AccountNotExistException;
import hu.cherubits.wonderjam.model.ContactDto;
import hu.cherubits.wonderjam.model.ChristeamDto;
import hu.cherubits.wonderjam.dal.AccountRepository;
import hu.cherubits.wonderjam.dal.NetworkTreeRepository;
import hu.cherubits.wonderjam.entities.AccountEntity;
import hu.cherubits.wonderjam.entities.NetworkNodeEntity;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lordoftheflies
 */
@RequestMapping(path = "/data-seed",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ExternalizationService {

    private static final Logger LOG = Logger.getLogger(ExternalizationService.class.getName());
    private static final String PH = "================================================================================================";

    @Autowired
    private NetworkTreeRepository networkTreeRepository;
    @Autowired
    private AccountRepository accountRepository;

    private NetworkDto buildTree(NetworkDto dto, NetworkNodeEntity parentEntity) {
        LOG.log(Level.INFO, "{0} account ...", ((dto.getId() == null) ? "Create new" : "Update"));
        if (dto.getId() == null) {
            LOG.log(Level.INFO, "\tNetwork node-id: {0}", dto.getId());
        }
        LOG.log(Level.INFO, "\tEmail: {0}", dto.getContact().getEmail());
        LOG.log(Level.INFO, "\tName: {0}", dto.getContact().getName());

// Create account entity.
        AccountEntity account = accountRepository.save(new AccountEntity(dto.getContact().getEmail(), dto.getContact().getName()));
        account = accountRepository.save(account);
        final NetworkNodeEntity networkNodeEntity = networkTreeRepository.save(new NetworkNodeEntity(account, parentEntity));
        account.setNode(networkNodeEntity);
        account = accountRepository.save(account);
        LOG.log(Level.INFO, "Account {0} ...", ((dto.getId() == null) ? "created" : "updated"));
        LOG.log(Level.INFO, "\tNetwork node-id: {0}", networkNodeEntity.getId());
        LOG.log(Level.INFO, "\tAccount id: {0}", account.getId());

        LOG.log(Level.INFO, "Create children of {0}", dto.getContact().getEmail());
        dto.getChildren().forEach(n -> buildTree(n, networkNodeEntity));
        return dto;
    }

    private NetworkDto buildTree(NetworkNodeEntity entity, NetworkDto parentDto) {
        AccountEntity account = accountRepository.findByNetwork(entity.getId());
        LOG.log(Level.INFO, "Fetch account ...");
        LOG.log(Level.INFO, "\tNetwork node-id: {0}", entity.getId());
        LOG.log(Level.INFO, "\tAccount id: {0}", account.getId());
        LOG.log(Level.INFO, "\tEmail: {0}", account.getEmail());
        LOG.log(Level.INFO, "\tName: {0}", account.getName());
        

        NetworkDto dto = new NetworkDto(new ContactDto(account.getId(), null, account.getName(), account.getEmail(), 0, null, null));
        dto.setId(entity.getId());

        LOG.log(Level.INFO, "Fetch children of {0}", account.getEmail());
        dto.setChildren(networkTreeRepository.findChildren(account.getId()).stream()
                .map((NetworkNodeEntity n) -> buildTree(n, dto))
                .collect(Collectors.toList()));

        return dto;
    }

    @RequestMapping(path = "/import",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public void importJson(@RequestBody ChristeamDto dto) {
        LOG.info("IMPORT JSON" + PH);
        this.buildTree(dto.getNetwork(), null);
        LOG.info("IMPORT EXECUTED" + PH);
    }

    @RequestMapping(path = "/{accountId}/export",
            method = RequestMethod.GET)
    public ChristeamDto exportJson(@PathVariable("accountId") String accountId) throws AccountNotExistException {
        LOG.info("EXPORT JSON" + PH);
        ChristeamDto dto = new ChristeamDto();

        UUID id = UUID.fromString(accountId);
        if (accountRepository.exists(id)) {
            dto.setNetwork(this.buildTree(networkTreeRepository.findByAccount(id), null));
        } else {
            throw new AccountNotExistException(accountId);
        }
        LOG.info("EXPORT EXECUTED" + PH);

        return dto;

    }
}
