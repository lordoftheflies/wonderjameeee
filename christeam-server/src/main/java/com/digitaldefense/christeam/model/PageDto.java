/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author lordoftheflies
 */
public class PageDto {

    public PageDto() {
    }

    public PageDto(String title) {
        this.title = title;
    }
 
    public PageDto(UUID id, String title, Collection<SectionDto> sections) {
        this.title = title;
        this.id = id;
        this.sections = new ArrayList<>(sections);
    }
    
    public PageDto(UUID id, String title, SectionDto... sections) {
        this.title = title;
        this.id = id;
        this.sections = Arrays.asList(sections);
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    private List<SectionDto> sections = new ArrayList<>();

    public List<SectionDto> getSections() {
        return sections;
    }

    public void setSections(List<SectionDto> sections) {
        this.sections = sections;
    }
}
