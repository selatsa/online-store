package com.sonacode.store.service.impl;

import com.sonacode.store.service.ProductOrderService;
import com.sonacode.store.domain.ProductOrder;
import com.sonacode.store.repository.ProductOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sonacode.store.security.AuthoritiesConstants;
import org.springframework.transaction.annotation.Transactional;

import com.sonacode.store.security.SecurityUtils;

import java.util.Optional;

/**
 * Service Implementation for managing ProductOrder.
 */
@Service
@Transactional
public class ProductOrderServiceImpl implements ProductOrderService {

    private final Logger log = LoggerFactory.getLogger(ProductOrderServiceImpl.class);

    private final ProductOrderRepository productOrderRepository;

    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    /**
     * Save a productOrder.
     *
     * @param productOrder the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductOrder save(ProductOrder productOrder) {
        log.debug("Request to save ProductOrder : {}", productOrder);
        return productOrderRepository.save(productOrder);
    }

    /**
     * Get all the productOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductOrder> findAll(Pageable pageable) {
        log.debug("Request to get all ProductOrders");
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return productOrderRepository.findAll(pageable);
        } else {
            return productOrderRepository.findAllByCustomerUserLogin(SecurityUtils.getCurrentUserLogin().get(), pageable);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ProductOrder> findAllByCustomerUserLogin(String login, Pageable pageable) {
        log.debug("Request to get all ProductOrders by user login");
        return productOrderRepository.findAllByCustomerUserLogin(login, pageable);
    }
      
    /**
     * Get one productOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductOrder> findOne(Long id) {
        log.debug("Request to get ProductOrder : {}", id);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return productOrderRepository.findById(id);
        } else {
            return productOrderRepository.findOneByIdAndCustomerUserLogin(id, SecurityUtils.getCurrentUserLogin().get() );
        }
    }
 
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductOrder> findOneByIdAndOrderCustomerUserLogin(Long id, String login) {
        log.debug("Request to get ProductOrder for login user : {}", id);
        return productOrderRepository.findOneByIdAndCustomerUserLogin(id, login );
    }
    /**
     * Delete the productOrder by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductOrder : {}", id);        
        productOrderRepository.deleteById(id);
    }
}
