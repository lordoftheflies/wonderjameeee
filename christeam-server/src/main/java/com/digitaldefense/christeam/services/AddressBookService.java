/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.services;

import com.digitaldefense.christeam.model.ContactDto;
import com.digitaldefense.christeam.exceptions.AccountNotExistException;
import com.digitaldefense.christeam.dal.AccountRepository;
import com.digitaldefense.christeam.dal.NetworkTreeRepository;
import com.digitaldefense.christeam.entities.AccountEntity;
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
}
