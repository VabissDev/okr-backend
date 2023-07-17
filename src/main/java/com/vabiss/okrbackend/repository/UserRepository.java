package com.vabiss.okrbackend.repository;

import com.vabiss.okrbackend.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("select u from User u where u.id=?1 ")
    User getById(@Param(value = "id") int userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM workspace_user WHERE user_id = :userId and workspace_id = :workspaceId", nativeQuery = true)
    void deleteMemberAndUser(@Param("userId") int userId, @Param("workspaceId") int workspaceId);
}