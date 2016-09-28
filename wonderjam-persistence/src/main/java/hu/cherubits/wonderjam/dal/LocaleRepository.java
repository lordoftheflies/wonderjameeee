/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.dal;

import hu.cherubits.wonderjam.entities.LocaleEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lordoftheflies
 */
@Repository
public interface LocaleRepository extends CrudRepository<LocaleEntity, String> {

    @Override
    List<LocaleEntity> findAll();

}
