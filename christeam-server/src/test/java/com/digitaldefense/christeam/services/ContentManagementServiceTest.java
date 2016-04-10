/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.services;

import com.digitaldefense.christeam.ChristeamServerApplicationTests;
import com.digitaldefense.christeam.dal.ContentRepository;
import com.digitaldefense.christeam.entities.ContainerContentEntity;
import com.digitaldefense.christeam.entities.ContentEntity;
import com.digitaldefense.christeam.entities.ImageContentEntity;
import com.digitaldefense.christeam.entities.ReferenceContentEntity;
import com.digitaldefense.christeam.entities.TextContentEntity;
import com.digitaldefense.christeam.entities.VideoContentEntity;
import com.digitaldefense.christeam.model.PageDto;
import com.digitaldefense.christeam.model.SectionDto;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.UUID;
import static org.hamcrest.Matchers.is;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author lordoftheflies
 */
public class ContentManagementServiceTest extends ChristeamServerApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ContentManagementService contentManagementServiceMock;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private ContentRepository contentRepository;

    private UUID textContentId;
    private UUID videoContentId;
    private UUID linkContentId;
    private UUID imageContentId;
    private UUID linkedTextContentId;
    private UUID containerContentId;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setUp() throws Exception {
        super.setup();

        ContainerContentEntity contentContainer = new ContainerContentEntity();
        contentContainer.setContent("test-container-content");
        contentContainer.setTitle("test-container-title");
//        contentContainer.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_CONTAINER);
        containerContentId = contentRepository.save(contentContainer).getId();
//
        ContentEntity imageContent = new ImageContentEntity();
        imageContent.setTitle("test-image-content");
        imageContent.setContent("https://www.google.hu/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwjGifff4qXLAhWFNpoKHX4XBssQjRwIBw&url=http%3A%2F%2Fwww.trademagazin.hu%2Fhirek-es-cikkek%2Fpiaci-hirek%2Fszeretjuk-a-kacsahust.html&psig=AFQjCNHKGebKo-p4MfAbQfxvxMKMgTF_jg&ust=1457137611971834");
        imageContent.setParent(contentContainer);
//        imageContent.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_IMAGE);
        imageContentId = contentRepository.save(imageContent).getId();

        ContentEntity videoContent = new VideoContentEntity();
        videoContent.setTitle("test-video-content");
        videoContent.setContent("gaVtx2wfTcE");
        videoContent.setParent(contentContainer);
//        videoContent.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_VIDEO);
        videoContentId = contentRepository.save(videoContent).getId();

        ContentEntity linkedContent = new TextContentEntity();
        linkedContent.setTitle("test-linked-content");
//        linkedContent.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT);
        linkedContent.setContent("We linked here!");
        linkedContent.setParent(contentContainer);
        linkedContent = contentRepository.save(linkedContent);
        linkedTextContentId = linkedContent.getId();

        ContentEntity linkContent = new ReferenceContentEntity();
        linkContent.setTitle("test-link-content");
//        linkContent.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_LINK);
        linkContent.setContent(linkedContent.getId().toString());
        linkContentId = contentRepository.save(linkContent).getId();

        ContentEntity textContent = new TextContentEntity();
        textContent.setTitle("test-text-content");
//        textContent.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT);
        textContent.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod , sunt in culpa qui officia deserunt mollit anim id est laborum.");
        textContentId = contentRepository.save(textContent).getId();
    }

    @After
    public void tearDown() {
//        contentRepository.deleteAll();
    }

    /**
     * Test of page method, of class ContentManagementService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testPage() throws Exception {
        System.out.println("Test /page");
        PageDto dto = contentManagementServiceMock.page(containerContentId.toString());
//        mockMvc.perform(get("/page/" + containerContentId.toString()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(contentType))
//                .andExpect(jsonPath("$.id", is(containerContentId.toString())));
    }

    /**
     * Test of save method, of class ContentManagementService.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        PageDto pageDto = new PageDto();
        pageDto.setTitle("test-save-page");
        pageDto.setSections(Arrays.asList(new SectionDto[]{
            new SectionDto("keyimg", ViewConstants.CONTENT_MANAGEMENT_WIDGET_IMAGE, "http://images2.fanpop.com/images/photos/7400000/asd-kimberley-walsh-7455554-1600-1200.jpg"),
            new SectionDto("keylink", ViewConstants.CONTENT_MANAGEMENT_WIDGET_LINK, linkedTextContentId.toString()),
            new SectionDto("keytext", ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT, "Lorem ipsum."),
            new SectionDto("keyvideo", ViewConstants.CONTENT_MANAGEMENT_WIDGET_VIDEO, "C0WXgZdwC4c"),
        }));
        contentManagementServiceMock.save(pageDto);
//        fail("The test case is a prototype.");
    }

    /**
     * Test of publish method, of class ContentManagementService.
     */
    @Test
    public void testPublish() throws Exception {
        System.out.println("publish");
//        fail("The test case is a prototype.");
    }

}
