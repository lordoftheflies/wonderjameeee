/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.dal;

import hu.cherubits.wonderjam.entities.VideoContentEntity;
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
public interface VideoContentRepository extends PagingAndSortingRepository<VideoContentEntity, UUID> {

    @Override
    public List<VideoContentEntity> findAll(Sort sort);

    @Override
    public List<VideoContentEntity> findAll(Iterable<UUID> itrbl);

    @Override
    public List<VideoContentEntity> findAll();

    public List<VideoContentEntity> findByParent(@Param("parentId") UUID parentId);
}
