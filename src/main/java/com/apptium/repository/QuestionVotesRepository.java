package com.apptium.repository;

import com.apptium.domain.QuestionVotes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the QuestionVotes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionVotesRepository extends JpaRepository<QuestionVotes, Long> {}
