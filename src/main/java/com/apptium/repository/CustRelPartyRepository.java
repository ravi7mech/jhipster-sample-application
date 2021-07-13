package com.apptium.repository;

import com.apptium.domain.CustRelParty;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustRelParty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustRelPartyRepository extends JpaRepository<CustRelParty, Long> {}
