/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.UUID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lordoftheflies
 */
@XmlRootElement
@ApiModel
public class ContactDto implements Serializable {

    public ContactDto() {
    }

    public ContactDto(UUID id, UUID parent, String name, String email, int codes, String phone) {
        this.id = id;
        this.parent = parent;
        this.name = name;
        this.email = email;
        this.codes = codes;
        this.phone = phone;
    }

    @ApiModelProperty
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @ApiModelProperty
    private UUID parent;

    public UUID getParent() {
        return parent;
    }

    public void setParent(UUID parentId) {
        this.parent = parentId;
    }

    @ApiModelProperty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ApiModelProperty
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ApiModelProperty
    private int codes;

    public int getCodes() {
        return codes;
    }

    public void setCodes(int codes) {
        this.codes = codes;
    }

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
