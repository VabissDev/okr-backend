package com.vabiss.okrbackend.repository;

import com.vabiss.okrbackend.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    Organization findByOrganizationId(int id);
    List<Organization> findAll();

}
