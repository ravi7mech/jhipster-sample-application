package com.apptium.repository;

import com.apptium.domain.CustChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustCharRepository extends JpaRepository<CustChar, Long> {}
