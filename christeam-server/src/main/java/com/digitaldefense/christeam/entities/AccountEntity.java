/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.entities;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author lordoftheflies
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "AccountEntity.findByNetwork", query = "SELECT a FROM AccountEntity a WHERE a.node.id = :nodeId")
})
public class AccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public AccountEntity() {
    }

    public AccountEntity(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public AccountEntity(UUID id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    @Id
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Generate UNIQUE entity key.
     */
//    @PrePersist
//    protected void generateKey() {
//        if (this.id == null) {
//            this.id = UUID.randomUUID();
//        }
//    }

    @OneToOne
    private NetworkNodeEntity node;

    public NetworkNodeEntity getNode() {
        return node;
    }

    public void setNode(NetworkNodeEntity node) {
        this.node = node;
    }

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountEntity)) {
            return false;
        }
        AccountEntity other = (AccountEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.digitaldefense.christeam.entities.AccountEntity[ id=" + id + " ]";
    }

}
