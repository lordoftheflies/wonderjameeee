/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author lordoftheflies
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "resource", discriminatorType = DiscriminatorType.STRING, length = 50)
@DiscriminatorValue(value = "resource")
@NamedQueries({
    @NamedQuery(name = "ContentEntity.findByParent", query = "SELECT c FROM ContentEntity c WHERE c.parent.id = :parentId ORDER BY c.orderIndex"),
    @NamedQuery(name = "ContentEntity.findPublicByParent", query = "SELECT c FROM ContentEntity c WHERE c.parent.id = :parentId AND c.parent.draft = FALSE ORDER BY c.orderIndex"),
    @NamedQuery(name = "ContentEntity.findPublishedByParent", query = "SELECT c FROM ContentEntity c WHERE c.parent.id = :parentId ORDER BY c.orderIndex"),
    
    @NamedQuery(name = "ContentEntity.findRoots", query = "SELECT c FROM ContentEntity c WHERE c.parent IS NULL"),
    @NamedQuery(name = "ContentEntity.findPublishedRoots", query = "SELECT c FROM ContentEntity c WHERE c.parent IS NULL"),
    
    @NamedQuery(name = "ContentEntity.findByChild", query = "SELECT c.parent FROM ContentEntity c WHERE c.id = :childId AND c.parent IS NOT NULL")
})
public class ContentEntity extends UniqueEntity {

    private static final long serialVersionUID = 1L;
    
    
    @Basic(optional = false)
    private Integer orderIndex;

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
    
    

    @Basic
    private int width;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    
    @Basic
    private int height;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    @Basic
    private int fontSize;

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
    
    @Basic
    private String justification;

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }
    
    
    
    

    @Column(name = "resource", insertable = false, updatable = false)
    private String resourceType;

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    
    @Basic
    @Column(length = 100000)
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

   

    @Override
    public String toString() {
        return "com.digitaldefense.christeam.entities.ContentEntity[ id=" + getId() + " ]";
    }
}
