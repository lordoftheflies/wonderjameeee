/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.dal;

import hu.cherubits.wonderjam.entities.MailBoxEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lordoftheflies
 */
@Repository
public interface MailBoxRepository extends CrudRepository<MailBoxEntity, Long> {

	/**
	 * 
	 * @param recipientId
	 * @return
	 */
    MailBoxEntity findByRecipient(@Param("recipientId") UUID recipientId);

}
