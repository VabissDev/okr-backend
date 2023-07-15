package com.vabiss.okrbackend.repository;

import com.vabiss.okrbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("select u from User u where u.id=?1 and u.organization.name=?2")
    User getByOrganizationName(@Param(value = "id") int userId, @Param(value = "name") String organizationName);

}