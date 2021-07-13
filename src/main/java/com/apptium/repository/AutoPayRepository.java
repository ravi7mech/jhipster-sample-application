package com.apptium.repository;

import com.apptium.domain.AutoPay;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AutoPay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutoPayRepository extends JpaRepository<AutoPay, Long> {}
