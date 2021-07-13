package com.apptium.repository;

import com.apptium.domain.CustISVRef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustISVRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustISVRefRepository extends JpaRepository<CustISVRef, Long> {}
