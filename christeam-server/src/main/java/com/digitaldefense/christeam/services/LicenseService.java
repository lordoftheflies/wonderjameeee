/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.services;

import com.digitaldefense.christeam.model.LicenseDto;
import com.digitaldefense.christeam.model.CodeRegenerationAnswerDto;
import com.digitaldefense.christeam.model.CodeRegenerationDto;
import com.digitaldefense.christeam.model.AccountActivationDto;
import com.digitaldefense.christeam.exceptions.LicenseCodeGenerationException;
import com.digitaldefense.christeam.exceptions.AccountNotExistException;
import com.digitaldefense.christeam.dal.AccountRepository;
import com.digitaldefense.christeam.dal.NetworkTreeRepository;
import com.digitaldefense.christeam.entities.AccountState;
import com.digitaldefense.christeam.entities.NetworkNodeEntity;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lordoftheflies
 */
@RequestMapping(path = "/license",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class LicenseService {

    @Autowired
    private NetworkTreeRepository networkTreeRepository;
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(path = "/{accountId}/send",
            method = RequestMethod.POST)
    public void generateCode(
            @RequestParam(name = "nodeId", required = true) UUID nodeId,
            @RequestBody LicenseDto licenseDto) throws LicenseCodeGenerationException, AccountNotExistException {
        if (accountRepository.exists(nodeId)) {
            NetworkNodeEntity object = networkTreeRepository.findByAccount(nodeId);
            NetworkNodeEntity subject = networkTreeRepository.findOne(licenseDto.getNodeId());
            if (networkTreeRepository.isRootNode(subject.getId())) {
                subject.setCodes(subject.getCodes() + licenseDto.getCount());
            } else if (object.getCodes() - licenseDto.getCount() >= 0) {
                object.setCodes(object.getCodes() - licenseDto.getCount());
                subject.setCodes(subject.getCodes() + licenseDto.getCount());
            } else {
                throw new LicenseCodeGenerationException(nodeId);
            }
        } else {
            throw new AccountNotExistException();
        }
    }

    @RequestMapping(path = "/{nodeId}/regenerate",
            method = RequestMethod.POST)
    public void codeRegenerationRequest(
            @RequestParam(name = "nodeId", required = true) UUID nodeId, @RequestBody CodeRegenerationDto dto) throws LicenseCodeGenerationException, AccountNotExistException {
        if (accountRepository.exists(nodeId)) {
            NetworkNodeEntity object = networkTreeRepository.findByAccount(nodeId);
            object.setActive(false);
            object.setState(AccountState.REGISTERED);
            networkTreeRepository.save(object);
            // TODO: email
        } else {
            throw new AccountNotExistException();
        }
    }

    @RequestMapping(path = "/{nodeId}/activate",
            method = RequestMethod.POST)
    public void activateCode(
            @RequestParam(name = "nodeId", required = true) UUID nodeId, @RequestBody AccountActivationDto dto) throws LicenseCodeGenerationException, AccountNotExistException {
        if (accountRepository.exists(nodeId)) {
            NetworkNodeEntity object = networkTreeRepository.findByAccount(nodeId);
            networkTreeRepository.save(object);
            // TODO: emails
        } else {
            throw new AccountNotExistException();
        }
    }

    @RequestMapping(path = "/{nodeId}/approve",
            method = RequestMethod.POST)
    public void approveCodeRegeneration(
            @RequestParam(name = "nodeId", required = true) UUID nodeId, @RequestBody CodeRegenerationAnswerDto dto) throws LicenseCodeGenerationException, AccountNotExistException {
        if (accountRepository.exists(nodeId)) {
            NetworkNodeEntity object = networkTreeRepository.findByAccount(nodeId);
            object.setActive(dto.getDecision());
            object.setState(AccountState.REGISTERED);
            networkTreeRepository.save(object);
            // TODO: emails
        } else {
            throw new AccountNotExistException();
        }
    }
}
