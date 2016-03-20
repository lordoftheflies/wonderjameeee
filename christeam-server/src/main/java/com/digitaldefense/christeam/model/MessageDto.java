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
public class MessageDto extends PageDto {

    public MessageDto() {
    }

    public MessageDto(UUID id, String title, List<UUID> recipients, List<SectionDto> sections) {
        super(id, title, sections);
        this.recipients = recipients;
    }

    public MessageDto(UUID id, String title, List<UUID> recipients, SectionDto... sections) {
        super(id, title, sections);
        this.recipients = recipients;
    }

    public MessageDto(UUID pageId, Collection<UUID> recipients) {
        super(pageId, null, new ArrayList<>());
        this.recipients = new ArrayList<>(recipients);
    }

    public MessageDto(UUID pageId, UUID... recipients) {
        super(pageId, null, new ArrayList<>());
        this.recipients = Arrays.asList(recipients);
    }

    @ApiModelProperty
    private List<UUID> recipients;

    public List<UUID> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<UUID> recipients) {
        this.recipients = recipients;
    }
}
