/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author lordoftheflies
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "NetworkNodeEntity.findContactsOfChildNodes", query = "SELECT n.contact FROM NetworkNodeEntity n WHERE n.parent.contact.id = :accountId"),
    @NamedQuery(name = "NetworkNodeEntity.findByAccount", query = "SELECT n FROM NetworkNodeEntity n WHERE n.contact.id = :accountId"),
    @NamedQuery(name = "NetworkNodeEntity.findChildrenNodes", query = "SELECT n FROM NetworkNodeEntity n WHERE n.parent.id = :nodeId"),
    @NamedQuery(name = "NetworkNodeEntity.findChildren", query = "SELECT n FROM NetworkNodeEntity n WHERE n.parent.contact.id = :accountId")

})
public class NetworkNodeEntity implements Serializable {

    public NetworkNodeEntity() {
    }

    public NetworkNodeEntity(Long id, NetworkNodeEntity parent, List<NetworkNodeEntity> children, AccountEntity contact) {
        this.id = id;
        this.parent = parent;
        this.children = children;
        this.contact = contact;
    }

    public NetworkNodeEntity(AccountEntity contact, NetworkNodeEntity parent, NetworkNodeEntity... children) {
        this.parent = parent;
        this.children = Arrays.asList(children);
        this.contact = contact;
    }

    public NetworkNodeEntity(Long id, AccountEntity contact, NetworkNodeEntity parent, NetworkNodeEntity... children) {
        this.id = id;
        this.parent = parent;
        this.children = Arrays.asList(children);
        this.contact = contact;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    private AccountState state;

    public AccountState getState() {
        return state;
    }

    public void setState(AccountState state) {
        this.state = state;
    }

    @ManyToOne
    private NetworkNodeEntity parent;

    public NetworkNodeEntity getParent() {
        return parent;
    }

    public void setParent(NetworkNodeEntity parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent")
    private List<NetworkNodeEntity> children;

    public List<NetworkNodeEntity> getChildren() {
        return children;
    }

    public void setChildren(List<NetworkNodeEntity> children) {
        this.children = children;
    }

    @OneToOne(mappedBy = "node")
    private AccountEntity contact;

    public AccountEntity getContact() {
        return contact;
    }

    public void setContact(AccountEntity contact) {
        this.contact = contact;
    }

    @Basic
    private Integer codes;

    public Integer getCodes() {
        return codes;
    }

    public void setCodes(Integer codes) {
        this.codes = codes;
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
        if (!(object instanceof NetworkNodeEntity)) {
            return false;
        }
        NetworkNodeEntity other = (NetworkNodeEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.digitaldefense.christeam.entities.NetworkNodeEntity[ id=" + id + " ]";
    }

}
