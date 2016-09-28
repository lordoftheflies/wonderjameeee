/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.services;

import hu.cherubits.wonderjam.model.PageDto;
import hu.cherubits.wonderjam.model.PublisherDto;
import hu.cherubits.wonderjam.model.SectionDto;
import hu.cherubits.wonderjam.common.ViewConstants;
import hu.cherubits.wonderjam.exceptions.ContentNotFoundException;
import hu.cherubits.wonderjam.dal.AccountRepository;
import hu.cherubits.wonderjam.dal.ContainerContentRepository;
import hu.cherubits.wonderjam.dal.ContentRepository;
import hu.cherubits.wonderjam.dal.ImageContentRepository;
import hu.cherubits.wonderjam.dal.LinkContentRepository;
import hu.cherubits.wonderjam.dal.MailBoxRepository;
import hu.cherubits.wonderjam.dal.MessageRepository;
import hu.cherubits.wonderjam.dal.NetworkTreeRepository;
import hu.cherubits.wonderjam.dal.TextContentRepository;
import hu.cherubits.wonderjam.dal.VideoContentRepository;
import hu.cherubits.wonderjam.entities.ContainerContentEntity;
import hu.cherubits.wonderjam.entities.ContentEntity;
import hu.cherubits.wonderjam.entities.ImageContentEntity;
import hu.cherubits.wonderjam.entities.NetworkNodeEntity;
import hu.cherubits.wonderjam.entities.ReferenceContentEntity;
import hu.cherubits.wonderjam.entities.TextContentEntity;
import hu.cherubits.wonderjam.entities.VideoContentEntity;
import hu.cherubits.wonderjam.common.ContentType;
import hu.cherubits.wonderjam.model.ContentDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RestController
@RequestMapping(path = "/content-management")
public class ContentManagementService {

    @Autowired
    private ContainerContentRepository containerContentRepository;

    @Autowired
    private ContainerContentRepository contentContainerRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private TextContentRepository textRepository;
    @Autowired
    private VideoContentRepository videoRepository;
    @Autowired
    private ImageContentRepository imageRepository;
    @Autowired
    private LinkContentRepository linkRepository;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private NetworkTreeRepository networkRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MailBoxRepository mailBoxRepository;

    private class ContentEntityFactory {

        private int index;

        private NetworkNodeEntity node;

        public ContentEntityFactory(int index, NetworkNodeEntity node) {
            this.index = index;
            this.node = node;
        }

        public ContentEntityFactory(NetworkNodeEntity node) {
            this.index = 0;
            this.node = node;
        }

        private void initialSetup(ContentEntity entity, ContentDto s, ContainerContentEntity parent, UUID id) {
            entity.setContent(s.getData());
            entity.setId(id);
            entity.setTitle(s.getTitle());
            entity.setJustification(s.getProperties().get("justification"));
            entity.setWidth(Integer.parseInt(s.getProperties().get("width")));
            entity.setHeight(Integer.parseInt(s.getProperties().get("height")));
            entity.setFontSize(Integer.parseInt(s.getProperties().get("fontSize")));
            entity.setParent(parent);
            entity.setOrderIndex(index++);
        }

        private <T extends ContentEntity> T safeGet(ContentDto s, CrudRepository<? extends T, UUID> repo, T newEntity) {
            try {
                T entity = repo.findOne(s.getId());
                return entity;
            } catch (Exception e) {
                return newEntity;
            }
        }

        public TextContentEntity createText(ContentDto s, ContainerContentEntity parent, UUID id) {

            TextContentEntity entity = safeGet(s, textRepository, new TextContentEntity());
            initialSetup(entity, s, parent, id);
//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT);
            return contentRepository.save(entity);
        }

        public VideoContentEntity createVideo(ContentDto s, ContainerContentEntity parent, UUID id) {
            VideoContentEntity entity = safeGet(s, videoRepository, new VideoContentEntity());
            initialSetup(entity, s, parent, id);
//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_VIDEO);
            return contentRepository.save(entity);
        }

