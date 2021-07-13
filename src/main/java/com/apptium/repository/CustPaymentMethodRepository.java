package com.apptium.repository;

import com.apptium.domain.CustPaymentMethod;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustPaymentMethod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustPaymentMethodRepository extends JpaRepository<CustPaymentMethod, Long> {}
