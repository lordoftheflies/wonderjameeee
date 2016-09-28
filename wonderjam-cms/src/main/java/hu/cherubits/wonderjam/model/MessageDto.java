/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author lordoftheflies
 */
public class MessageDto {

    private UUID pageId;

    public UUID getPageId() {
        return pageId;
    }

    public void setPageId(UUID pageId) {
        this.pageId = pageId;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private List<UUID> recipients;

    public List<UUID> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<UUID> recipients) {
        this.recipients = recipients;
    }
}
