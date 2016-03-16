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

/**
 *
 * @author lordoftheflies
 */
@ApiModel
public class SessionDto implements Serializable {

    public SessionDto() {
    }

    public SessionDto(boolean powerUser, int unused, String token, String userName) {
        this.powerUser = powerUser;
        this.unused = unused;
        this.token = token;
        this.userName = userName;
    }

    

    private boolean powerUser;

    public boolean isPowerUser() {
        return powerUser;
    }

    public void setPowerUser(boolean powerUser) {
        this.powerUser = powerUser;
    }

    private int unused;

    public int getUnused() {
        return unused;
    }

    public void setUnused(int unused) {
        this.unused = unused;
    }

    @ApiModelProperty
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @ApiModelProperty
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
