/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.dal;

import hu.cherubits.wonderjam.entities.ImageContentEntity;
import hu.cherubits.wonderjam.entities.ReferenceContentEntity;
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
public interface LinkContentRepository extends PagingAndSortingRepository<ReferenceContentEntity, UUID> {

    @Override
    public List<ReferenceContentEntity> findAll(Sort sort);

    @Override
    public List<ReferenceContentEntity> findAll(Iterable<UUID> itrbl);

    @Override
    public List<ReferenceContentEntity> findAll();

//    public List<ReferenceContentEntity> findByParent(@Param("parentId") UUID parentId);

}
