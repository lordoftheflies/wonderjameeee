/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.entities;

/**
 *
 * @author lordoftheflies
 */
public enum AccountState {

    REGISTERED("registered"),
    OFFLINE("offline"),
    ONLINE("online");

    private final String key;

    private AccountState(String key) {
        this.key = key;
    }

}
