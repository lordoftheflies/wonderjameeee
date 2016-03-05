/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.dal;

import com.digitaldefense.christeam.entities.AccountEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author lordoftheflies
 */
public interface AccountRepository extends CrudRepository<AccountEntity, UUID> {

    AccountEntity findByNetwork(@Param("nodeId") Long nodeId);
    
    AccountEntity findByCredentials(@Param("email") String email, @Param("password") String password);
}
