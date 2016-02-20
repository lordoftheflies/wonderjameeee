/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.dal;

import com.digitaldefense.christeam.entities.MessageEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author lordoftheflies
 */
public interface MessageRepository extends PagingAndSortingRepository<MessageEntity, Long> {
    
}
