/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.dal;

import hu.cherubits.wonderjam.entities.MessageEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lordoftheflies
 */
@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Long>{
    
    
    List<MessageEntity> inboxByRecipient(@Param("recipientId") UUID recipientId);
    List<MessageEntity> notificationInboxByRecipient(@Param("subscriptionId") String subscriptionId);
    List<MessageEntity> outboxByRecipient(@Param("senderId") UUID senderId);
}
