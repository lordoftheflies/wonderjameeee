/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author lordoftheflies
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "AccountEntity.findRoleById", query = "SELECT a.state FROM AccountEntity a WHERE a.id = :id"),
    @NamedQuery(name = "AccountEntity.findRootAccounts", query = "SELECT a FROM AccountEntity a WHERE a.node.parent IS NULL"),
    @NamedQuery(name = "AccountEntity.findBySubscriptionId", query = "SELECT a FROM AccountEntity a WHERE a.subscriptionId = :subscriptionId"),
    @NamedQuery(name = "AccountEntity.getParent", query = "SELECT n.parent.contact FROM NetworkNodeEntity n WHERE n.contact.id = :childId"),
    @NamedQuery(name = "AccountEntity.findByNetwork", query = "SELECT a FROM AccountEntity a WHERE a.node.id = :nodeId"),
    @NamedQuery(name = "AccountEntity.findByEmail", query = "SELECT a FROM AccountEntity a WHERE a.email = :email"),
    @NamedQuery(name = "AccountEntity.findByCredentials", query = "SELECT a FROM AccountEntity a WHERE a.email = :email AND a.password = :password")
})
public class AccountEntity implements Serializable, UserDetails {

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
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Basic(optional = true)
    private String subscriptionId;

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    @Basic(optional = true)
    private String homeUrl;

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
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
    @OneToOne(fetch = FetchType.EAGER)
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

    private String password;

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String preferredLanguage;

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    private NetworkNodeType state = NetworkNodeType.USER;

    public NetworkNodeType getState() {
        return state;
    }

    public void setState(NetworkNodeType state) {
        this.state = state;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<? extends GrantedAuthority> auhorities = new ArrayList<GrantedAuthority>();
        switch (this.state) {
            case USER:
                return Arrays.asList(NetworkNodeType.USER);
            case ADMIN:
                return Arrays.asList(NetworkNodeType.USER, NetworkNodeType.ADMIN);
            default:
                return new ArrayList<GrantedAuthority>();
        }
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
