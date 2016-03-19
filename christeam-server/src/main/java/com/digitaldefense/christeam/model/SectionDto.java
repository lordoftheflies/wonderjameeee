/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author lordoftheflies
 */
@ApiModel
public class SectionDto {

    public SectionDto() {
    }

    public SectionDto(String type) {
        this.type = type;
    }

    public SectionDto(String key, String type, String data) {
        this.key = key;
        this.type = type;
        this.data = data;
    }
    @ApiModelProperty
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @ApiModelProperty
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @ApiModelProperty
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
