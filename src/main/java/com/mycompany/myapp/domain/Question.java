package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "question")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "student", "teacher", "question" }, allowSetters = true)
    private Set<QuestionVotes> questionVotes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Question title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<QuestionVotes> getQuestionVotes() {
        return this.questionVotes;
    }

    public Question questionVotes(Set<QuestionVotes> questionVotes) {
        this.setQuestionVotes(questionVotes);
        return this;
    }

    public Question addQuestionVotes(QuestionVotes questionVotes) {
        this.questionVotes.add(questionVotes);
        questionVotes.setQuestion(this);
        return this;
    }

    public Question removeQuestionVotes(QuestionVotes questionVotes) {
        this.questionVotes.remove(questionVotes);
        questionVotes.setQuestion(null);
        return this;
    }

    public void setQuestionVotes(Set<QuestionVotes> questionVotes) {
        if (this.questionVotes != null) {
            this.questionVotes.forEach(i -> i.setQuestion(null));
        }
        if (questionVotes != null) {
            questionVotes.forEach(i -> i.setQuestion(this));
        }
        this.questionVotes = questionVotes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        return id != null && id.equals(((Question) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
