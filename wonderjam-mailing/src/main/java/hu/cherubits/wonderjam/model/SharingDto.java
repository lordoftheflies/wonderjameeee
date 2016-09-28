/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.model;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author lordoftheflies
 */
public class SharingDto {

    public SharingDto() {
    }

    public SharingDto(UUID contentId, String message, List<UUID> recipients) {
        this.recipients = recipients;
        this.contentId = contentId;
        this.message = message;
    }

    public SharingDto(UUID contentId, String message, UUID... recipients) {
        this.recipients = Arrays.asList(recipients);
        this.contentId = contentId;
        this.message = message;
    }

    private List<UUID> recipients;

    public List<UUID> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<UUID> recipients) {
        this.recipients = recipients;
    }

    private UUID contentId;

    public UUID getContentId() {
        return contentId;
    }

    public void setContentId(UUID contentId) {
        this.contentId = contentId;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
