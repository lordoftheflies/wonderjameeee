/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.services;

import com.digitaldefense.christeam.dal.ContentRepository;
import com.digitaldefense.christeam.dal.MessageRepository;
import com.digitaldefense.christeam.entities.MessageEntity;
import com.digitaldefense.christeam.model.PageDto;
import com.digitaldefense.christeam.model.SectionDto;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lordoftheflies
 */
@RestController
@RequestMapping(path = "/mailbox")
public class MailBoxService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ContentRepository contentRepository;

    @RequestMapping(path = "/{accountId}/inbox", method = RequestMethod.GET)
    public PageDto inbox(@PathVariable("accountId") String accountId) {
        UUID id = UUID.fromString(accountId);
        PageDto dto = new PageDto();
        dto.setSections(messageRepository.inboxByRecipient(id).stream()
                .map((MessageEntity e) -> new SectionDto(e.getContent().getTitle(), e.getContent().getResourceType(), null))
                .collect(Collectors.toList()));
        return dto;
    }
}
