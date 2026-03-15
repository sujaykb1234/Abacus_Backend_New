package com.abacus.franchise.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.model.Roles;


@Repository
public interface RolesRepository extends JpaRepository<Roles, UUID> {
	
	@Query(value="SELECT role_name FROM roles where role_id = :roleId and is_active =  true",nativeQuery = true)
	String checkRoleIdPresentOrNot(@Param("roleId") String roleId);

}
