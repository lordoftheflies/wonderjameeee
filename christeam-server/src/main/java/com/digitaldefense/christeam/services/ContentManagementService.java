/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.services;

import com.digitaldefense.christeam.exceptions.ContentNotFoundException;
import com.digitaldefense.christeam.dal.AccountRepository;
import com.digitaldefense.christeam.dal.ContentRepository;
import com.digitaldefense.christeam.dal.MailBoxRepository;
import com.digitaldefense.christeam.dal.MessageRepository;
import com.digitaldefense.christeam.entities.ContainerContentEntity;
import com.digitaldefense.christeam.entities.ContentEntity;
import com.digitaldefense.christeam.entities.ImageContentEntity;
import com.digitaldefense.christeam.entities.MessageEntity;
import com.digitaldefense.christeam.entities.ReferenceContentEntity;
import com.digitaldefense.christeam.entities.TextContentEntity;
import com.digitaldefense.christeam.entities.VideoContentEntity;
import com.digitaldefense.christeam.model.MessageDto;
import com.digitaldefense.christeam.model.PageDto;
import com.digitaldefense.christeam.model.SectionDto;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lordoftheflies
 */
@RestController
@RequestMapping(path = "/content-management")
public class ContentManagementService {

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MailBoxRepository mailBoxRepository;

    private class ContentEntityFactory {

        public TextContentEntity createText(String title, String value, ContainerContentEntity parent) {
            TextContentEntity entity = new TextContentEntity();
            entity.setContent(value);
            entity.setTitle(title);
            entity.setParent(parent);
//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT);
            return contentRepository.save(entity);
        }

        public VideoContentEntity createVideo(String title, String value, ContainerContentEntity parent) {
            VideoContentEntity entity = new VideoContentEntity();
            entity.setContent(value);
            entity.setTitle(title);
            entity.setParent(parent);
//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_VIDEO);
            return contentRepository.save(entity);
        }

        private ImageContentEntity createImage(String title, String value, ContainerContentEntity parent) {
            ImageContentEntity entity = new ImageContentEntity();
            entity.setContent(value);
            entity.setTitle(title);
            entity.setParent(parent);
//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_IMAGE);
            return contentRepository.save(entity);
        }

        private ReferenceContentEntity createReference(String title, String value, ContainerContentEntity parent) {
            ReferenceContentEntity entity = new ReferenceContentEntity();
            entity.setContent(value);
            entity.setTitle(title);
            entity.setParent(parent);
//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_LINK);
            return contentRepository.save(entity);
        }

        private ContainerContentEntity createContent(String title, String value, ContainerContentEntity parent) {
            ContainerContentEntity entity = new ContainerContentEntity();
            entity.setContent(value);
            entity.setTitle(title);
            entity.setParent(parent);
//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_CONTAINER);
            return contentRepository.save(entity);
        }
    }

    private ContentEntityFactory factory = new ContentEntityFactory();

