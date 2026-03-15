package com.abacus.franchise.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.dto.ProductDetail;
import com.abacus.franchise.model.Product;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
	
	@Query(value = "SELECT product_id,product_name,quantity FROM product where is_active = true",nativeQuery = true)
	List<ProductDetail> getAllProductDetail();
	
	@Query(value = "SELECT product_id,product_name,quantity FROM product where product_id = :productId AND is_active = true",nativeQuery = true)
	ProductDetail getProductIdById(@Param("productId") String productId);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE product SET quantity = :qty WHERE product_id = :productId",nativeQuery = true)
	int updateProductQuantityById(@Param("productId") String productId,@Param("qty") int qty);

}
