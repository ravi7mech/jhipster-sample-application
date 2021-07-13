package com.apptium.service.impl;

import com.apptium.domain.CustContactChar;
import com.apptium.repository.CustContactCharRepository;
import com.apptium.service.CustContactCharService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustContactChar}.
 */
@Service
@Transactional
public class CustContactCharServiceImpl implements CustContactCharService {

    private final Logger log = LoggerFactory.getLogger(CustContactCharServiceImpl.class);

    private final CustContactCharRepository custContactCharRepository;

    public CustContactCharServiceImpl(CustContactCharRepository custContactCharRepository) {
        this.custContactCharRepository = custContactCharRepository;
    }

    @Override
    public CustContactChar save(CustContactChar custContactChar) {
        log.debug("Request to save CustContactChar : {}", custContactChar);
        return custContactCharRepository.save(custContactChar);
    }

    @Override
    public Optional<CustContactChar> partialUpdate(CustContactChar custContactChar) {
        log.debug("Request to partially update CustContactChar : {}", custContactChar);

        return custContactCharRepository
            .findById(custContactChar.getId())
            .map(
                existingCustContactChar -> {
                    if (custContactChar.getType() != null) {
                        existingCustContactChar.setType(custContactChar.getType());
                    }
                    if (custContactChar.getStreet1() != null) {
                        existingCustContactChar.setStreet1(custContactChar.getStreet1());
                    }
                    if (custContactChar.getStreet2() != null) {
                        existingCustContactChar.setStreet2(custContactChar.getStreet2());
                    }
                    if (custContactChar.getCity() != null) {
                        existingCustContactChar.setCity(custContactChar.getCity());
                    }
                    if (custContactChar.getStateOrProvince() != null) {
                        existingCustContactChar.setStateOrProvince(custContactChar.getStateOrProvince());
                    }
                    if (custContactChar.getCountry() != null) {
                        existingCustContactChar.setCountry(custContactChar.getCountry());
                    }
                    if (custContactChar.getPostCode() != null) {
                        existingCustContactChar.setPostCode(custContactChar.getPostCode());
                    }
                    if (custContactChar.getPhoneNumber() != null) {
                        existingCustContactChar.setPhoneNumber(custContactChar.getPhoneNumber());
                    }
                    if (custContactChar.getEmailAddress() != null) {
                        existingCustContactChar.setEmailAddress(custContactChar.getEmailAddress());
                    }
                    if (custContactChar.getFaxNumber() != null) {
                        existingCustContactChar.setFaxNumber(custContactChar.getFaxNumber());
                    }
                    if (custContactChar.getLatitude() != null) {
                        existingCustContactChar.setLatitude(custContactChar.getLatitude());
                    }
                    if (custContactChar.getLongitude() != null) {
                        existingCustContactChar.setLongitude(custContactChar.getLongitude());
                    }
                    if (custContactChar.getSvContactId() != null) {
                        existingCustContactChar.setSvContactId(custContactChar.getSvContactId());
                    }
                    if (custContactChar.getIsEmailValid() != null) {
                        existingCustContactChar.setIsEmailValid(custContactChar.getIsEmailValid());
                    }
                    if (custContactChar.getIsAddressValid() != null) {
                        existingCustContactChar.setIsAddressValid(custContactChar.getIsAddressValid());
                    }
                    if (custContactChar.getCustContactMediumId() != null) {
                        existingCustContactChar.setCustContactMediumId(custContactChar.getCustContactMediumId());
                    }

                    return existingCustContactChar;
                }
            )
            .map(custContactCharRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustContactChar> findAll() {
        log.debug("Request to get all CustContactChars");
        return custContactCharRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustContactChar> findOne(Long id) {
        log.debug("Request to get CustContactChar : {}", id);
        return custContactCharRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustContactChar : {}", id);
        custContactCharRepository.deleteById(id);
    }
}
