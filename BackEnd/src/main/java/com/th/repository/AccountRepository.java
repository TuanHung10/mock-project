package com.th.repository;

import com.th.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

    boolean existsByUsername(String username);

    Account findByUsername(String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM Account WHERE id IN(:ids)")
    void deleteByIds(@Param("ids") List<Integer> ids);



}
