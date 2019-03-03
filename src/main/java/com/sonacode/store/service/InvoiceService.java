package com.sonacode.store.service;

import com.sonacode.store.domain.Invoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Invoice.
 */
public interface InvoiceService {

    /**
     * Save a invoice.
     *
     * @param invoice the entity to save
     * @return the persisted entity
     */
    Invoice save(Invoice invoice);

    /**
     * Get all the invoices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Invoice> findAll(Pageable pageable);

    Page<Invoice> findAllByOrderCustomerUserLogin(String login, Pageable pageable);
        /**
     * Get the "id" invoice.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Invoice> findOne(Long id);
    
    Optional<Invoice> findOneByIdAndOrderCustomerUserLogin(Long id, String login);

    /**
     * Delete the "id" invoice.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
