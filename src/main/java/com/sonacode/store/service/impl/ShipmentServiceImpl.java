package com.sonacode.store.service.impl;

import com.sonacode.store.service.ShipmentService;
import com.sonacode.store.domain.Shipment;
import com.sonacode.store.repository.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import com.sonacode.store.security.SecurityUtils;
import com.sonacode.store.security.AuthoritiesConstants;

/**
 * Service Implementation for managing Shipment.
 */
@Service
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

    private final Logger log = LoggerFactory.getLogger(ShipmentServiceImpl.class);

    private final ShipmentRepository shipmentRepository;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    /**
     * Save a shipment.
     *
     * @param shipment the entity to save
     * @return the persisted entity
     */
    @Override
    public Shipment save(Shipment shipment) {
        log.debug("Request to save Shipment : {}", shipment);
        return shipmentRepository.save(shipment);
    }

    /**
     * Get all the shipments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */

    @Override
    @Transactional(readOnly = true)
    public Page<Shipment> findAll(Pageable pageable) {
        log.debug("Request to get all Shipmnents");
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return shipmentRepository.findAll(pageable);
        } else {
            return shipmentRepository.findAllByInvoiceOrderCustomerUserLogin(SecurityUtils.getCurrentUserLogin().get(), pageable);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Shipment> findAllByShipmentCustomerUserLogin(String login, Pageable pageable) {
        log.debug("Request to get all Shipmnents by user login");
        return shipmentRepository.findAllByInvoiceOrderCustomerUserLogin(login, pageable);
    }

    /**
     * Get one shipment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Shipment> findOne(Long id) {
        log.debug("Request to get Shipment : {}", id);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return shipmentRepository.findById(id);
        } else {
            return shipmentRepository.findOneByIdAndInvoiceOrderCustomerUserLogin(id, SecurityUtils.getCurrentUserLogin().get() );
        }
    }
 
    @Override
    @Transactional(readOnly = true)
    public Optional<Shipment> findOneByIdAndShipmentCustomerUserLogin(Long id, String login) {
        log.debug("Request to get Shipment for login user : {}", id);
        return shipmentRepository.findOneByIdAndInvoiceOrderCustomerUserLogin(id, login );
    }

    /**
     * Delete the shipment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shipment : {}", id);        shipmentRepository.deleteById(id);
    }
}
