package com.apptium.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ShoppingSessionRef.
 */
@Entity
@Table(name = "shopping_session_ref")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShoppingSessionRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "href", nullable = false)
    private String href;

    @NotNull
    @Column(name = "shopping_session_id", nullable = false)
    private Integer shoppingSessionId;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @NotNull
    @Column(name = "session_abondoned", nullable = false)
    private Boolean sessionAbondoned;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @NotNull
    @Column(name = "channel", nullable = false)
    private String channel;

    @NotNull
    @Column(name = "individual_id", nullable = false)
    private Integer individualId;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "custBillingAcc",
            "custCreditProfile",
            "custBillingRef",
            "custContacts",
            "custStatistics",
            "custChars",
            "custCommunicationRefs",
            "custPasswordChars",
            "custNewsLetterConfigs",
            "custSecurityChars",
            "custRelParties",
            "custISVRefs",
            "shoppingSessionRefs",
            "industry",
        },
        allowSetters = true
    )
    private Customer customer;

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

    public ShoppingSessionRef id(Long id) {
        this.id = id;
        return this;
    }

    public String getHref() {
        return this.href;
    }

    public ShoppingSessionRef href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getShoppingSessionId() {
        return this.shoppingSessionId;
    }

    public ShoppingSessionRef shoppingSessionId(Integer shoppingSessionId) {
        this.shoppingSessionId = shoppingSessionId;
        return this;
    }

    public void setShoppingSessionId(Integer shoppingSessionId) {
        this.shoppingSessionId = shoppingSessionId;
    }

    public String getStatus() {
        return this.status;
    }

    public ShoppingSessionRef status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public ShoppingSessionRef createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getSessionAbondoned() {
        return this.sessionAbondoned;
    }

    public ShoppingSessionRef sessionAbondoned(Boolean sessionAbondoned) {
        this.sessionAbondoned = sessionAbondoned;
        return this;
    }

    public void setSessionAbondoned(Boolean sessionAbondoned) {
        this.sessionAbondoned = sessionAbondoned;
    }

    public Integer getCustomerId() {
        return this.customerId;
    }

    public ShoppingSessionRef customerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getChannel() {
        return this.channel;
    }

    public ShoppingSessionRef channel(String channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getIndividualId() {
        return this.individualId;
    }

    public ShoppingSessionRef individualId(Integer individualId) {
        this.individualId = individualId;
        return this;
    }

    public void setIndividualId(Integer individualId) {
        this.individualId = individualId;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public ShoppingSessionRef customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Individual getIndividual() {
        return this.individual;
    }

    public ShoppingSessionRef individual(Individual individual) {
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
        if (!(o instanceof ShoppingSessionRef)) {
            return false;
        }
        return id != null && id.equals(((ShoppingSessionRef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShoppingSessionRef{" +
            "id=" + getId() +
            ", href='" + getHref() + "'" +
            ", shoppingSessionId=" + getShoppingSessionId() +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", sessionAbondoned='" + getSessionAbondoned() + "'" +
            ", customerId=" + getCustomerId() +
            ", channel='" + getChannel() + "'" +
            ", individualId=" + getIndividualId() +
            "}";
    }
}
