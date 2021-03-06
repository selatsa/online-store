package com.sonacode.store.service;

import com.sonacode.store.domain.Shipment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Shipment.
 */
public interface ShipmentService {

    /**
     * Save a shipment.
     *
     * @param shipment the entity to save
     * @return the persisted entity
     */
    Shipment save(Shipment shipment);

    /**
     * Get all the shipments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Shipment> findAll(Pageable pageable);

    Page<Shipment> findAllByShipmentCustomerUserLogin(String login, Pageable pageable);
    
    /**
     * Get the "id" shipment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Shipment> findOne(Long id);
    
    Optional<Shipment> findOneByIdAndShipmentCustomerUserLogin(Long id, String login);

    /**
     * Delete the "id" shipment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
