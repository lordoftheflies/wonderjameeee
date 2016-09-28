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
public class PermissionChangeDto {

    public PermissionChangeDto() {
    }

    public PermissionChangeDto(UUID subject, UUID owner) {
        this.subject = subject;
        this.owner = owner;
    }
    
    private UUID subject;

    public UUID getSubject() {
        return subject;
    }

    public void setSubject(UUID subject) {
        this.subject = subject;
    }
    
    private UUID owner;

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }
}
