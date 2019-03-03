package com.sonacode.store.repository;

import com.sonacode.store.domain.Shipment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
/**
 * Spring Data  repository for the Shipment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    
    Page<Shipment> findAllByInvoiceOrderCustomerUserLogin(String login, Pageable pageable);
    
    Optional<Shipment> findOneByIdAndInvoiceOrderCustomerUserLogin(Long id, String login);
}
