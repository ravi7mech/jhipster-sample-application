package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AnswerVotes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnswerVotes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerVotesRepository extends JpaRepository<AnswerVotes, Long> {}
