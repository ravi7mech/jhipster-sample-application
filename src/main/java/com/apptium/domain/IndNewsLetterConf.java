package com.apptium.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IndNewsLetterConf.
 */
@Entity
@Table(name = "ind_news_letter_conf")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IndNewsLetterConf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "new_letter_type_id", nullable = false)
    private Integer newLetterTypeId;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "individual_id", nullable = false)
    private Integer individualId;

    @JsonIgnoreProperties(
        value = {
            "indActivation", "indNewsLetterConf", "indContacts", "indChars", "indAuditTrials", "custRelParties", "shoppingSessionRefs",
        },
        allowSetters = true
    )
    @OneToOne(mappedBy = "indNewsLetterConf")
    private Individual individual;

    @ManyToOne
    @JsonIgnoreProperties(value = { "custNewsLetterConfigs", "indNewsLetterConfs" }, allowSetters = true)
    private NewsLetterType newsLetterType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IndNewsLetterConf id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNewLetterTypeId() {
        return this.newLetterTypeId;
    }

    public IndNewsLetterConf newLetterTypeId(Integer newLetterTypeId) {
        this.newLetterTypeId = newLetterTypeId;
        return this;
    }

    public void setNewLetterTypeId(Integer newLetterTypeId) {
        this.newLetterTypeId = newLetterTypeId;
    }

    public String getValue() {
        return this.value;
    }

    public IndNewsLetterConf value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getIndividualId() {
        return this.individualId;
    }

    public IndNewsLetterConf individualId(Integer individualId) {
        this.individualId = individualId;
        return this;
    }

    public void setIndividualId(Integer individualId) {
        this.individualId = individualId;
    }

    public Individual getIndividual() {
        return this.individual;
    }

    public IndNewsLetterConf individual(Individual individual) {
        this.setIndividual(individual);
        return this;
    }

    public void setIndividual(Individual individual) {
        if (this.individual != null) {
            this.individual.setIndNewsLetterConf(null);
        }
        if (individual != null) {
            individual.setIndNewsLetterConf(this);
        }
        this.individual = individual;
    }

    public NewsLetterType getNewsLetterType() {
        return this.newsLetterType;
    }

    public IndNewsLetterConf newsLetterType(NewsLetterType newsLetterType) {
        this.setNewsLetterType(newsLetterType);
        return this;
    }

    public void setNewsLetterType(NewsLetterType newsLetterType) {
        this.newsLetterType = newsLetterType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndNewsLetterConf)) {
            return false;
        }
        return id != null && id.equals(((IndNewsLetterConf) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndNewsLetterConf{" +
            "id=" + getId() +
            ", newLetterTypeId=" + getNewLetterTypeId() +
            ", value='" + getValue() + "'" +
            ", individualId=" + getIndividualId() +
            "}";
    }
}
