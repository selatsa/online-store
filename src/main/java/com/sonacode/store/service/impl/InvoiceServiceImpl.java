package com.sonacode.store.service.impl;

import com.sonacode.store.service.InvoiceService;
import com.sonacode.store.domain.Invoice;
import com.sonacode.store.repository.InvoiceRepository;
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
 * Service Implementation for managing Invoice.
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Save a invoice.
     *
     * @param invoice the entity to save
     * @return the persisted entity
     */
    @Override
    public Invoice save(Invoice invoice) {
        log.debug("Request to save Invoice : {}", invoice);
        return invoiceRepository.save(invoice);
    }

    /**
     * Get all the invoices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Invoice> findAll(Pageable pageable) {
        log.debug("Request to get all Invoices");
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return invoiceRepository.findAll(pageable);
        } else {
            return invoiceRepository.findAllByOrderCustomerUserLogin(SecurityUtils.getCurrentUserLogin().get(), pageable);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Invoice> findAllByOrderCustomerUserLogin(String login, Pageable pageable) {
        log.debug("Request to get all Invoices by user login");
        return invoiceRepository.findAllByOrderCustomerUserLogin(login, pageable);
    }
    /**
     * Get one invoice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> findOne(Long id) {
        log.debug("Request to get Invoice : {}", id);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return invoiceRepository.findById(id);
        } else {
            return invoiceRepository.findOneByIdAndOrderCustomerUserLogin(id, SecurityUtils.getCurrentUserLogin().get() );
        }
    }
 
    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> findOneByIdAndOrderCustomerUserLogin(Long id, String login) {
        log.debug("Request to get Invoice for login user : {}", id);
        return invoiceRepository.findOneByIdAndOrderCustomerUserLogin(id, login );
    }
    /**
     * Delete the invoice by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Invoice : {}", id);        invoiceRepository.deleteById(id);
    }
}
