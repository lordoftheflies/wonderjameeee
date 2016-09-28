/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.dal;

import hu.cherubits.wonderjam.entities.AccountEntity;
import hu.cherubits.wonderjam.entities.NetworkNodeEntity;
import hu.cherubits.wonderjam.entities.NetworkNodeType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lordoftheflies
 */
@Repository
public interface NetworkTreeRepository extends PagingAndSortingRepository<NetworkNodeEntity, Long> {

    List<AccountEntity> findContactsOfChildNodes(@Param("accountId") UUID accountId);
    List<AccountEntity> findContactsOfRootNodes();
    List<NetworkNodeEntity> findChildren(@Param("accountId") UUID accountId);
    List<NetworkNodeEntity> findChildrenNodes(@Param("nodeId") Long nodeId);
    NetworkNodeEntity findByAccount(@Param("accountId") UUID accountId);
    Boolean isRoot(@Param("accountId") UUID accountId);
    Boolean isRootNode(@Param("nodeId") Long nodeId);
}
