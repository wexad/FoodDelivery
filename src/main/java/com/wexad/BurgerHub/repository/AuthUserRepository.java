package com.wexad.BurgerHub.repository;

import com.wexad.BurgerHub.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);

    @Query("SELECT au.id FROM AuthUser au WHERE au.username = :username")
    Long getIdWithUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("update AuthUser a set a.deleted = true where a.id = ?1")
    void updateDeletedBy(Long id);

    @Query("SELECT a.deleted FROM AuthUser a WHERE a.username = ?1")
    boolean isDeleted(String username);

    @Transactional
    @Modifying
    @Query("update AuthUser a set a.password = ?1 where a.id = ?2")
    void updatePasswordByPassword( String password, Long id);
}