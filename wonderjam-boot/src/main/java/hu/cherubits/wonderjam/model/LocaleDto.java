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
public class LocaleDto {

    public LocaleDto() {
    }

    public LocaleDto(String code, String name) {
        this.code = code;
        this.name = name;
    }

    
    
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
