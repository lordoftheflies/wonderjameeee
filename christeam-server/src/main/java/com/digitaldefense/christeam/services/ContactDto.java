/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.services;

import java.io.Serializable;

/**
 *
 * @author lordoftheflies
 */
public class ContactDto implements Serializable {

    public ContactDto(String name) {
        this.name = name;
    }

    public ContactDto() {
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
