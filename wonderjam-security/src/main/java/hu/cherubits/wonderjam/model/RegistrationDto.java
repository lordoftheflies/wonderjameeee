/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.model;

import java.util.UUID;

/**
 *
 * @author lordoftheflies
 */
public class RegistrationDto {

    public RegistrationDto() {
    }

    public RegistrationDto(UUID accountId, String phone, String name, String email, String password) {
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountId = accountId.toString();
    }

    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String activationCode) {
        this.code = activationCode;
    }

    private String locale;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String preferredLanguage) {
        this.locale = preferredLanguage;
    }
}
