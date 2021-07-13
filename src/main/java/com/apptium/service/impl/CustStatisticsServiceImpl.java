package com.apptium.service.impl;

import com.apptium.domain.CustStatistics;
import com.apptium.repository.CustStatisticsRepository;
import com.apptium.service.CustStatisticsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustStatistics}.
 */
@Service
@Transactional
public class CustStatisticsServiceImpl implements CustStatisticsService {

    private final Logger log = LoggerFactory.getLogger(CustStatisticsServiceImpl.class);

    private final CustStatisticsRepository custStatisticsRepository;

    public CustStatisticsServiceImpl(CustStatisticsRepository custStatisticsRepository) {
        this.custStatisticsRepository = custStatisticsRepository;
    }

    @Override
    public CustStatistics save(CustStatistics custStatistics) {
        log.debug("Request to save CustStatistics : {}", custStatistics);
        return custStatisticsRepository.save(custStatistics);
    }

    @Override
    public Optional<CustStatistics> partialUpdate(CustStatistics custStatistics) {
        log.debug("Request to partially update CustStatistics : {}", custStatistics);

        return custStatisticsRepository
            .findById(custStatistics.getId())
            .map(
                existingCustStatistics -> {
                    if (custStatistics.getName() != null) {
                        existingCustStatistics.setName(custStatistics.getName());
                    }
                    if (custStatistics.getValue() != null) {
                        existingCustStatistics.setValue(custStatistics.getValue());
                    }
                    if (custStatistics.getValuetype() != null) {
                        existingCustStatistics.setValuetype(custStatistics.getValuetype());
                    }
                    if (custStatistics.getCustomerId() != null) {
                        existingCustStatistics.setCustomerId(custStatistics.getCustomerId());
                    }

                    return existingCustStatistics;
                }
            )
            .map(custStatisticsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustStatistics> findAll() {
        log.debug("Request to get all CustStatistics");
        return custStatisticsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustStatistics> findOne(Long id) {
        log.debug("Request to get CustStatistics : {}", id);
        return custStatisticsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustStatistics : {}", id);
        custStatisticsRepository.deleteById(id);
    }
}
