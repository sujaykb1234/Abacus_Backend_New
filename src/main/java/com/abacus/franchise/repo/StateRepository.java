package com.abacus.franchise.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, UUID> {

	@Query(value="select * from state",nativeQuery = true)
	public List<State> getAllState();
	
	@Query(value="SELECT COUNT(*) FROM state WHERE state_id = :stateId AND is_active = true",nativeQuery = true)
	int checkStateIdPresentOrNot(@Param("stateId") String stateId);

}
