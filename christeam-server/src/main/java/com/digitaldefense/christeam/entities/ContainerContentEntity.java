/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.entities;

import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author lordoftheflies
 */
@Entity
@DiscriminatorValue(value = ContainerContentEntity.RESOURCE_TYPE)
public class ContainerContentEntity extends ContentEntity {

    public static final String RESOURCE_TYPE = "container";

    @OneToMany(mappedBy = "parent")
    private List<ContentEntity> children;

    public List<ContentEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ContentEntity> children) {
        this.children = children;
    }
}
