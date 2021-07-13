package com.apptium.domain;

import com.apptium.domain.enumeration.Vote;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QuestionVotes.
 */
@Entity
@Table(name = "question_votes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuestionVotes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote")
    private Vote vote;

    @ManyToOne
    @JsonIgnoreProperties(value = { "questionVotes", "answerVotes" }, allowSetters = true)
    private Student student;

    @ManyToOne
    @JsonIgnoreProperties(value = { "questionVotes", "answerVotes" }, allowSetters = true)
    private Teacher teacher;

    @ManyToOne
    @JsonIgnoreProperties(value = { "questionVotes" }, allowSetters = true)
    private Question question;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionVotes id(Long id) {
        this.id = id;
        return this;
    }

    public Vote getVote() {
        return this.vote;
    }

    public QuestionVotes vote(Vote vote) {
        this.vote = vote;
        return this;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public Student getStudent() {
        return this.student;
    }

    public QuestionVotes student(Student student) {
        this.setStudent(student);
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return this.teacher;
    }

    public QuestionVotes teacher(Teacher teacher) {
        this.setTeacher(teacher);
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Question getQuestion() {
        return this.question;
    }

    public QuestionVotes question(Question question) {
        this.setQuestion(question);
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionVotes)) {
            return false;
        }
        return id != null && id.equals(((QuestionVotes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionVotes{" +
            "id=" + getId() +
            ", vote='" + getVote() + "'" +
            "}";
    }
}
