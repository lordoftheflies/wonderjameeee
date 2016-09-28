/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.dal;

import hu.cherubits.wonderjam.entities.ContentEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lordoftheflies
 */
@Repository
public interface ContentRepository extends PagingAndSortingRepository<ContentEntity, UUID> {

    @Override
    public List<ContentEntity> findAll(Sort sort);

    @Override
    public List<ContentEntity> findAll(Iterable<UUID> itrbl);

    @Override
    public List<ContentEntity> findAll();

    

    public ContentEntity findByChild(@Param("childId") UUID childId);

    public List<ContentEntity> findByParent(@Param("parentId") UUID fromString);

    
}
