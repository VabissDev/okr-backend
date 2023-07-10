package com.vabiss.okrbackend.repository;

import com.vabiss.okrbackend.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
//    Optional<Organization> findByOrganizationId(int id);
    Optional<Organization> findByName(String name);
    List<Organization> findAll();

}
