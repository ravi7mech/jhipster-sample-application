package com.apptium.repository;

import com.apptium.domain.CustBillingAcc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustBillingAcc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustBillingAccRepository extends JpaRepository<CustBillingAcc, Long> {}
