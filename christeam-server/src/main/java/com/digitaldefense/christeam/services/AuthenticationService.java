/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.services;

import com.digitaldefense.christeam.exceptions.CredentialsException;
import com.digitaldefense.christeam.exceptions.AccountNotFoundException;
import com.digitaldefense.christeam.exceptions.RegistrationCodeAlreadyUsedException;
import com.digitaldefense.christeam.dal.AccountRepository;
import com.digitaldefense.christeam.dal.NetworkTreeRepository;
import com.digitaldefense.christeam.entities.AccountEntity;
import com.digitaldefense.christeam.entities.NetworkNodeEntity;
import com.digitaldefense.christeam.model.CredentialsDto;
import com.digitaldefense.christeam.model.RegistrationDto;
import com.digitaldefense.christeam.model.SessionDto;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lordoftheflies
 */
@RestController
@RequestMapping(
        path = "/authentication",
        consumes = {
            MediaType.APPLICATION_JSON_VALUE
        },
        produces = {
            MediaType.APPLICATION_JSON_VALUE
        })
public class AuthenticationService {

    private static final Logger LOG = Logger.getLogger(AuthenticationService.class.getName());

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private NetworkTreeRepository networkRepository;

    private void apply(RegistrationDto dto, AccountEntity entity) {
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setName(dto.getName());
        entity.setId(UUID.fromString(dto.getActivationCode()));
    }

    private void apply(AccountEntity entity, SessionDto dto) {
        dto.setUserName(entity.getName());
        dto.setToken(entity.getId().toString());
    }

    @CrossOrigin
    @RequestMapping(path = "/signon", method = RequestMethod.POST)
    public void signOn(RegistrationDto dto) throws AccountNotFoundException, RegistrationCodeAlreadyUsedException {
        if (accountRepository.count() == 0) {
            AccountEntity accountEntity = new AccountEntity();
            apply(dto, accountEntity);
            accountEntity = accountRepository.save(accountEntity);

            NetworkNodeEntity nodeEntity = new NetworkNodeEntity();
            nodeEntity.setActive(true);
            nodeEntity.setContact(accountEntity);
            networkRepository.save(nodeEntity);

            LOG.log(Level.INFO, "Sign-on principal user {0}", dto.toString());
        } else if (!accountRepository.exists(UUID.fromString(dto.getActivationCode()))) {
            throw new AccountNotFoundException();
        } else {
            AccountEntity accountEntity = accountRepository.findOne(UUID.fromString(dto.getActivationCode()));
            NetworkNodeEntity nodeEntity = networkRepository.findByAccount(accountEntity.getId());
            if (nodeEntity.getActive()) {
                throw new RegistrationCodeAlreadyUsedException();
            } else {
                apply(dto, accountEntity);
                accountRepository.save(accountEntity);

                nodeEntity.setActive(true);
                networkRepository.save(nodeEntity);

                LOG.log(Level.INFO, "Sign-on user {0}", dto.getEmail());
            }
        }
    }

    @CrossOrigin
    @RequestMapping(path = "/signin", method = RequestMethod.POST)
    public SessionDto signIn(CredentialsDto dto) throws CredentialsException {
        SessionDto result = new SessionDto();
        AccountEntity accountEntity = accountRepository.findByCredentials(dto.getEmail(), dto.getPassword());
        if (accountEntity == null) {
            throw new CredentialsException();
        } else {
            LOG.log(Level.INFO, "Sign-in user {0}", dto.getEmail());
            apply(accountEntity, result);
            return result;
        }
    }

    @CrossOrigin
    @RequestMapping(path = "/signout", method = RequestMethod.POST)
    public void signOut(SessionDto session) {
        LOG.log(Level.INFO, "Sign-out token {0}", session.getToken());
    }

    @CrossOrigin
    @RequestMapping(path = "/session", method = RequestMethod.GET)
    public SessionDto session(@RequestParam(value = "token") String token) throws AccountNotFoundException {
        if (!accountRepository.exists(UUID.fromString(token))) {
            throw new AccountNotFoundException();
        } else {
            SessionDto dto = new SessionDto();
            AccountEntity accountEntity = accountRepository.findOne(UUID.fromString(token));
            apply(accountEntity, dto);
            return dto;
        }
    }
}
