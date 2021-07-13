package com.apptium.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IndAuditTrial.
 */
@Entity
@Table(name = "ind_audit_trial")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IndAuditTrial implements Serializable {

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
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @NotNull
    @Column(name = "individual_id", nullable = false)
    private Integer individualId;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "indActivation", "indNewsLetterConf", "indContacts", "indChars", "indAuditTrials", "custRelParties", "shoppingSessionRefs",
        },
        allowSetters = true
    )
    private Individual individual;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IndAuditTrial id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public IndAuditTrial name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return this.activity;
    }

    public IndAuditTrial activity(String activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Integer getCustomerId() {
        return this.customerId;
    }

    public IndAuditTrial customerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getIndividualId() {
        return this.individualId;
    }

    public IndAuditTrial individualId(Integer individualId) {
        this.individualId = individualId;
        return this;
    }

    public void setIndividualId(Integer individualId) {
        this.individualId = individualId;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public IndAuditTrial createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Individual getIndividual() {
        return this.individual;
    }

    public IndAuditTrial individual(Individual individual) {
        this.setIndividual(individual);
        return this;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndAuditTrial)) {
            return false;
        }
        return id != null && id.equals(((IndAuditTrial) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndAuditTrial{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", activity='" + getActivity() + "'" +
            ", customerId=" + getCustomerId() +
            ", individualId=" + getIndividualId() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
