/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitaldefense.christeam.dal;

import com.digitaldefense.christeam.entities.TextContentEntity;
import com.digitaldefense.christeam.entities.VideoContentEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author lordoftheflies
 */
public interface TextContentRepository extends PagingAndSortingRepository<TextContentEntity, UUID> {

    @Override
    public List<TextContentEntity> findAll(Sort sort);

    @Override
    public List<TextContentEntity> findAll(Iterable<UUID> itrbl);

    @Override
    public List<TextContentEntity> findAll();

    public List<TextContentEntity> findByParent(@Param("parentId") UUID parentId);
}
