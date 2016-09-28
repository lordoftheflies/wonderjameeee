/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.model;

import java.util.UUID;

/**
 *
 * @author lordoftheflies
 */
public class ContactInfoDto {

    public ContactInfoDto() {
    }

    public ContactInfoDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public ContactInfoDto(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
