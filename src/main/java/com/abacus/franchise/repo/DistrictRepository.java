package com.abacus.franchise.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.model.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, UUID> {

	@Query(value = "SELECT * FROM district where state_state_id = ?",nativeQuery =  true)
	List<District> getAllDistrictByStateId(Long stateId);
	
	@Query(value="SELECT COUNT(*) FROM district WHERE district_id = :districtId AND is_active = true",nativeQuery = true)
	int checkDistrictIdPresentOrNot(@Param("districtId") String districtId);

	
	
}
