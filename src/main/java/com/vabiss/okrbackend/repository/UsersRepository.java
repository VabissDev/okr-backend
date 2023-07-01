package com.vabiss.okrbackend.repository;

import com.vabiss.okrbackend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Integer> {
}
