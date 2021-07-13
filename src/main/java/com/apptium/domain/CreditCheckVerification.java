package com.apptium.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CreditCheckVerification.
 */
@Entity
@Table(name = "credit_check_verification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CreditCheckVerification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "verification_question", nullable = false)
    private String verificationQuestion;

    @NotNull
    @Column(name = "verification_question_choice", nullable = false)
    private String verificationQuestionChoice;

    @NotNull
    @Column(name = "verification_answer", nullable = false)
    private String verificationAnswer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreditCheckVerification id(Long id) {
        this.id = id;
        return this;
    }

    public String getVerificationQuestion() {
        return this.verificationQuestion;
    }

    public CreditCheckVerification verificationQuestion(String verificationQuestion) {
        this.verificationQuestion = verificationQuestion;
        return this;
    }

    public void setVerificationQuestion(String verificationQuestion) {
        this.verificationQuestion = verificationQuestion;
    }

    public String getVerificationQuestionChoice() {
        return this.verificationQuestionChoice;
    }

    public CreditCheckVerification verificationQuestionChoice(String verificationQuestionChoice) {
        this.verificationQuestionChoice = verificationQuestionChoice;
        return this;
    }

    public void setVerificationQuestionChoice(String verificationQuestionChoice) {
        this.verificationQuestionChoice = verificationQuestionChoice;
    }

    public String getVerificationAnswer() {
        return this.verificationAnswer;
    }

    public CreditCheckVerification verificationAnswer(String verificationAnswer) {
        this.verificationAnswer = verificationAnswer;
        return this;
    }

    public void setVerificationAnswer(String verificationAnswer) {
        this.verificationAnswer = verificationAnswer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditCheckVerification)) {
            return false;
        }
        return id != null && id.equals(((CreditCheckVerification) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditCheckVerification{" +
            "id=" + getId() +
            ", verificationQuestion='" + getVerificationQuestion() + "'" +
            ", verificationQuestionChoice='" + getVerificationQuestionChoice() + "'" +
            ", verificationAnswer='" + getVerificationAnswer() + "'" +
            "}";
    }
}
