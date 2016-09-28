/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author lordoftheflies
 */
@Configuration
@ComponentScan(
//        basePackages = {
//            "com.dd.mlm.topn.persistence.entities"
//        },
        basePackageClasses = {
            MailConfig.class,
            DatabaseConfiguration.class
        })
//@EnableJpaRepositories(basePackages = {
//    "com.dd.mlm.topn.persistence.dal"
//})
@EnableAutoConfiguration
public class NetworkServiceConfiguration {

}
