package com.abacus.franchise.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Query(value = "SELECT address_id FROM address where address_id = :addressId AND is_active = true", nativeQuery = true)
    public UUID getAddressById(@Param("addressId") String addressId);

    Optional<Address> findByUserId(UUID userId);
}
