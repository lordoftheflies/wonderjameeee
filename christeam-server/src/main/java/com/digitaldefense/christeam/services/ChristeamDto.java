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
public class ChristeamDto implements Serializable {

    private NetworkDto network;

    public NetworkDto getNetwork() {
        return network;
    }

    public void setNetwork(NetworkDto network) {
        this.network = network;
    }
}
