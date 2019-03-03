package com.sonacode.store.service.impl;

import com.sonacode.store.service.OrderItemService;
import com.sonacode.store.domain.OrderItem;
import com.sonacode.store.repository.OrderItemRepository;
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
 * Service Implementation for managing OrderItem.
 */
@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private final Logger log = LoggerFactory.getLogger(OrderItemServiceImpl.class);

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * Save a orderItem.
     *
     * @param orderItem the entity to save
     * @return the persisted entity
     */
    @Override
    public OrderItem save(OrderItem orderItem) {
        log.debug("Request to save OrderItem : {}", orderItem);
        return orderItemRepository.save(orderItem);
    }

    /**
     * Get all the orderItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItem> findAll(Pageable pageable) {
        log.debug("Request to get all OrderItems");
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return orderItemRepository.findAll(pageable);
        } else {
            return orderItemRepository.findAllByOrderCustomerUserLogin(SecurityUtils.getCurrentUserLogin().get(), pageable);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItem> findAllByOrderCustomerUserLogin(String login, Pageable pageable) {
        log.debug("Request to get all Invoices by user login");
        return orderItemRepository.findAllByOrderCustomerUserLogin(login, pageable);
    }
    /**
     * Get one orderItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderItem> findOne(Long id) {
        log.debug("Request to get OrderItem : {}", id);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return orderItemRepository.findById(id);
        } else {
            return orderItemRepository.findOneByIdAndOrderCustomerUserLogin(id, SecurityUtils.getCurrentUserLogin().get() );
        }
    }
 
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderItem> findOneByIdAndOrderCustomerUserLogin(Long id, String login) {
        log.debug("Request to get OrderItem for login user : {}", id);
        return orderItemRepository.findOneByIdAndOrderCustomerUserLogin(id, login );
    }
    /**
     * Delete the orderItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderItem : {}", id);        orderItemRepository.deleteById(id);
    }
}
