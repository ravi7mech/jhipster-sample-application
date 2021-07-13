package com.apptium.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IndActivation.
 */
@Entity
@Table(name = "ind_activation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IndActivation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "activity", nullable = false)
    private String activity;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @NotNull
    @Column(name = "individual_id", nullable = false)
    private Integer individualId;

    @JsonIgnoreProperties(
        value = {
            "indActivation", "indNewsLetterConf", "indContacts", "indChars", "indAuditTrials", "custRelParties", "shoppingSessionRefs",
        },
        allowSetters = true
    )
    @OneToOne(mappedBy = "indActivation")
    private Individual individual;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IndActivation id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public IndActivation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return this.activity;
    }

    public IndActivation activity(String activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public IndActivation createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCustomerId() {
        return this.customerId;
    }

    public IndActivation customerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getIndividualId() {
        return this.individualId;
    }

    public IndActivation individualId(Integer individualId) {
        this.individualId = individualId;
        return this;
    }

    public void setIndividualId(Integer individualId) {
        this.individualId = individualId;
    }

    public Individual getIndividual() {
        return this.individual;
    }

    public IndActivation individual(Individual individual) {
        this.setIndividual(individual);
        return this;
    }

    public void setIndividual(Individual individual) {
        if (this.individual != null) {
            this.individual.setIndActivation(null);
        }
        if (individual != null) {
            individual.setIndActivation(this);
        }
        this.individual = individual;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndActivation)) {
            return false;
        }
        return id != null && id.equals(((IndActivation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndActivation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", activity='" + getActivity() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", customerId=" + getCustomerId() +
            ", individualId=" + getIndividualId() +
            "}";
    }
}
