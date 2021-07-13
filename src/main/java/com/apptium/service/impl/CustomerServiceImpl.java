package com.apptium.service.impl;

import com.apptium.domain.Customer;
import com.apptium.repository.CustomerRepository;
import com.apptium.service.CustomerService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Customer}.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        log.debug("Request to save Customer : {}", customer);
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> partialUpdate(Customer customer) {
        log.debug("Request to partially update Customer : {}", customer);

        return customerRepository
            .findById(customer.getId())
            .map(
                existingCustomer -> {
                    if (customer.getName() != null) {
                        existingCustomer.setName(customer.getName());
                    }
                    if (customer.getFormattedName() != null) {
                        existingCustomer.setFormattedName(customer.getFormattedName());
                    }
                    if (customer.getTradingName() != null) {
                        existingCustomer.setTradingName(customer.getTradingName());
                    }
                    if (customer.getCustType() != null) {
                        existingCustomer.setCustType(customer.getCustType());
                    }
                    if (customer.getTitle() != null) {
                        existingCustomer.setTitle(customer.getTitle());
                    }
                    if (customer.getFirstName() != null) {
                        existingCustomer.setFirstName(customer.getFirstName());
                    }
                    if (customer.getLastName() != null) {
                        existingCustomer.setLastName(customer.getLastName());
                    }
                    if (customer.getMiddleName() != null) {
                        existingCustomer.setMiddleName(customer.getMiddleName());
                    }
                    if (customer.getDateOfBirth() != null) {
                        existingCustomer.setDateOfBirth(customer.getDateOfBirth());
                    }
                    if (customer.getGender() != null) {
                        existingCustomer.setGender(customer.getGender());
                    }
                    if (customer.getMaritalStatus() != null) {
                        existingCustomer.setMaritalStatus(customer.getMaritalStatus());
                    }
                    if (customer.getNationality() != null) {
                        existingCustomer.setNationality(customer.getNationality());
                    }
                    if (customer.getStatus() != null) {
                        existingCustomer.setStatus(customer.getStatus());
                    }
                    if (customer.getCustomerEmail() != null) {
                        existingCustomer.setCustomerEmail(customer.getCustomerEmail());
                    }
                    if (customer.getCompanyidType() != null) {
                        existingCustomer.setCompanyidType(customer.getCompanyidType());
                    }
                    if (customer.getCompanyid() != null) {
                        existingCustomer.setCompanyid(customer.getCompanyid());
                    }
                    if (customer.getPrimaryContactAdminIndividualId() != null) {
                        existingCustomer.setPrimaryContactAdminIndividualId(customer.getPrimaryContactAdminIndividualId());
                    }

                    return existingCustomer;
                }
            )
            .map(customerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        log.debug("Request to get all Customers");
        return customerRepository.findAll();
    }

    /**
     *  Get all the customers where Industry is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Customer> findAllWhereIndustryIsNull() {
        log.debug("Request to get all customers where Industry is null");
        return StreamSupport
            .stream(customerRepository.findAll().spliterator(), false)
            .filter(customer -> customer.getIndustry() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
    }
}
