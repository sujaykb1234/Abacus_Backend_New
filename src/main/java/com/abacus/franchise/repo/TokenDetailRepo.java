package com.abacus.franchise.repo;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.abacus.franchise.model.TokenDetail;

@Repository
public interface TokenDetailRepo extends JpaRepository<TokenDetail, UUID> {
	
	Optional<TokenDetail> findByUserId(UUID userId);

}
