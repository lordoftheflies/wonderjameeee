/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.services;

import hu.cherubits.wonderjam.model.LocaleDto;
import hu.cherubits.wonderjam.model.ChangeLanguageDto;
import hu.cherubits.wonderjam.dal.AccountRepository;
import hu.cherubits.wonderjam.dal.LocaleRepository;
import hu.cherubits.wonderjam.dal.ResourceRepository;
import hu.cherubits.wonderjam.entities.AccountEntity;
import hu.cherubits.wonderjam.entities.LocaleEntity;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lordoftheflies
 */
@RestController
@RequestMapping(path = "/localization")
public class LocalizationService {

    private static final Logger LOG = Logger.getLogger(LocalizationService.class.getName());

    @Autowired
    private AccountRepository accountDao;
    @Autowired
    private LocaleRepository localeDao;
    @Autowired
    private ResourceRepository resourceDao;

    @RequestMapping(path = "/change", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity changeLanguage(@RequestBody ChangeLanguageDto changeLanguageModel) {
        try {
            UUID id = UUID.fromString(changeLanguageModel.getAccountId());

            AccountEntity account = accountDao.findOne(id);
            account.setPreferredLanguage(changeLanguageModel.getLanguage());
            accountDao.save(account);
            return new ResponseEntity(HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity("Entit", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/locales", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<LocaleDto> locales() {
        return localeDao.findAll().stream()
                .map((LocaleEntity entity) -> new LocaleDto(
                        entity.getLanguageCode(),
                        entity.getDisplayName()
                ))
                .collect(Collectors.toList());
    }
}
