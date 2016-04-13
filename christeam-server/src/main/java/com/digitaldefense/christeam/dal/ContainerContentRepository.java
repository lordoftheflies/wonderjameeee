/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.dal;

import com.digitaldefense.christeam.entities.ContainerContentEntity;
import com.digitaldefense.christeam.entities.ContentEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author lordoftheflies
 */
public interface ContainerContentRepository extends PagingAndSortingRepository<ContainerContentEntity, UUID> {

    @Override
    public List<ContainerContentEntity> findAll(Sort sort);

    @Override
    public List<ContainerContentEntity> findAll(Iterable<UUID> itrbl);

    @Override
    public List<ContainerContentEntity> findAll();

    public List<ContainerContentEntity> findByParent(@Param("parentId") UUID parentId);
//    
//    public List<ContentEntity> findByChildren(@Param("childId") UUID childId);
    
    public List<ContentEntity> findRoots();
}
