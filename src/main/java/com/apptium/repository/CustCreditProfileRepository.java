package com.apptium.repository;

import com.apptium.domain.CustCreditProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustCreditProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustCreditProfileRepository extends JpaRepository<CustCreditProfile, Long> {}
