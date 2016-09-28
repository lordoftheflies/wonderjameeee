/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.service;

import hu.cherubits.wonderjam.model.PasswordResetDto;
import hu.cherubits.wonderjam.model.CredentialsDto;
import hu.cherubits.wonderjam.model.RegistrationDto;
import hu.cherubits.wonderjam.model.SessionDto;
import hu.cherubits.wonderjam.exceptions.AccesDeniedException;
import hu.cherubits.wonderjam.exceptions.CredentialsException;
import hu.cherubits.wonderjam.exceptions.RegistrationCodeAlreadyUsedException;
import hu.cherubits.wonderjam.dal.AccountRepository;
import hu.cherubits.wonderjam.dal.MailBoxRepository;
import hu.cherubits.wonderjam.dal.NetworkTreeRepository;
import hu.cherubits.wonderjam.entities.AccountEntity;
import hu.cherubits.wonderjam.entities.MailBoxEntity;
import hu.cherubits.wonderjam.entities.NetworkNodeEntity;
import hu.cherubits.wonderjam.entities.NetworkNodeType;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lordoftheflies
 */
@RestController
@RequestMapping(path = "/authentication")
public class AuthenticationService {

    private static final Logger LOG = Logger.getLogger(AuthenticationService.class.getName());

    @Autowired
    private UaaMailSender uaaMailer;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private NetworkTreeRepository networkRepository;
    @Autowired
    private MailBoxRepository mailBoxRepository;

    private void apply(RegistrationDto dto, AccountEntity entity) {
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setPreferredLanguage((dto.getLocale() == null) ? "en" : dto.getLocale());
        if (dto.getCode() != null && !"".equals(dto.getCode())) {
            entity.setId(UUID.fromString(dto.getCode()));
        } else {
            entity.setId(UUID.randomUUID());
        }
    }

    private void apply(AccountEntity entity, SessionDto dto) {
        dto.setDisplayName(entity.getName());
        dto.setToken(entity.getId().toString());
//        dto.setPowerUser(NetworkNodeType.ADMIN == accountRepository.findRoleById(entity.getId()));
        dto.setToken(entity.getId().toString());
        dto.setPreferredLanguage(entity.getPreferredLanguage());
    }

    public static final String SYSTEM_SENDER_RECIPIENT = "topflavon@digitaldefense.hu";
    public static final String ACTIVATION_CODE_MESSAGE = "topflavon@digitaldefense.hu";

    @Value("${mail.passwordreset}")
    private String passwordResetLink;

    @Value("${mail.organization}")
    private String organizationName;

    @Value("${mail.application}")
    private String applicationName;

