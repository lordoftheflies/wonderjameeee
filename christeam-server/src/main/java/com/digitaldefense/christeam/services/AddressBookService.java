/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.services;

import com.digitaldefense.christeam.model.ContactInfoDto;
import com.digitaldefense.christeam.exceptions.InsufficientCodesException;
import com.digitaldefense.christeam.model.ContactDto;
import com.digitaldefense.christeam.exceptions.AccountNotExistException;
import com.digitaldefense.christeam.dal.AccountRepository;
import com.digitaldefense.christeam.dal.MailBoxRepository;
import com.digitaldefense.christeam.dal.NetworkTreeRepository;
import com.digitaldefense.christeam.entities.AccountEntity;
import com.digitaldefense.christeam.entities.MailBoxEntity;
import com.digitaldefense.christeam.entities.NetworkNodeEntity;
import com.digitaldefense.christeam.model.TransactionDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    private static final Logger LOG = Logger.getLogger(AddressBookService.class.getName());

    @Autowired
    private NetworkTreeRepository networkTreeRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MailBoxRepository mbRepository;

    @RequestMapping(path = "/{id}/contacts",
            method = RequestMethod.GET)
    public List<ContactInfoDto> getContactsOfMember(@PathVariable("id") String accountId) {
        LOG.log(Level.INFO, "Retrieve contacts of account[{0}] ...", accountId);
        return accountRepository.findAll().stream()
                .map((AccountEntity a) -> new ContactInfoDto(
                        a.getId(),
                        a.getName(),
                        a.getEmail()))
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/roots",
            method = RequestMethod.GET)
    public List<ContactDto> getRoots() {
        return accountRepository.findRootAccounts().stream()
                .map((AccountEntity a) -> new ContactDto(
                        a.getId(),
                        (networkTreeRepository.isRoot(a.getId())) ? null : accountRepository.getParent(a.getId()).getId(),
                        a.getName(),
                        a.getEmail(),
                        nullSafeGet(networkTreeRepository.findByAccount(a.getId()).getCodes()),
                        a.getPhone()))
                .collect(Collectors.toList());
    }

    private int nullSafeGet(Integer i) {
        return (i == null) ? 0 : i;
    }

    @RequestMapping(path = "/all",
            method = RequestMethod.GET)
    public List<ContactDto> getAll() {
        return accountRepository.findAll().stream()
                .map((AccountEntity a) -> new ContactDto(
                        a.getId(),
                        (networkTreeRepository.isRoot(a.getId())) ? null : accountRepository.getParent(a.getId()).getId(),
                        a.getName(),
                        a.getEmail(),
                        nullSafeGet(networkTreeRepository.findByAccount(a.getId()).getCodes()),
                        a.getPhone()))
                .collect(Collectors.toList());
    }

    private List<ContactDto> retrieveParent(AccountEntity a, List<ContactDto> accumulator) {
        accumulator.add(new ContactDto(
                a.getId(),
                (networkTreeRepository.isRoot(a.getId())) ? null : accountRepository.getParent(a.getId()).getId(),
                a.getName(),
                a.getEmail(),
                nullSafeGet(networkTreeRepository.findByAccount(a.getId()).getCodes()),
                a.getPhone()));
        if (networkTreeRepository.isRoot(a.getId())) {
            return accumulator;
        } else {
            return retrieveParent(accountRepository.getParent(a.getId()), accumulator);
        }
    }

    @RequestMapping(path = "/genocide",
            method = RequestMethod.GET)
    public List<ContactDto> getParents(@RequestParam(name = "childId", required = true) String childId) {
        if (ROOT_KEY.equals(childId)) {
            return new ArrayList<>();
        } else {

            final List<ContactDto> results = this.retrieveParent(accountRepository.findOne(UUID.fromString(childId)), new ArrayList<>());
            Collections.reverse(results);
            return results;
        }
    }

    @RequestMapping(path = "/children",
            method = RequestMethod.GET)
    public List<ContactDto> getChildren(@RequestParam(name = "parentId", required = true) String parentId) throws AccountNotExistException {
        if (ROOT_KEY.equals(parentId)) {
            return networkTreeRepository.findContactsOfRootNodes().stream()
                    .map((AccountEntity a) -> new ContactDto(
                            a.getId(),
                            (networkTreeRepository.isRoot(a.getId())) ? null : accountRepository.getParent(a.getId()).getId(),
                            a.getName(),
                            a.getEmail(),
                            nullSafeGet(networkTreeRepository.findByAccount(a.getId()).getCodes()),
                            a.getPhone()))
                    .collect(Collectors.toList());
        } else {

            final UUID id = UUID.fromString(parentId);
            if (accountRepository.exists(id)) {
                return networkTreeRepository.findContactsOfChildNodes(id).stream()
                        .map((AccountEntity a) -> new ContactDto(
                                a.getId(),
                                (networkTreeRepository.isRoot(a.getId())) ? null : accountRepository.getParent(a.getId()).getId(),
                                a.getName(),
                                a.getEmail(),
                                nullSafeGet(networkTreeRepository.findByAccount(a.getId()).getCodes()),
                                a.getPhone()))
                        .collect(Collectors.toList());
            } else {
                throw new AccountNotExistException(parentId);
            }
        }
    }
    public static final String ROOT_KEY = "empty";

    @RequestMapping(path = "/save",
            method = RequestMethod.POST)
    public void save(@RequestBody ContactDto dto) throws AccountNotExistException {
        if (dto.getId() == null || !accountRepository.exists(dto.getId())) {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setEmail(dto.getEmail());
            accountEntity.setName(dto.getName());
            accountEntity.setPhone(dto.getPhone());
            // TODO: generate a strong password.
            accountEntity.setPassword("qwe123");
            accountEntity = accountRepository.save(accountEntity);

            NetworkNodeEntity nodeEntity = new NetworkNodeEntity();
            nodeEntity.setActive(true);
            nodeEntity.setContact(accountEntity);
            nodeEntity.setCodes(dto.getCodes());
            networkTreeRepository.save(nodeEntity);

            NetworkNodeEntity parentEntity = networkTreeRepository.findByAccount(dto.getParent());

            nodeEntity.setParent(parentEntity);
            if (parentEntity.getChildren() == null) {
                parentEntity.setChildren(new ArrayList<>());
            }
            parentEntity.getChildren().add(nodeEntity);
            networkTreeRepository.save(nodeEntity);
            networkTreeRepository.save(parentEntity);

            accountEntity.setNode(nodeEntity);
            accountRepository.save(accountEntity);

            MailBoxEntity mbEntity = new MailBoxEntity();
            mbEntity.setOwner(nodeEntity);
            mbRepository.save(mbEntity);

            nodeEntity.setMailBox(mbEntity);
            networkTreeRepository.save(nodeEntity);

            LOG.log(Level.INFO, "Created new member for {0}", dto.getEmail());
            LOG.log(Level.INFO, "\t- Account: {0}", accountEntity.getId());
            LOG.log(Level.INFO, "\t- Node: {0}", nodeEntity.getId());
            LOG.log(Level.INFO, "\t- Mail-box: {0}", mbEntity.getId());

        }
    }

    @RequestMapping(path = "/send",
            method = RequestMethod.POST)
    public void sendCodes(@RequestBody TransactionDto dto) throws InsufficientCodesException, AccountNotExistException {
        // Source account validation.
        if (!accountRepository.exists(dto.getFrom())) {
            LOG.log(Level.WARNING, "Source account {0} not exsists.", dto.getFrom());
            throw new AccountNotExistException();
        }
        // Destination account validation.
        if (!accountRepository.exists(dto.getTo())) {
            LOG.log(Level.WARNING, "Destination account {0} not exsists.", dto.getTo());
            throw new AccountNotExistException();
        }

        // Retrieve entities from database.
        NetworkNodeEntity source = networkTreeRepository.findByAccount(dto.getFrom());
        NetworkNodeEntity destination = networkTreeRepository.findByAccount(dto.getTo());

        // Validation for the transaction.
        if (source.getCodes() < dto.getCount() && !networkTreeRepository.isRoot(dto.getFrom())) {
            LOG.log(Level.WARNING, "Account {0} codes insufficient for the transaction.", dto.getFrom());
            throw new InsufficientCodesException();
        }

        source.setCodes(source.getCodes() - dto.getCount());
        networkTreeRepository.save(source);
        destination.setCodes(destination.getCodes() + dto.getCount());
        networkTreeRepository.save(destination);

        LOG.log(Level.INFO, "Send {0} codes from account {1} to account {2}", new Object[]{dto.getCount(), dto.getFrom(), dto.getTo()});
    }
}
