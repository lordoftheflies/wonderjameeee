/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author lordoftheflies
 */
@ApiModel
public class MessageDto implements Serializable {

    public MessageDto() {
    }

    public MessageDto(UUID pageId, Collection<UUID> recipients) {
        this.recipients = new ArrayList<>(recipients);
        this.pageId = pageId;
    }

    public MessageDto(UUID pageId, UUID... recipients) {
        this.recipients = Arrays.asList(recipients);
        this.pageId = pageId;
    }

    @ApiModelProperty
    private List<UUID> recipients;

    public List<UUID> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<UUID> recipients) {
        this.recipients = recipients;
    }

    @ApiModelProperty
    private UUID pageId;

    public UUID getPageId() {
        return pageId;
    }

    public void setPageId(UUID pageId) {
        this.pageId = pageId;
    }
}
