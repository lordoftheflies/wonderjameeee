/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author lordoftheflies
 */
@Entity
@DiscriminatorValue(value = VideoContentEntity.RESOURCE_TYPE)
public class VideoContentEntity extends ContentEntity {

    public static final String RESOURCE_TYPE = "video";
}
