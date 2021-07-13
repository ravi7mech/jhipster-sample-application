package com.apptium.repository;

import com.apptium.domain.CustStatistics;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustStatistics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustStatisticsRepository extends JpaRepository<CustStatistics, Long> {}
