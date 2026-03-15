package com.abacus.franchise.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.dto.KitRequestsDetail;
import com.abacus.franchise.model.KitRequests;

@Repository
public interface KitRequestsRepository extends JpaRepository<KitRequests, UUID> {

	@Query(value = """
		SELECT 
		   kr.kit_request_id,
		   kr.request_status,
		   kr.placed_date,
		   kr.dispatched_date,
		   kr.delivered_date, 
		   a.line1,
		   a.landmark,
		   a.city,
		   a.pincode,
		   a.country_name,
		   s.state_name,
		   d.district_name,
		   koi.kit_count,
		   c.course_name
		FROM kit_requests kr 
			JOIN address a ON a.address_id = kr.address_id AND a.is_active = true
			JOIN state s ON s.state_id = a.state_id AND s.is_active = true
			JOIN district d ON d.district_id = a.district_id AND d.is_active = true
			JOIN kit_order_item koi ON koi.kit_request_id = kr.kit_request_id
			JOIN course c on c.course_id = koi.course_id AND c.is_active = true
		WHERE kr.franchise_id = :franchiseId and kr.is_active = true;
	""",nativeQuery = true)
	List<KitRequestsDetail> getAllKitRequestsDetailByFranchiseId(String franchiseId);
	
}
