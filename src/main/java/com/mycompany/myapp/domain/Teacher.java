package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Teacher.
 */
@Entity
@Table(name = "teacher")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "teacher")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "student", "teacher", "question" }, allowSetters = true)
    private Set<QuestionVotes> questionVotes = new HashSet<>();

    @OneToMany(mappedBy = "teacher")
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

    public Teacher id(Long id) {
        this.id = id;
        return this;
    }

    public Set<QuestionVotes> getQuestionVotes() {
        return this.questionVotes;
    }

    public Teacher questionVotes(Set<QuestionVotes> questionVotes) {
        this.setQuestionVotes(questionVotes);
        return this;
    }

    public Teacher addQuestionVotes(QuestionVotes questionVotes) {
        this.questionVotes.add(questionVotes);
        questionVotes.setTeacher(this);
        return this;
    }

    public Teacher removeQuestionVotes(QuestionVotes questionVotes) {
        this.questionVotes.remove(questionVotes);
        questionVotes.setTeacher(null);
        return this;
    }

    public void setQuestionVotes(Set<QuestionVotes> questionVotes) {
        if (this.questionVotes != null) {
            this.questionVotes.forEach(i -> i.setTeacher(null));
        }
        if (questionVotes != null) {
            questionVotes.forEach(i -> i.setTeacher(this));
        }
        this.questionVotes = questionVotes;
    }

    public Set<AnswerVotes> getAnswerVotes() {
        return this.answerVotes;
    }

    public Teacher answerVotes(Set<AnswerVotes> answerVotes) {
        this.setAnswerVotes(answerVotes);
        return this;
    }

    public Teacher addAnswerVotes(AnswerVotes answerVotes) {
        this.answerVotes.add(answerVotes);
        answerVotes.setTeacher(this);
        return this;
    }

    public Teacher removeAnswerVotes(AnswerVotes answerVotes) {
        this.answerVotes.remove(answerVotes);
        answerVotes.setTeacher(null);
        return this;
    }

    public void setAnswerVotes(Set<AnswerVotes> answerVotes) {
        if (this.answerVotes != null) {
            this.answerVotes.forEach(i -> i.setTeacher(null));
        }
        if (answerVotes != null) {
            answerVotes.forEach(i -> i.setTeacher(this));
        }
        this.answerVotes = answerVotes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Teacher)) {
            return false;
        }
        return id != null && id.equals(((Teacher) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + getId() +
            "}";
    }
}
