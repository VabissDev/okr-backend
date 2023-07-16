package com.vabiss.okrbackend.repository;

import com.vabiss.okrbackend.entity.Organization;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkspaceRepository extends JpaRepository<Workspace, Integer> {

    List<Workspace> findWorkspacesByOrganization(Organization organization);

    @Query("select w from Workspace w where w.id=?1 ")
    Workspace getById(@Param(value = "id") int workspaceId);

}
