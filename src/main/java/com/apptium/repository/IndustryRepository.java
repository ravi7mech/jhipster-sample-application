package com.apptium.repository;

import com.apptium.domain.Industry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Industry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long> {}
