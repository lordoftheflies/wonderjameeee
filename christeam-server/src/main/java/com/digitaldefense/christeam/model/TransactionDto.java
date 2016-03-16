/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;

/**
 * Model for code distributing transaction.
 * 
 * @author lordoftheflies
 */
@ApiModel(value = "Transaction DTO", description = "Code sending transaction model object.")
public class TransactionDto {

    /**
     * Source account ID.
     */
    @ApiModelProperty
    private UUID from;

    public UUID getFrom() {
        return from;
    }

    public void setFrom(UUID from) {
        this.from = from;
    }

    /**
     * Destination account.
     */
    @ApiModelProperty
    private UUID to;

    public UUID getTo() {
        return to;
    }

    public void setTo(UUID to) {
        this.to = to;
    }

    /**
     * Count of transferred codes.
     */
    @ApiModelProperty
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
