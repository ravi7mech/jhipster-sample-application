package com.apptium.repository;

import com.apptium.domain.CustCommunicationRef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustCommunicationRef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustCommunicationRefRepository extends JpaRepository<CustCommunicationRef, Long> {}
