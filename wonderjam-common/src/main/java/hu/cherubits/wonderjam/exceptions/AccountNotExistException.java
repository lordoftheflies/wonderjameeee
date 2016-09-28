/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.exceptions;

/**
 *
 * @author lordoftheflies
 */
public class AccountNotExistException extends Exception {

    public AccountNotExistException(String parentId) {
        super("Account not exsist.");
    }

    public AccountNotExistException() {
        super("Account not exsist.");
    }

    private String parentId;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
