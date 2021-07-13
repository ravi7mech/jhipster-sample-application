package com.apptium.repository;

import com.apptium.domain.GeographicSiteRef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GeographicSiteRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeographicSiteRefRepository extends JpaRepository<GeographicSiteRef, Long> {}
