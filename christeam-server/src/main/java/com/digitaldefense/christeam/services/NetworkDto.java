/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lordoftheflies
 */
@XmlRootElement
public class NetworkDto implements Serializable {

    public NetworkDto() {
        this.contact = new ContactDto();
        this.children = new ArrayList<>();
    }

    public NetworkDto(ContactDto contact, NetworkDto... children) {
        this.contact = contact;
        this.children = Arrays.asList(children);
    }

    private ContactDto contact;

    public ContactDto getContact() {
        return contact;
    }

    public void setContact(ContactDto contact) {
        this.contact = contact;
    }

    private List<NetworkDto> children;

    public List<NetworkDto> getChildren() {
        return children;
    }

    public void setChildren(List<NetworkDto> children) {
        this.children = children;
    }

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
