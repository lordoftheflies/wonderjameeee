/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.services;

import com.digitaldefense.christeam.dal.NetworkTreeRepository;
import com.digitaldefense.christeam.entities.AccountEntity;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author lordoftheflies
 */
@RequestMapping(path = "/addressbook",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressBookService {

    @Autowired
    private NetworkTreeRepository networkTreeRepository;

    @RequestMapping(path = "/children",
            method = RequestMethod.GET)
    public List<ContactDto> getChildren(String parentId) {
        return networkTreeRepository.findContactsOfChildNodes(UUID.fromString(parentId)).stream()
                .map((AccountEntity a) -> new ContactDto(a.getName()))
                .collect(Collectors.toList());
    }
}