    @CrossOrigin(origins = "*")
    @RequestMapping(
            path = "/{id}/profile",
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET)
    public ResponseEntity profile(@PathVariable("id") String id) {
        try {
            AccountEntity entity = accountRepository.findOne(UUID.fromString(id));

            RegistrationDto dto = new RegistrationDto(entity.getId(), entity.getPhone(), entity.getName(), entity.getEmail(), null);

            LOG.log(Level.INFO, "Get registration profile for user {0}", id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            path = "/signon",
            consumes = {
                MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST)
    public ResponseEntity<SessionDto> signOn(@RequestBody(required = true) RegistrationDto dto) throws AccountNotFoundException, RegistrationCodeAlreadyUsedException {
        if (accountRepository.count() == 0) {
            AccountEntity accountEntity = new AccountEntity();
            apply(dto, accountEntity);
            accountEntity = accountRepository.save(accountEntity);

            NetworkNodeEntity nodeEntity = new NetworkNodeEntity();
            nodeEntity.setActive(true);
            nodeEntity.setContact(accountEntity);
            networkRepository.save(nodeEntity);

            accountEntity.setNode(nodeEntity);
            accountEntity.setState(NetworkNodeType.ADMIN);
            accountRepository.save(accountEntity);

            MailBoxEntity mbEntity = new MailBoxEntity();
            mbEntity.setOwner(nodeEntity);
            mailBoxRepository.save(mbEntity);

            nodeEntity.setMailBox(mbEntity);
            networkRepository.save(nodeEntity);

            LOG.log(Level.INFO, "Sign-on principal user {0}", dto.toString());

            return new ResponseEntity<>(new SessionDto(
                    accountRepository.findRoleById(accountEntity.getId()),
                    0,
                    accountEntity.getId().toString(),
                    accountEntity.getName(),
                    accountEntity.getPreferredLanguage()
            ),
                    HttpStatus.OK);
        } else if (dto.getCode() != null && !accountRepository.exists(UUID.fromString(dto.getCode()))) {
            LOG.log(Level.INFO, "Sign-on failed account {0} not found.", dto.getEmail());
            throw new AccountNotFoundException();
        } else {
            UUID accountId = UUID.fromString(dto.getCode());
            AccountEntity accountEntity = accountRepository.findOne(accountId);
            NetworkNodeEntity nodeEntity = networkRepository.findByAccount(accountId);
//            if (nodeEntity.getActive()) {
//                throw new RegistrationCodeAlreadyUsedException();
//            } else {
            apply(dto, accountEntity);
            accountRepository.save(accountEntity);

            nodeEntity.setActive(true);
            networkRepository.save(nodeEntity);

            LOG.log(Level.INFO, "Sign-on user {0}", dto.getEmail());
            return new ResponseEntity<>(new SessionDto(
                    accountRepository.findRoleById(accountEntity.getId()),
                    networkRepository.findByAccount(accountEntity.getId()).getCodes(),
                    accountEntity.getId().toString(),
                    accountEntity.getName(),
                    accountEntity.getPreferredLanguage()
            ), HttpStatus.OK);
//            }
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            path = "/signin",
            consumes = {
                MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST)
    public ResponseEntity<SessionDto> signIn(@RequestBody(required = true) CredentialsDto dto) throws CredentialsException {
        SessionDto result = new SessionDto();
        AccountEntity accountEntity = accountRepository.findByCredentials(dto.getEmail(), dto.getPassword());
        if (accountEntity == null) {
            LOG.log(Level.INFO, "Sign-in failed for {0}", dto.getEmail());
            throw new CredentialsException();
        } else {
            LOG.log(Level.INFO, "Sign-in user {0}", dto.getEmail());
            apply(accountEntity, result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            path = "/signout",
            consumes = {
                MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST)
    public ResponseEntity<SessionDto> signOut(@RequestBody(required = true) SessionDto session) {
        LOG.log(Level.INFO, "Sign-out token {0}", session.getToken());
        session.setToken(null);
        return new ResponseEntity<>(session, HttpStatus.UNAUTHORIZED);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            path = "/resetpassword",
            consumes = {
                MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST)
    public ResponseEntity resetPassword(@RequestBody(required = true) PasswordResetDto model) {
        AccountEntity accountEntity = accountRepository.findByEmail(model.getEmail());
        LOG.log(Level.INFO, "Reset password for {0} [email={1}]", new Object[]{accountEntity.getName(), model.getEmail()});
        try {
            uaaMailer.sendForgottenPasswordEmail(
                    accountEntity.getEmail(),
                    accountEntity.getName(),
                    applicationName,
                    organizationName,
                    String.format(passwordResetLink, accountEntity.getId().toString()),
                    accountEntity.getPreferredLanguage());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            path = "/session",
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET)
    public ResponseEntity session(
            @RequestParam(value = "token", required = false, defaultValue = DEFAULT_TOKEN_EMPTY) String token
    ) throws AccountNotFoundException, AccesDeniedException {
        if (accountRepository.count() == 0) {
            SessionDto dto = new SessionDto();
            dto.setToken(token);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(dto);
        } else if (token == null || token.equals(DEFAULT_TOKEN_EMPTY)) {
            LOG.log(Level.INFO, "Session fetching failed for {0}", token);
            throw new AccesDeniedException("Empty user token");
        } else if (!accountRepository.exists(UUID.fromString(token))) {
            LOG.log(Level.INFO, "Session fetching failed for {0}", token);
            throw new AccountNotFoundException();
        } else {
            SessionDto dto = new SessionDto();
            LOG.log(Level.INFO, "Session fetched for {0}", token);
            AccountEntity accountEntity = accountRepository.findOne(UUID.fromString(token));
            apply(accountEntity, dto);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    private static final String DEFAULT_TOKEN_EMPTY = "empty";
}
