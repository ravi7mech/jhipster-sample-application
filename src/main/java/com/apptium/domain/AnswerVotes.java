package com.apptium.domain;

import com.apptium.domain.enumeration.Vote;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnswerVotes.
 */
@Entity
@Table(name = "answer_votes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnswerVotes implements Serializable {

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
    @JsonIgnoreProperties(value = { "answerVotes" }, allowSetters = true)
    private Answer answer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnswerVotes id(Long id) {
        this.id = id;
        return this;
    }

    public Vote getVote() {
        return this.vote;
    }

    public AnswerVotes vote(Vote vote) {
        this.vote = vote;
        return this;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public Student getStudent() {
        return this.student;
    }

    public AnswerVotes student(Student student) {
        this.setStudent(student);
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return this.teacher;
    }

    public AnswerVotes teacher(Teacher teacher) {
        this.setTeacher(teacher);
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Answer getAnswer() {
        return this.answer;
    }

    public AnswerVotes answer(Answer answer) {
        this.setAnswer(answer);
        return this;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnswerVotes)) {
            return false;
        }
        return id != null && id.equals(((AnswerVotes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnswerVotes{" +
            "id=" + getId() +
            ", vote='" + getVote() + "'" +
            "}";
    }
}
