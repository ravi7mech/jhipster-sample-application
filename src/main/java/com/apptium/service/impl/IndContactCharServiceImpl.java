package com.apptium.service.impl;

import com.apptium.domain.IndContactChar;
import com.apptium.repository.IndContactCharRepository;
import com.apptium.service.IndContactCharService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IndContactChar}.
 */
@Service
@Transactional
public class IndContactCharServiceImpl implements IndContactCharService {

    private final Logger log = LoggerFactory.getLogger(IndContactCharServiceImpl.class);

    private final IndContactCharRepository indContactCharRepository;

    public IndContactCharServiceImpl(IndContactCharRepository indContactCharRepository) {
        this.indContactCharRepository = indContactCharRepository;
    }

    @Override
    public IndContactChar save(IndContactChar indContactChar) {
        log.debug("Request to save IndContactChar : {}", indContactChar);
        return indContactCharRepository.save(indContactChar);
    }

    @Override
    public Optional<IndContactChar> partialUpdate(IndContactChar indContactChar) {
        log.debug("Request to partially update IndContactChar : {}", indContactChar);

        return indContactCharRepository
            .findById(indContactChar.getId())
            .map(
                existingIndContactChar -> {
                    if (indContactChar.getType() != null) {
                        existingIndContactChar.setType(indContactChar.getType());
                    }
                    if (indContactChar.getStreet1() != null) {
                        existingIndContactChar.setStreet1(indContactChar.getStreet1());
                    }
                    if (indContactChar.getStreet2() != null) {
                        existingIndContactChar.setStreet2(indContactChar.getStreet2());
                    }
                    if (indContactChar.getCity() != null) {
                        existingIndContactChar.setCity(indContactChar.getCity());
                    }
                    if (indContactChar.getStateOrProvince() != null) {
                        existingIndContactChar.setStateOrProvince(indContactChar.getStateOrProvince());
                    }
                    if (indContactChar.getCountry() != null) {
                        existingIndContactChar.setCountry(indContactChar.getCountry());
                    }
                    if (indContactChar.getPostCode() != null) {
                        existingIndContactChar.setPostCode(indContactChar.getPostCode());
                    }
                    if (indContactChar.getPhoneNumber() != null) {
                        existingIndContactChar.setPhoneNumber(indContactChar.getPhoneNumber());
                    }
                    if (indContactChar.getEmailAddress() != null) {
                        existingIndContactChar.setEmailAddress(indContactChar.getEmailAddress());
                    }
                    if (indContactChar.getFaxNumber() != null) {
                        existingIndContactChar.setFaxNumber(indContactChar.getFaxNumber());
                    }
                    if (indContactChar.getLatitude() != null) {
                        existingIndContactChar.setLatitude(indContactChar.getLatitude());
                    }
                    if (indContactChar.getLongitude() != null) {
                        existingIndContactChar.setLongitude(indContactChar.getLongitude());
                    }
                    if (indContactChar.getSvContactId() != null) {
                        existingIndContactChar.setSvContactId(indContactChar.getSvContactId());
                    }
                    if (indContactChar.getIsEmailValid() != null) {
                        existingIndContactChar.setIsEmailValid(indContactChar.getIsEmailValid());
                    }
                    if (indContactChar.getIsAddressValid() != null) {
                        existingIndContactChar.setIsAddressValid(indContactChar.getIsAddressValid());
                    }
                    if (indContactChar.getIndividualContactId() != null) {
                        existingIndContactChar.setIndividualContactId(indContactChar.getIndividualContactId());
                    }

                    return existingIndContactChar;
                }
            )
            .map(indContactCharRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndContactChar> findAll() {
        log.debug("Request to get all IndContactChars");
        return indContactCharRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndContactChar> findOne(Long id) {
        log.debug("Request to get IndContactChar : {}", id);
        return indContactCharRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IndContactChar : {}", id);
        indContactCharRepository.deleteById(id);
    }
}
