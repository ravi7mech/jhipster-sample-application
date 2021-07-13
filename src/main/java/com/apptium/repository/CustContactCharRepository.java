package com.apptium.repository;

import com.apptium.domain.CustContactChar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustContactChar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustContactCharRepository extends JpaRepository<CustContactChar, Long> {}
