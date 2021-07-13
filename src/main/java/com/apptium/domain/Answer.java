package com.apptium.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Answer.
 */
@Entity
@Table(name = "answer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "body", nullable = false)
    private String body;

    @OneToMany(mappedBy = "answer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "student", "teacher", "answer" }, allowSetters = true)
    private Set<AnswerVotes> answerVotes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Answer id(Long id) {
        this.id = id;
        return this;
    }

    public String getBody() {
        return this.body;
    }

    public Answer body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Set<AnswerVotes> getAnswerVotes() {
        return this.answerVotes;
    }

    public Answer answerVotes(Set<AnswerVotes> answerVotes) {
        this.setAnswerVotes(answerVotes);
        return this;
    }

    public Answer addAnswerVotes(AnswerVotes answerVotes) {
        this.answerVotes.add(answerVotes);
        answerVotes.setAnswer(this);
        return this;
    }

    public Answer removeAnswerVotes(AnswerVotes answerVotes) {
        this.answerVotes.remove(answerVotes);
        answerVotes.setAnswer(null);
        return this;
    }

    public void setAnswerVotes(Set<AnswerVotes> answerVotes) {
        if (this.answerVotes != null) {
            this.answerVotes.forEach(i -> i.setAnswer(null));
        }
        if (answerVotes != null) {
            answerVotes.forEach(i -> i.setAnswer(this));
        }
        this.answerVotes = answerVotes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Answer)) {
            return false;
        }
        return id != null && id.equals(((Answer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Answer{" +
            "id=" + getId() +
            ", body='" + getBody() + "'" +
            "}";
    }
}
