/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.model;

import hu.cherubits.wonderjam.common.ContentType;
import java.util.UUID;

/**
 *
 * @author lordoftheflies
 */
public class SectionDto extends ContentDto {

    public SectionDto() {
    }

    public SectionDto(String type) {
        this.type = type;
    }

    public SectionDto(UUID id, ContentType contentType, String title, String type, String data, String justification, int fontSize, int width, int height) {
        this.title = title;
        this.name = (id == null) ? "ROOT" : id.toString();
        this.id = id;
        this.type = type;
        this.data = data;
        this.justification = justification;
        this.fontSize = fontSize;
        this.width = width;
        this.height = height;
    }

    private ContentType contentType = ContentType.ASSEMBLED;

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String justification;

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    private int fontSize;

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    private int width;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    private int height;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