        private ImageContentEntity createImage(ContentDto s, ContainerContentEntity parent, UUID id) {
            ImageContentEntity entity = safeGet(s, imageRepository, new ImageContentEntity());
            initialSetup(entity, s, parent, id);
//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_IMAGE);
            return contentRepository.save(entity);
        }

        private ReferenceContentEntity createReference(ContentDto s, ContainerContentEntity parent, UUID id) {
            ReferenceContentEntity entity = safeGet(s, linkRepository, new ReferenceContentEntity());
            initialSetup(entity, s, parent, id);

//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_LINK);
            return contentRepository.save(entity);
        }

        private ContainerContentEntity createAssembledContent(String title, String value, ContainerContentEntity parent, int index, UUID id) {
            ContainerContentEntity entity = new ContainerContentEntity();
            entity.setContentType(ContentType.ASSEMBLED);
            entity.setId(id);
            entity.setTitle(title);
            entity.setParent(parent);
//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_CONTAINER);
            return contentContainerRepository.save(entity);
        }

        private ContainerContentEntity createEmbeddedContent(String title, String value, ContainerContentEntity parent, int index, UUID id) {
            ContainerContentEntity entity = new ContainerContentEntity();
            entity.setContentType(ContentType.EMBEDDED);
            entity.setId(id);
            entity.setTitle(title);
            entity.setParent(parent);
//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_CONTAINER);
            return contentContainerRepository.save(entity);
        }

