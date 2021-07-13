package com.apptium.repository;

import com.apptium.domain.BankCardType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankCardType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankCardTypeRepository extends JpaRepository<BankCardType, Long> {}
