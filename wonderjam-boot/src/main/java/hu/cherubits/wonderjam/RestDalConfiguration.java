/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam;

import hu.cherubits.wonderjam.entities.AccountEntity;
import hu.cherubits.wonderjam.entities.NetworkNodeEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

/**
 *
 * @author lordoftheflies
 */
@Configuration
public class RestDalConfiguration extends RepositoryRestMvcConfiguration {
 
    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(AccountEntity.class);
        config.exposeIdsFor(NetworkNodeEntity.class);
    }    
}
