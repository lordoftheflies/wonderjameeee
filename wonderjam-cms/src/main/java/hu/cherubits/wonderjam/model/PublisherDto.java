/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.model;

import java.util.UUID;

/**
 *
 * @author lordoftheflies
 */
public class PublisherDto {

    private UUID owner;

    private UUID article;

    public UUID getArticle() {
        return article;
    }

    public void setArticle(UUID article) {
        this.article = article;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }
}
