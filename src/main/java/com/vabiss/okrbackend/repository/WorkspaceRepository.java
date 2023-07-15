package com.vabiss.okrbackend.repository;

import com.vabiss.okrbackend.entity.Organization;
import com.vabiss.okrbackend.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkspaceRepository extends JpaRepository<Workspace, Integer> {

    List<Workspace> findWorkspacesByOrganization(Organization organization);

}
