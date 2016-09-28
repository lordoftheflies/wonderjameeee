/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.model;

/**
 *
 * @author lordoftheflies
 */
public class PasswordResetDto {

    public PasswordResetDto() {
    }

    public PasswordResetDto(String email) {
        this.email = email;
    }

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
