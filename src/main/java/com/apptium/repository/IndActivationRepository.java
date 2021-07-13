package com.apptium.repository;

import com.apptium.domain.IndActivation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IndActivation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndActivationRepository extends JpaRepository<IndActivation, Long> {}
