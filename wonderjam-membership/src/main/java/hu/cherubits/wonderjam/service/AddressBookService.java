/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.service;

import hu.cherubits.wonderjam.exceptions.AccountNotExistException;
import hu.cherubits.wonderjam.exceptions.InsufficientCodesException;
import hu.cherubits.wonderjam.model.ContactDto;
import hu.cherubits.wonderjam.model.ContactInfoDto;
import hu.cherubits.wonderjam.model.PermissionChangeDto;
import hu.cherubits.wonderjam.dal.AccountRepository;
import hu.cherubits.wonderjam.dal.MailBoxRepository;
import hu.cherubits.wonderjam.dal.NetworkTreeRepository;
import hu.cherubits.wonderjam.entities.AccountEntity;
import hu.cherubits.wonderjam.entities.MailBoxEntity;
import hu.cherubits.wonderjam.entities.NetworkNodeEntity;
import hu.cherubits.wonderjam.entities.NetworkNodeType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(path = "/addressbook",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AddressBookService {

    private static final Logger LOG = Logger.getLogger(AddressBookService.class.getName());

    @Autowired
    private NetworkTreeRepository networkTreeRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MailBoxRepository mbRepository;

    @Autowired
    private UaaMailSender mailSender;

    @Value("${mail.frontend.installer.web}")
    private String webFrontentLink;

    @Value("${mail.frontend.installer.android}")
    private String androidInstallerLink;

    @Value("${mail.organization}")
    private String organizationName;

    @Value("${mail.application}")
    private String applicationName;

    @RequestMapping(path = "/{id}/contacts",
            method = RequestMethod.GET)
    public List<ContactInfoDto> getContactsOfMember(@PathVariable("id") String accountId) {
        LOG.log(Level.INFO, "Retrieve contacts of account[{0}] ...", accountId);
        return accountRepository.findAll().stream()
                .map((AccountEntity a) -> new ContactInfoDto(
                        a.getId(),
                        a.getName(),
                        a.getEmail()))
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/roots",
            method = RequestMethod.GET)
    public List<ContactDto> getRoots() {
        return accountRepository.findRootAccounts().stream()
                .map((AccountEntity a) -> new ContactDto(
                        a.getId(),
                        (networkTreeRepository.isRoot(a.getId())) ? null : accountRepository.getParent(a.getId()).getId(),
                        a.getName(),
                        a.getEmail(),
                        nullSafeGet(networkTreeRepository.findByAccount(a.getId()).getCodes()),
                        a.getPhone(),
                        accountRepository.findRoleById(a.getId())))
                .collect(Collectors.toList());
    }

    private int nullSafeGet(Integer i) {
        return (i == null) ? 0 : i;
    }

    @RequestMapping(path = "/all",
            method = RequestMethod.GET)
    public List<ContactDto> getAll() {
        return accountRepository.findAll().stream()
                .map((AccountEntity a) -> new ContactDto(
                        a.getId(),
                        (networkTreeRepository.isRoot(a.getId())) ? null : accountRepository.getParent(a.getId()).getId(),
                        a.getName(),
                        a.getEmail(),
                        nullSafeGet(networkTreeRepository.findByAccount(a.getId()).getCodes()),
                        a.getPhone(),
                        accountRepository.findRoleById(a.getId())))
                .collect(Collectors.toList());
    }

    private List<ContactDto> retrieveParent(AccountEntity a, List<ContactDto> accumulator) {
        accumulator.add(new ContactDto(
                a.getId(),
                (networkTreeRepository.isRoot(a.getId())) ? null : accountRepository.getParent(a.getId()).getId(),
                a.getName(),
                a.getEmail(),
                nullSafeGet(networkTreeRepository.findByAccount(a.getId()).getCodes()),
                a.getPhone(),
                accountRepository.findRoleById(a.getId())));
        if (networkTreeRepository.isRoot(a.getId())) {
            return accumulator;
        } else {
            return retrieveParent(accountRepository.getParent(a.getId()), accumulator);
        }
    }

    @RequestMapping(path = "/genocide",
            method = RequestMethod.GET)
    public List<ContactDto> getParents(@RequestParam(name = "childId", required = true) String childId) {
        if (ROOT_KEY.equals(childId)) {
            return new ArrayList<>();
        } else {

            final List<ContactDto> results = this.retrieveParent(accountRepository.findOne(UUID.fromString(childId)), new ArrayList<>());
            Collections.reverse(results);
            return results;
        }
    }

    @RequestMapping(path = "/children",
            method = RequestMethod.GET)
    public List<ContactDto> getChildren(@RequestParam(name = "parentId", required = true) String parentId) throws AccountNotExistException {
        if (ROOT_KEY.equals(parentId)) {
            return networkTreeRepository.findContactsOfRootNodes().stream()
                    .map((AccountEntity a) -> new ContactDto(
                            a.getId(),
                            (networkTreeRepository.isRoot(a.getId())) ? null : accountRepository.getParent(a.getId()).getId(),
                            a.getName(),
                            a.getEmail(),
                            nullSafeGet(networkTreeRepository.findByAccount(a.getId()).getCodes()),
                            a.getPhone(),
                            accountRepository.findRoleById(a.getId())))
                    .collect(Collectors.toList());
        } else {

            final UUID id = UUID.fromString(parentId);
            if (accountRepository.exists(id)) {
                return networkTreeRepository.findContactsOfChildNodes(id).stream()
                        .map((AccountEntity a) -> new ContactDto(
                                a.getId(),
                                (networkTreeRepository.isRoot(a.getId())) ? null : accountRepository.getParent(a.getId()).getId(),
                                a.getName(),
                                a.getEmail(),
                                nullSafeGet(networkTreeRepository.findByAccount(a.getId()).getCodes()),
                                a.getPhone(),
                                accountRepository.findRoleById(a.getId())))
                        .collect(Collectors.toList());
            } else {
                throw new AccountNotExistException(parentId);
            }
        }
    }
    public static final String ROOT_KEY = "empty";

    @RequestMapping(path = "/save",
            method = RequestMethod.POST)
    public void save(@RequestBody ContactDto dto) throws AccountNotExistException {
        if (dto.getId() == null || !accountRepository.exists(dto.getId())) {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setEmail(dto.getEmail());
            accountEntity.setName(dto.getName());
            accountEntity.setPhone(dto.getPhone());
            accountEntity.setPreferredLanguage(dto.getPreferredLanguage());
            // TODO: generate a strong password.
            accountEntity.setPassword(null);

            accountEntity = accountRepository.save(accountEntity);

            NetworkNodeEntity nodeEntity = new NetworkNodeEntity();
            nodeEntity.setActive(true);
            nodeEntity.setContact(accountEntity);
            nodeEntity.setCodes(dto.getCodes());
            accountEntity.setState(NetworkNodeType.valueOf(dto.getRole()));
            networkTreeRepository.save(nodeEntity);

            NetworkNodeEntity parentEntity = networkTreeRepository.findByAccount(dto.getParent());

            nodeEntity.setParent(parentEntity);
            if (parentEntity.getChildren() == null) {
                parentEntity.setChildren(new ArrayList<>());
            }
            parentEntity.getChildren().add(nodeEntity);
            networkTreeRepository.save(nodeEntity);
            networkTreeRepository.save(parentEntity);

            accountEntity.setNode(nodeEntity);
            accountRepository.save(accountEntity);

            MailBoxEntity mbEntity = new MailBoxEntity();
            mbEntity.setOwner(nodeEntity);
            mbRepository.save(mbEntity);

            nodeEntity.setMailBox(mbEntity);
            networkTreeRepository.save(nodeEntity);

            AccountEntity parentAccountEntity = accountRepository.findOne(dto.getParent());

            LOG.log(Level.INFO, "Created new member for {0}", dto.getEmail());
            LOG.log(Level.INFO, "\t- Account: {0}", accountEntity.getId());
            LOG.log(Level.INFO, "\t- Node: {0}", nodeEntity.getId());
            LOG.log(Level.INFO, "\t- Mail-box: {0}", mbEntity.getId());

            if (!NetworkNodeType.GROUP.equals(accountRepository.findRoleById(accountEntity.getId()))) {
                mailSender.sendRegistrationActivationEmail(parentAccountEntity.getName(), accountEntity.getEmail(),
                        accountEntity.getName() == null ? "Mr/Ms" : accountEntity.getName(),
                        applicationName,
                        organizationName,
                        String.format(webFrontentLink, accountEntity.getId().toString()),
                        String.format(androidInstallerLink, accountEntity.getId().toString()),
                        accountEntity.getPreferredLanguage());
            }
        }
    }

    @RequestMapping(path = "/{id}/profile",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity profile(@PathVariable("id") String id) {
        try {
            AccountEntity entity = accountRepository.findOne(UUID.fromString(id));
            ContactDto dto = new ContactDto(null, 0,
                    entity.getPhone(),
                    entity.getId(),
                    entity.getName(),
                    entity.getEmail(),
                    true,
                    entity.getPreferredLanguage());

            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/promote",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public void promote(@RequestBody PermissionChangeDto dto) throws InsufficientCodesException, AccountNotExistException {
        // Source account validation.
        if (!accountRepository.exists(dto.getOwner())) {
            LOG.log(Level.WARNING, "Source account {0} not exsists.", dto.getOwner());
            throw new AccountNotExistException();
        }
        // Destination account validation.
        if (!accountRepository.exists(dto.getSubject())) {
            LOG.log(Level.WARNING, "Destination account {0} not exsists.", dto.getSubject());
            throw new AccountNotExistException();
        }

        // Retrieve entities from database.
        NetworkNodeEntity source = networkTreeRepository.findByAccount(dto.getOwner());
        AccountEntity sourceEntity = accountRepository.findOne(dto.getOwner());
        NetworkNodeEntity destination = networkTreeRepository.findByAccount(dto.getSubject());
        AccountEntity destinationEntity = accountRepository.findOne(dto.getSubject());

        if (!destination.getActive() || NetworkNodeType.GROUP.equals(destinationEntity.getState())) {
            throw new IllegalArgumentException("Group could not be promoted to admin.");
        }

        if (!source.getActive() || !NetworkNodeType.ADMIN.equals(sourceEntity.getState())) {
            throw new IllegalArgumentException("No permission to promote user.");
        }

        destinationEntity.setState(NetworkNodeType.ADMIN);
        networkTreeRepository.save(destination);

        LOG.log(Level.INFO, "Promote user {0} to admin by {1}", new Object[]{
            dto.getSubject(),
            dto.getOwner()
        });
    }

    @RequestMapping(path = "/demote",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public void demote(@RequestBody PermissionChangeDto dto) throws InsufficientCodesException, AccountNotExistException {
        // Source account validation.
        if (!accountRepository.exists(dto.getOwner())) {
            LOG.log(Level.WARNING, "Source account {0} not exsists.", dto.getOwner());
            throw new AccountNotExistException();
        }
        // Destination account validation.
        if (!accountRepository.exists(dto.getSubject())) {
            LOG.log(Level.WARNING, "Destination account {0} not exsists.", dto.getSubject());
            throw new AccountNotExistException();
        }

        // Retrieve entities from database.
        NetworkNodeEntity source = networkTreeRepository.findByAccount(dto.getOwner());
        NetworkNodeEntity destination = networkTreeRepository.findByAccount(dto.getSubject());
        AccountEntity sourceEntity = accountRepository.findOne(dto.getOwner());
        AccountEntity destinationEntity = accountRepository.findOne(dto.getSubject());

        if (!destination.getActive() || NetworkNodeType.GROUP.equals(destinationEntity.getState())) {
            throw new IllegalArgumentException("Group could not be demoted to user.");
        }

        if (!source.getActive() || !NetworkNodeType.ADMIN.equals(sourceEntity.getState())) {
            throw new IllegalArgumentException("No permission to demote admin.");
        }

        destinationEntity.setState(NetworkNodeType.USER);
        networkTreeRepository.save(destination);

        LOG.log(Level.INFO, "Demote user {0} to user by {1}", new Object[]{
            dto.getSubject(),
            dto.getOwner()
        });
    }

    @RequestMapping(path = "/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public void erase(@RequestBody PermissionChangeDto dto) throws InsufficientCodesException, AccountNotExistException {
        NetworkNodeEntity destination = networkTreeRepository.findByAccount(dto.getSubject());
        NetworkNodeEntity source = networkTreeRepository.findByAccount(dto.getOwner());
        destination.setActive(false);
        networkTreeRepository.save(destination);

        LOG.log(Level.INFO, "{0} delete member {1}", new Object[]{
            dto.getOwner(),
            dto.getSubject()
        });
    }
}
