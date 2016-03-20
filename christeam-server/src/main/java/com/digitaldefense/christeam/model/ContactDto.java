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
public class ContactDto extends ContactInfoDto {

    public ContactDto() {
    }

    public ContactDto(UUID id, UUID parent, String name, String email, int codes, String phone) {
        super(id, name, email);
        this.parent = parent;
        this.codes = codes;
        this.phone = phone;
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
