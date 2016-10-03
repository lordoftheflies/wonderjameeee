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
    assembled("assembled"), embedded("embedded"), linked("linked");
    private final String key;

    private ContentType(String key) {
        this.key = key;
    }
    
}
