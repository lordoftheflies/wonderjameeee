/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.entities;

import hu.cherubits.wonderjam.common.ContentType;
import hu.cherubits.wonderjam.common.ViewConstants;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author lordoftheflies
 */
@Entity
@DiscriminatorValue(value = ContainerContentEntity.RESOURCE_TYPE)
@NamedQueries({
//    @NamedQuery(name = "ContainerContentEntity.findByParent", query = "SELECT c FROM ContentEntity c WHERE c.parent.id = :parentId"),
//    @NamedQuery(name = "ContainerContentEntity.findRoots", query = "SELECT c FROM ContentEntity c WHERE c.parent IS NULL"),
    @NamedQuery(name = "ContainerContentEntity.findByParent", query = "SELECT c FROM ContainerContentEntity c WHERE c.parent.id = :parentId ORDER BY c.title"),
    @NamedQuery(name = "ContainerContentEntity.findDraftByParent", query = "SELECT c FROM ContainerContentEntity c WHERE c.parent.id = :parentId AND c.node.contact.id = :accountId ORDER BY c.title"),
    @NamedQuery(name = "ContainerContentEntity.findPublicByParent", query = "SELECT c FROM ContainerContentEntity c WHERE c.parent.id = :parentId AND c.publicIndicator = TRUE ORDER BY c.title"),
    @NamedQuery(name = "ContainerContentEntity.findPublishedByParent", query = "SELECT c FROM ContainerContentEntity c WHERE c.parent.id = :parentId AND c.publicIndicator = FALSE ORDER BY c.title"),

    @NamedQuery(name = "ContainerContentEntity.findRoots", query = "SELECT c FROM ContainerContentEntity c WHERE c.parent IS NULL"),
    @NamedQuery(name = "ContainerContentEntity.findDraftRoots", query = "SELECT c FROM ContainerContentEntity c WHERE c.parent IS NULL AND c.node.contact.id = :accountId ORDER BY c.title"),
    @NamedQuery(name = "ContainerContentEntity.findPublicRoots", query = "SELECT c FROM ContainerContentEntity c WHERE c.parent IS NULL AND c.publicIndicator = TRUE ORDER BY c.title"),
    @NamedQuery(name = "ContainerContentEntity.findPublishedRoots", query = "SELECT c FROM ContainerContentEntity c WHERE c.parent IS NULL AND c.publicIndicator = FALSE ORDER BY c.title")
})
public class ContainerContentEntity extends UniqueEntity {

    public static final String RESOURCE_TYPE = ViewConstants.CONTENT_MANAGEMENT_WIDGET_CONTAINER;

    @Basic
    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    @Basic
    private boolean draft = true;

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    @Basic
    private Boolean publicIndicator = true;

    public Boolean getPublicIndicator() {
        return publicIndicator;
    }

    public void setPublicIndicator(Boolean publicIndicator) {
        this.publicIndicator = publicIndicator;
    }
    
    @Basic
    private String embeddedFile;

    public String getEmbeddedFile() {
        return embeddedFile;
    }

    public void setEmbeddedFile(String embeddedFile) {
        this.embeddedFile = embeddedFile;
    }

    @ManyToOne
    private NetworkNodeEntity node;

    public NetworkNodeEntity getNode() {
        return node;
    }

    public void setNode(NetworkNodeEntity node) {
        this.node = node;
    }

    @OneToMany(mappedBy = "parent")
    private List<ContentEntity> children;

    public List<ContentEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ContentEntity> children) {
        this.children = children;
    }
}
