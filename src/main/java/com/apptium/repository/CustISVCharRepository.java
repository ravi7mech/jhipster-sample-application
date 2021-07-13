package com.apptium.repository;

import com.apptium.domain.CustISVChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustISVChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustISVCharRepository extends JpaRepository<CustISVChar, Long> {}
