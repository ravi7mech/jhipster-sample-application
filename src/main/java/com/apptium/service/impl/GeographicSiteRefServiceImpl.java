package com.apptium.service.impl;

import com.apptium.domain.GeographicSiteRef;
import com.apptium.repository.GeographicSiteRefRepository;
import com.apptium.service.GeographicSiteRefService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GeographicSiteRef}.
 */
@Service
@Transactional
public class GeographicSiteRefServiceImpl implements GeographicSiteRefService {

    private final Logger log = LoggerFactory.getLogger(GeographicSiteRefServiceImpl.class);

    private final GeographicSiteRefRepository geographicSiteRefRepository;

    public GeographicSiteRefServiceImpl(GeographicSiteRefRepository geographicSiteRefRepository) {
        this.geographicSiteRefRepository = geographicSiteRefRepository;
    }

    @Override
    public GeographicSiteRef save(GeographicSiteRef geographicSiteRef) {
        log.debug("Request to save GeographicSiteRef : {}", geographicSiteRef);
        return geographicSiteRefRepository.save(geographicSiteRef);
    }

    @Override
    public Optional<GeographicSiteRef> partialUpdate(GeographicSiteRef geographicSiteRef) {
        log.debug("Request to partially update GeographicSiteRef : {}", geographicSiteRef);

        return geographicSiteRefRepository
            .findById(geographicSiteRef.getId())
            .map(
                existingGeographicSiteRef -> {
                    if (geographicSiteRef.getSiteRef() != null) {
                        existingGeographicSiteRef.setSiteRef(geographicSiteRef.getSiteRef());
                    }
                    if (geographicSiteRef.getLocation() != null) {
                        existingGeographicSiteRef.setLocation(geographicSiteRef.getLocation());
                    }
                    if (geographicSiteRef.getCustomerContactId() != null) {
                        existingGeographicSiteRef.setCustomerContactId(geographicSiteRef.getCustomerContactId());
                    }

                    return existingGeographicSiteRef;
                }
            )
            .map(geographicSiteRefRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GeographicSiteRef> findAll() {
        log.debug("Request to get all GeographicSiteRefs");
        return geographicSiteRefRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GeographicSiteRef> findOne(Long id) {
        log.debug("Request to get GeographicSiteRef : {}", id);
        return geographicSiteRefRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GeographicSiteRef : {}", id);
        geographicSiteRefRepository.deleteById(id);
    }
}
