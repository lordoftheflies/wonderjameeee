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

/**
 *
 * @author lordoftheflies
 */
public class PageDto {

    public PageDto() {
    }

    public PageDto(String title, Collection<SectionDto> sections) {
        this.title = title;
        this.sections = new ArrayList<>(sections);
    }

    public PageDto(String title, SectionDto... sections) {
        this.title = title;
        this.sections = Arrays.asList(sections);
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    private List<SectionDto> sections;

    public List<SectionDto> getSections() {
        return sections;
    }

    public void setSections(List<SectionDto> sections) {
        this.sections = sections;
    }
}