        private ContainerContentEntity createLinkedContent(String title, String value, ContainerContentEntity parent, int index, UUID id) {
            ContainerContentEntity entity = new ContainerContentEntity();
            entity.setContentType(ContentType.LINKED);
            entity.setId(id);
            entity.setTitle(title);
            entity.setParent(parent);
//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_CONTAINER);
            return contentContainerRepository.save(entity);
        }
    }
//
//    private ContentEntityFactory factory = new ContentEntityFactory();

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
                        .map((ContentEntity entity) -> new SectionDto(
                                entity.getId(),
                                null,
                                entity.getTitle(),
                                entity.getResourceType(),
                                entity.getContent(),
                                entity.getJustification(),
                                entity.getFontSize(),
                                entity.getWidth(),
                                entity.getHeight()))
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
            if (!contentContainerRepository.exists(pageUuid)) {
                throw new ContentNotFoundException();
            } else {
                ContainerContentEntity container = contentContainerRepository.findOne(UUID.fromString(pageId));
                dto.setTitle(container.getTitle());
                dto.setId(container.getId());
            }
        }

        return dto;

    }

    private void publicArticles(String pageId, PageDto dto) throws ContentNotFoundException {
        if (ROOT_PSEUDO_ID.equals(pageId)) {
            dto.setContentType(ContentType.LINKED);
            dto.setSections(contentContainerRepository
                    .findPublicRoots()
                    .stream()
                    .map((ContainerContentEntity entity) -> new PageDto(
                            entity.getId(),
                            entity.getTitle(),
                            entity.getContentType()))
                    .collect(Collectors.toList()));
        } else if (!contentContainerRepository.exists(UUID.fromString(pageId))) {
            throw new ContentNotFoundException();
        } else {
            ContainerContentEntity container = contentContainerRepository.findOne(UUID.fromString(pageId));
            dto.setContentType(container.getContentType());
            if (container.getContentType().equals(ContentType.EMBEDDED)) {
            } else {
                dto.setTitle(container.getTitle());
                dto.setId(container.getId());
//                dto.setSections(contentRepository
//                        .findByParent(UUID.fromString(pageId))
//                        .stream()
//                        .map((ContentEntity entity) -> new SectionDto(
//                                entity.getId().toString(),
//                                null,
//                                entity.getTitle(),
//                                entity.getResourceType(),
//                                entity.getContent(),
//                                entity.getJustification(),
//                                entity.getFontSize(),
//                                entity.getWidth(),
//                                entity.getHeight()))
//                        .collect(Collectors.toList()));
                setSections(pageId, dto);
            }

        }
    }

    @RequestMapping(path = "/article", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity articles(@RequestParam(value = "pageId", defaultValue = "ROOT") String pageId) throws ContentNotFoundException {

        PageDto dto = new PageDto();

        ContainerContentEntity container = contentContainerRepository.findOne(UUID.fromString(pageId));
        dto.setId(container.getId());
        dto.setContentType(container.getContentType());
        if (container.getContentType().equals(ContentType.EMBEDDED)) {
            // Redirect to upload
        } else {
            dto.setTitle(container.getTitle());
            dto.setId(container.getId());
            dto.setSections(contentRepository
                    .findByParent(UUID.fromString(pageId))
                    .stream()
                    .map((ContentEntity entity) -> new SectionDto(
                            entity.getId(),
                            null,
                            entity.getTitle(),
                            entity.getResourceType(),
                            entity.getContent(),
                            entity.getJustification(),
                            entity.getFontSize(),
                            entity.getWidth(),
                            entity.getHeight()))
                    .collect(Collectors.toList()));
        }
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    private void ownArticles(UUID accountId, String pageId, PageDto dto) throws ContentNotFoundException {
        if (ROOT_PSEUDO_ID.equals(pageId)) {
            dto.setContentType(ContentType.LINKED);
            dto.setSections(containerContentRepository
                    .findDraftRoots(accountId)
                    .stream()
                    .map((ContainerContentEntity entity) -> new PageDto(
                            entity.getId(),
                            entity.getTitle(),
                            entity.getContentType()))
                    .collect(Collectors.toList()));
        } else if (!contentContainerRepository.exists(UUID.fromString(pageId))) {
            throw new ContentNotFoundException();
        } else {
            ContainerContentEntity container = contentContainerRepository.findOne(UUID.fromString(pageId));
            dto.setDraft(container.isDraft());
            dto.setContentType(container.getContentType());
            if (container.getContentType().equals(ContentType.EMBEDDED)) {

            } else {
                dto.setTitle(container.getTitle());
                dto.setId(container.getId());
                setSections(pageId, dto);
            }

        }
    }

    private void setSections(String pageId, PageDto dto) {
        List<SectionDto> files = contentRepository
                .findByParent(UUID.fromString(pageId))
                .stream()
                .map((ContentEntity entity) -> new SectionDto(
                        entity.getId(),
                        null,
                        entity.getTitle(),
                        entity.getResourceType(),
                        entity.getContent(),
                        entity.getJustification(),
                        entity.getFontSize(),
                        entity.getWidth(),
                        entity.getHeight()))
                .collect(Collectors.toList());
        List<SectionDto> folders = contentContainerRepository
                .findByParent(UUID.fromString(pageId))
                .stream()
                .map((ContainerContentEntity entity) -> new SectionDto(
                        entity.getId(),
                        ContentType.LINKED,
                        entity.getTitle(),
                        ContentType.LINKED.toString(),
                        null,
                        null,
                        0,
                        0,
                        0))
                .collect(Collectors.toList());
        List<SectionDto> result = new ArrayList<>(folders);
        result.addAll(files);
        dto.setSections(result);
    }

    private void publishedArticles(String pageId, PageDto dto) throws ContentNotFoundException {
        if (ROOT_PSEUDO_ID.equals(pageId)) {
            dto.setContentType(ContentType.LINKED);
            dto.setSections(contentContainerRepository
                    .findPublishedRoots()
                    .stream()
                    .map((ContainerContentEntity entity) -> new PageDto(
                            entity.getId(),
                            entity.getTitle(),
                            entity.getContentType()))
                    .collect(Collectors.toList()));
        } else if (!contentContainerRepository.exists(UUID.fromString(pageId))) {
            throw new ContentNotFoundException();
        } else {
            ContainerContentEntity container = contentContainerRepository.findOne(UUID.fromString(pageId));
//            dto.setHasEmbeddedFile(container.isHasEmbeddedFile());
            if (container.getContentType().equals(ContentType.EMBEDDED)) {
//                dto.setEmbeddedFileName(container.getContent());
            } else {
                dto.setTitle(container.getTitle());
                dto.setId(container.getId());
                dto.setContentType(container.getContentType());
//                dto.setSections(contentRepository
//                        .findByParent(UUID.fromString(pageId))
//                        .stream()
//                        .map((ContentEntity entity) -> new SectionDto(
//                                entity.getId().toString(),
//                                null,
//                                entity.getTitle(),
//                                entity.getResourceType(),
//                                entity.getContent(),
//                                entity.getJustification(),
//                                entity.getFontSize(),
//                                entity.getWidth(),
//                                entity.getHeight()))
//                        .collect(Collectors.toList()));
                setSections(pageId, dto);
            }

        }
    }

    @CrossOrigin
    @RequestMapping(path = "/toc",
            method = RequestMethod.GET,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public PageDto toc(
            @RequestParam(value = "tag", defaultValue = "public", required = false) String tag,
            @RequestParam(value = "owner", required = false) String owner,
            @RequestParam(value = "pageId", defaultValue = "ROOT") String pageId) throws ContentNotFoundException {
        PageDto dto = new PageDto();
        switch (tag) {
            case "drafts":
                this.ownArticles(UUID.fromString(owner), pageId, dto);
                break;
            case "published":
                this.publishedArticles(pageId, dto);
                break;
            case "public":
            default:
                this.publicArticles(pageId, dto);
                break;
        }
        LOG.log(Level.INFO, "Fetched {0} articles from {1} folder.", new Object[]{dto.getSections().size(), tag});
        return dto;
    }

    @CrossOrigin
    @RequestMapping(path = "/toc/path",
            method = RequestMethod.GET,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public PageDto tocPath(@RequestParam(value = "pageId", defaultValue = "ROOT") String pageId) throws ContentNotFoundException {
        PageDto dto = new PageDto();

        if (ROOT_PSEUDO_ID.equals(pageId)) {
            dto.setTitle(ROOT_PLACEHOLDER);
            dto.setSections(Arrays.asList(new PageDto(null, "ROOT", ContentType.LINKED)));
        } else if (!containerContentRepository.exists(UUID.fromString(pageId))) {
            throw new ContentNotFoundException();
        } else {
            ContentEntity item = contentRepository.findOne(UUID.fromString(pageId));
            List<SectionDto> sections = new ArrayList<>();
            while (item != null) {
                sections.add(new SectionDto(
                        item.getId(),
                        null,
                        item.getTitle(),
                        null,
                        item.getId().toString(),
                        item.getJustification(),
                        item.getFontSize(),
                        item.getWidth(),
                        item.getHeight()));
                item = contentRepository.findByChild(item.getId());
            }

            sections.add(new SectionDto(null, ContentType.LINKED, ROOT_PLACEHOLDER, null, "ROOT", null, 0, 0, 0));
            dto.setSections(sections);

            Collections.reverse(dto.getSections());
        }
        return dto;
    }

    public static final String ROOT_PSEUDO_ID = "ROOT";

    @CrossOrigin
    @RequestMapping(path = "/{owner}/save",
            method = RequestMethod.POST,
            consumes = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public void save(
            @PathVariable("owner") String owner,
            @RequestBody PageDto dto) {

        NetworkNodeEntity node = networkRepository.findByAccount(UUID.fromString(owner));

        ContentEntityFactory factory = new ContentEntityFactory(node);

        ContainerContentEntity container = factory.createAssembledContent(dto.getTitle(), null, null, 0, dto.getId());

        switch (dto.getContentType()) {
            case ASSEMBLED:
                LOG.log(Level.INFO, "Created new assembled page[{0}].", container.getId());
                contentContainerRepository.save(container);
                dto.getSections().forEach(s -> {
                    try {
                        ContentEntity ce = null;
                        UUID sectionId = null;
                        try {

                            sectionId = s.getId();
                            LOG.log(Level.INFO, "Section id presented, modify {0} section", s.getType());
                            ContentEntity sectionEntity = contentRepository.findOne(sectionId);
                            sectionEntity.setContent(s.getData());
                            sectionEntity.setTitle(s.getTitle());
                            factory.initialSetup(sectionEntity, s, container, sectionId);
                            contentRepository.save(sectionEntity);
                        } catch (IllegalArgumentException parseEx) {
                            LOG.log(Level.INFO, "Section id not presented, create {0} section", s.getType());
                            switch (s.getType()) {
//                                case "video":
                                case ViewConstants.CONTENT_MANAGEMENT_WIDGET_VIDEO:

                                    ce = factory.createVideo(s, container, sectionId);
                                    break;
//                                case "image":
                                case ViewConstants.CONTENT_MANAGEMENT_WIDGET_IMAGE:
                                    ce = factory.createImage(s, container, sectionId);
                                    break;
//                                case "link":
                                case ViewConstants.CONTENT_MANAGEMENT_WIDGET_LINK:
                                    ce = factory.createReference(s, container, sectionId);
                                    break;
//                                case "text":
                                case ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT:
                                default:
                                    ce = factory.createText(s, container, sectionId);
                                    break;
                            }

                            LOG.log(Level.INFO, "Created new {0}-content[{1}].", new Object[]{s.getType(), ce.getId()});
                        }
                    } catch (Exception ex) {
                        LOG.log(Level.SEVERE, "Failed to persist section.", ex);
                    }

                });
                break;
            case EMBEDDED:
                container.setEmbeddedFile(dto.getEmbeddedFile());
                contentContainerRepository.save(container);
                break;
        }

//        container.setLeaf(dto.isLeaf());
        container.setDraft(dto.isDraft());
        container.setNode(node);
        contentContainerRepository.save(container);

    }

    @CrossOrigin
    @RequestMapping(path = "/publish",
            method = RequestMethod.POST,
            consumes = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public void publish(@RequestBody PublisherDto dto) throws ContentNotFoundException {
        NetworkNodeEntity entity = networkRepository.findByAccount(dto.getOwner());

        ContainerContentEntity content = contentContainerRepository.findOne(dto.getArticle());
        LOG.log(Level.INFO, "Article {0} published by owner {1}", new Object[]{dto.getArticle(), dto.getOwner()});
        content.setDraft(false);
        contentContainerRepository.save(content);
    }

    @CrossOrigin
    @RequestMapping(path = "/unpublish",
            method = RequestMethod.POST,
            consumes = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public void unpublish(@RequestBody PublisherDto dto) throws ContentNotFoundException {
        NetworkNodeEntity entity = networkRepository.findByAccount(dto.getOwner());

        ContainerContentEntity content = contentContainerRepository.findOne(dto.getArticle());
        LOG.log(Level.INFO, "Article {0} unpublished by owner {1}", new Object[]{dto.getArticle(), dto.getOwner()});
        content.setDraft(true);
        contentContainerRepository.save(content);
    }

    @CrossOrigin
    @RequestMapping(path = "/candidates/parent",
            method = RequestMethod.GET,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public List<PageDto> parentCandidates() {
        List<PageDto> pages = containerContentRepository.findAll()
                .stream()
                .filter(e -> ContentType.LINKED.equals(e.getContentType()))
                .map((ContainerContentEntity e) -> new PageDto(e.getId(), e.getTitle(), e.getContentType()))
                .collect(Collectors.toList());
        pages.add(new PageDto(null, ROOT_PLACEHOLDER, ContentType.LINKED));
        return pages;
    }

    @CrossOrigin
    @RequestMapping(path = "/candidates/child",
            method = RequestMethod.GET,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public List<PageDto> childCandidates() {
        List<PageDto> pages = containerContentRepository.findAll()
                .stream()
                .filter(e -> ContentType.LINKED.equals(e.getContentType()))
                .map((ContainerContentEntity e) -> new PageDto(e.getId(), e.getTitle(), e.getContentType()))
                .collect(Collectors.toList());
        return pages;
    }

    private static final String ROOT_PLACEHOLDER = "Root";

    private static final Logger LOG = Logger.getLogger(ContentManagementService.class
            .getName());
}
