package hu.cherubits.wonderjam;

import hu.cherubits.wonderjam.FcmConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lordoftheflies
 */
@Configuration
@ComponentScan(
        basePackages = {
            "com.dd.mlm.topn.persistence.entities"
        },
        basePackageClasses = {
            DatabaseConfiguration.class,
            FcmConfiguration.class
        })
@EnableJpaRepositories(basePackages = {
    "com.dd.mlm.topn.persistence.dal"
})
@EnableAutoConfiguration
public class MailingServiceConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
