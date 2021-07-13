package com.apptium.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnswerVotesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerVotes.class);
        AnswerVotes answerVotes1 = new AnswerVotes();
        answerVotes1.setId(1L);
        AnswerVotes answerVotes2 = new AnswerVotes();
        answerVotes2.setId(answerVotes1.getId());
        assertThat(answerVotes1).isEqualTo(answerVotes2);
        answerVotes2.setId(2L);
        assertThat(answerVotes1).isNotEqualTo(answerVotes2);
        answerVotes1.setId(null);
        assertThat(answerVotes1).isNotEqualTo(answerVotes2);
    }
}
