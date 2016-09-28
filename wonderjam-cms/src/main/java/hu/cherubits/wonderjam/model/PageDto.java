/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.model;

import hu.cherubits.wonderjam.common.ContentType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author lordoftheflies
 */
public class PageDto extends ContentDto {

    public PageDto() {
    }
    
    public PageDto(UUID id, String title, ContentType ct) {
        this.id = id;
        this.title = title;
        this.contentType = ct;
    }

    public PageDto(String title, ContentType ct) {
        this.title = title;
        this.contentType = ct;
    }

    public PageDto(UUID id, String title, Collection<SectionDto> sections, ContentType ct) {
        this.title = title;
        this.id = id;
        this.sections = new ArrayList<SectionDto>(sections);
        this.contentType = ct;
    }

    public PageDto(UUID id, String title, ContentType ct, SectionDto... sections) {
        this.title = title;
        this.id = id;
        this.sections = Arrays.asList(sections);
        this.contentType = ct;
    }

    

    private boolean draft;

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    private List<? extends ContentDto> sections = new ArrayList<>();

    public List<? extends ContentDto> getSections() {
        return sections;
    }

    public void setSections(List<? extends ContentDto> sections) {
        this.sections = sections;
    }
    
    private ContentType contentType;

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
    
    private String embeddedFile;

    public String getEmbeddedFile() {
        return embeddedFile;
    }

    public void setEmbeddedFile(String embeddedFile) {
        this.embeddedFile = embeddedFile;
    }
    
    

}
