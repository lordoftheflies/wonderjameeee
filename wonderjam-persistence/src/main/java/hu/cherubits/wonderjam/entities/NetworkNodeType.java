/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.entities;

import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author lordoftheflies
 */
public enum NetworkNodeType implements GrantedAuthority {

    GROUP("group"),
    USER("user"),
    ADMIN("admin");

    private final String key;

    public String getKey() {
        return key;
    }
    
    private NetworkNodeType(String key) {
        this.key = key;
    }

    @Override
    public String getAuthority() {
        return key;
    }
}
