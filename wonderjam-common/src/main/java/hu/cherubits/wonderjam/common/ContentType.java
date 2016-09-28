/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.common;

/**
 *
 * @author lordoftheflies
 */
public enum ContentType {
    ASSEMBLED("assembled"), EMBEDDED("embedded"), LINKED("linked");
    private final String key;

    private ContentType(String key) {
        this.key = key;
    }
    
}