    @CrossOrigin
    @RequestMapping(path = "/page/{pageId}",
            method = RequestMethod.GET,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public PageDto page(@PathVariable(value = "pageId") String pageId) throws ContentNotFoundException {
        if (!contentRepository.exists(UUID.fromString(pageId))) {
            throw new ContentNotFoundException();
        } else {
            PageDto dto = new PageDto();
            ContentEntity container = contentRepository.findOne(UUID.fromString(pageId));
            dto.setTitle(container.getTitle());
            dto.setId(container.getId());
            if (ContainerContentEntity.RESOURCE_TYPE.equals(container.getResourceType())) {
                dto.setSections(contentRepository
                        .findByParent(UUID.fromString(pageId))
                        .stream()
                        .map((ContentEntity entity) -> new SectionDto(entity.getTitle(), entity.getResourceType(), entity.getContent()))
                        .collect(Collectors.toList()));
            } else {
                LOG.log(Level.INFO, "{0} is not a container.", pageId);
            }
            return dto;
        }
    }

    @CrossOrigin
    @RequestMapping(path = "/toc/info",
            method = RequestMethod.GET,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public PageDto pageInfo(@RequestParam(value = "pageId", required = true) String pageId) throws ContentNotFoundException {
        PageDto dto = new PageDto();
        if (ROOT_PSEUDO_ID.equals(pageId)) {
            dto.setTitle("");
            dto.setId(null);

        } else {

            UUID pageUuid = UUID.fromString(pageId);
            if (!contentRepository.exists(pageUuid)) {
                throw new ContentNotFoundException();
            } else {
                ContentEntity container = contentRepository.findOne(UUID.fromString(pageId));
                dto.setTitle(container.getTitle());
                dto.setId(container.getId());
            }
        }

        return dto;

    }

    @CrossOrigin
    @RequestMapping(path = "/toc",
            method = RequestMethod.GET,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public PageDto toc(@RequestParam(value = "pageId", defaultValue = "ROOT") String pageId) throws ContentNotFoundException {
        PageDto dto = new PageDto();

        if (ROOT_PSEUDO_ID.equals(pageId)) {
            dto.setSections(contentRepository
                    .findRoots()
                    .stream()
                    .map((ContentEntity entity) -> new SectionDto(entity.getTitle(), entity.getResourceType(), entity.getId().toString()))
                    .collect(Collectors.toList()));
        } else if (!contentRepository.exists(UUID.fromString(pageId))) {
            throw new ContentNotFoundException();
        } else {
            ContentEntity container = contentRepository.findOne(UUID.fromString(pageId));
            dto.setTitle(container.getTitle());
            dto.setId(container.getId());
            dto.setSections(contentRepository
                    .findByParent(UUID.fromString(pageId))
                    .stream()
                    .map((ContentEntity entity) -> new SectionDto(entity.getTitle(), entity.getResourceType(), entity.getId().toString()))
                    .collect(Collectors.toList()));

        }
        return dto;
    }
    public static final String ROOT_PSEUDO_ID = "ROOT";

    @CrossOrigin
    @RequestMapping(path = "/save",
            method = RequestMethod.POST,
            consumes = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public void save(PageDto dto) {
        ContainerContentEntity container = factory.createContent(dto.getTitle(), null, null);
        LOG.log(Level.INFO, "Created new page[{0}].", container.getId());
        dto.getSections().forEach(s -> {
            ContentEntity ce;
            switch (s.getType()) {
                case "video":
                case ViewConstants.CONTENT_MANAGEMENT_WIDGET_VIDEO:
                    ce = factory.createVideo(s.getKey(), s.getData(), container);
                    break;
                case "image":
                case ViewConstants.CONTENT_MANAGEMENT_WIDGET_IMAGE:
                    ce = factory.createImage(s.getKey(), s.getData(), container);
                    break;
                case "link":
                case ViewConstants.CONTENT_MANAGEMENT_WIDGET_LINK:
                    ce = factory.createReference(s.getKey(), s.getData(), container);
                    break;
                case "text":
                case ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT:
                default:
                    ce = factory.createText(s.getKey(), s.getData(), container);
                    break;
            }
            LOG.log(Level.INFO, "Created new {0}-content[{1}].", new Object[]{s.getType(), ce.getId()});
        });
    }

    @CrossOrigin
    @RequestMapping(path = "/publish",
            method = RequestMethod.POST,
            consumes = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public void publish(MessageDto dto) throws ContentNotFoundException {
        if (!contentRepository.exists(dto.getId())) {
            throw new ContentNotFoundException();
        } else {
            ContentEntity contentEntity = contentRepository.findOne(dto.getId());
            messageRepository.save(dto.getRecipients().stream().map((UUID recipientId) -> {
                MessageEntity message = new MessageEntity();
                message.setContent(contentEntity);
                message.setMailBox(mailBoxRepository.findByRecipient(recipientId));
                message.setRead(false);
                return message;
            }).collect(Collectors.toList()));
        }
    }

    private static final Logger LOG = Logger.getLogger(ContentManagementService.class.getName());
}
