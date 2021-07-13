package com.apptium.service.impl;

import com.apptium.domain.IndContact;
import com.apptium.repository.IndContactRepository;
import com.apptium.service.IndContactService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IndContact}.
 */
@Service
@Transactional
public class IndContactServiceImpl implements IndContactService {

    private final Logger log = LoggerFactory.getLogger(IndContactServiceImpl.class);

    private final IndContactRepository indContactRepository;

    public IndContactServiceImpl(IndContactRepository indContactRepository) {
        this.indContactRepository = indContactRepository;
    }

    @Override
    public IndContact save(IndContact indContact) {
        log.debug("Request to save IndContact : {}", indContact);
        return indContactRepository.save(indContact);
    }

    @Override
    public Optional<IndContact> partialUpdate(IndContact indContact) {
        log.debug("Request to partially update IndContact : {}", indContact);

        return indContactRepository
            .findById(indContact.getId())
            .map(
                existingIndContact -> {
                    if (indContact.getPreferred() != null) {
                        existingIndContact.setPreferred(indContact.getPreferred());
                    }
                    if (indContact.getType() != null) {
                        existingIndContact.setType(indContact.getType());
                    }
                    if (indContact.getValidFrom() != null) {
                        existingIndContact.setValidFrom(indContact.getValidFrom());
                    }
                    if (indContact.getValidTo() != null) {
                        existingIndContact.setValidTo(indContact.getValidTo());
                    }
                    if (indContact.getIndividualId() != null) {
                        existingIndContact.setIndividualId(indContact.getIndividualId());
                    }

                    return existingIndContact;
                }
            )
            .map(indContactRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndContact> findAll() {
        log.debug("Request to get all IndContacts");
        return indContactRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndContact> findOne(Long id) {
        log.debug("Request to get IndContact : {}", id);
        return indContactRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IndContact : {}", id);
        indContactRepository.deleteById(id);
    }
}
