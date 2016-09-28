/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.entities;

import hu.cherubits.wonderjam.common.ViewConstants;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author lordoftheflies
 */
@Entity
@DiscriminatorValue(value = TextContentEntity.RESOURCE_TYPE)
public class TextContentEntity extends ContentEntity {

    public static final String RESOURCE_TYPE = ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT;
}
