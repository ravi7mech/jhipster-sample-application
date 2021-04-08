package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "student")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "student", "teacher", "question" }, allowSetters = true)
    private Set<QuestionVotes> questionVotes = new HashSet<>();

    @OneToMany(mappedBy = "student")
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

    public Student id(Long id) {
        this.id = id;
        return this;
    }

    public Set<QuestionVotes> getQuestionVotes() {
        return this.questionVotes;
    }

    public Student questionVotes(Set<QuestionVotes> questionVotes) {
        this.setQuestionVotes(questionVotes);
        return this;
    }

    public Student addQuestionVotes(QuestionVotes questionVotes) {
        this.questionVotes.add(questionVotes);
        questionVotes.setStudent(this);
        return this;
    }

    public Student removeQuestionVotes(QuestionVotes questionVotes) {
        this.questionVotes.remove(questionVotes);
        questionVotes.setStudent(null);
        return this;
    }

    public void setQuestionVotes(Set<QuestionVotes> questionVotes) {
        if (this.questionVotes != null) {
            this.questionVotes.forEach(i -> i.setStudent(null));
        }
        if (questionVotes != null) {
            questionVotes.forEach(i -> i.setStudent(this));
        }
        this.questionVotes = questionVotes;
    }

    public Set<AnswerVotes> getAnswerVotes() {
        return this.answerVotes;
    }

    public Student answerVotes(Set<AnswerVotes> answerVotes) {
        this.setAnswerVotes(answerVotes);
        return this;
    }

    public Student addAnswerVotes(AnswerVotes answerVotes) {
        this.answerVotes.add(answerVotes);
        answerVotes.setStudent(this);
        return this;
    }

    public Student removeAnswerVotes(AnswerVotes answerVotes) {
        this.answerVotes.remove(answerVotes);
        answerVotes.setStudent(null);
        return this;
    }

    public void setAnswerVotes(Set<AnswerVotes> answerVotes) {
        if (this.answerVotes != null) {
            this.answerVotes.forEach(i -> i.setStudent(null));
        }
        if (answerVotes != null) {
            answerVotes.forEach(i -> i.setStudent(this));
        }
        this.answerVotes = answerVotes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            "}";
    }
}
