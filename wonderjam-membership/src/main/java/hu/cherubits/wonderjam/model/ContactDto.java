/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.model;

import hu.cherubits.wonderjam.entities.NetworkNodeType;
import java.util.UUID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lordoftheflies
 */
@XmlRootElement
public class ContactDto extends ContactInfoDto {

    public ContactDto() {
    }

    public ContactDto(UUID id, UUID parent, String name, String email, int codes, String phone, NetworkNodeType role) {
        super(id, name, email);
        this.parent = parent;
        this.codes = codes;
        this.phone = phone;
        this.role = role.name();
    }

    public ContactDto(UUID parent, int codes, String phone, UUID id, String name, String email, boolean active, String preferredLanguage) {
        super(id, name, email);
        this.active = active;
        this.preferredLanguage = preferredLanguage;
        this.parent = parent;
        this.codes = codes;
        this.phone = phone;
    }

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private String preferredLanguage;

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    private UUID parent;

    public UUID getParent() {
        return parent;
    }

    public void setParent(UUID parentId) {
        this.parent = parentId;
    }

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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
