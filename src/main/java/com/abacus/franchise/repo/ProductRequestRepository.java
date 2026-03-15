package com.abacus.franchise.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.dto.ProductRequestDetail;
import com.abacus.franchise.model.ProductRequest;

@Repository
public interface ProductRequestRepository extends JpaRepository<ProductRequest, UUID> {

	@Query(value = """
		SELECT 
		   pr.quantity,
		   pr.request_date,
		   pr.request_status,
		   p.product_name
		FROM product_request pr 
		JOIN product p ON p.product_id = pr.product_id AND p.is_active = true
		WHERE pr.franchise_id = :franchiseId AND  pr.is_active = true;
    """,nativeQuery = true)
	
	List<ProductRequestDetail> getAllProductRequest(String franchiseId);
	
	
}
