package com.vabiss.okrbackend.repository;

import com.vabiss.okrbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
