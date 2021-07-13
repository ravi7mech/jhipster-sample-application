package com.apptium.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CustCreditProfile.
 */
@Entity
@Table(name = "cust_credit_profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustCreditProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "customer_id_type_1", nullable = false)
    private String customerIDType1;

    @NotNull
    @Column(name = "customer_id_ref_1", nullable = false)
    private String customerIDRef1;

    @NotNull
    @Column(name = "customer_id_type_2", nullable = false)
    private String customerIDType2;

    @NotNull
    @Column(name = "customer_id_ref_2", nullable = false)
    private String customerIDRef2;

    @NotNull
    @Column(name = "credit_card_number", nullable = false)
    private Long creditCardNumber;

    @NotNull
    @Column(name = "credit_profile_data", nullable = false)
    private Instant creditProfileData;

    @NotNull
    @Column(name = "credit_risk_rating", nullable = false)
    private String creditRiskRating;

    @NotNull
    @Column(name = "credit_risk_rating_desc", nullable = false)
    private String creditRiskRatingDesc;

    @NotNull
    @Column(name = "credit_score", nullable = false)
    private Integer creditScore;

    @NotNull
    @Column(name = "valid_until", nullable = false)
    private Instant validUntil;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

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
    @OneToOne(mappedBy = "custCreditProfile")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustCreditProfile id(Long id) {
        this.id = id;
        return this;
    }

    public String getCustomerIDType1() {
        return this.customerIDType1;
    }

    public CustCreditProfile customerIDType1(String customerIDType1) {
        this.customerIDType1 = customerIDType1;
        return this;
    }

    public void setCustomerIDType1(String customerIDType1) {
        this.customerIDType1 = customerIDType1;
    }

    public String getCustomerIDRef1() {
        return this.customerIDRef1;
    }

    public CustCreditProfile customerIDRef1(String customerIDRef1) {
        this.customerIDRef1 = customerIDRef1;
        return this;
    }

    public void setCustomerIDRef1(String customerIDRef1) {
        this.customerIDRef1 = customerIDRef1;
    }

    public String getCustomerIDType2() {
        return this.customerIDType2;
    }

    public CustCreditProfile customerIDType2(String customerIDType2) {
        this.customerIDType2 = customerIDType2;
        return this;
    }

    public void setCustomerIDType2(String customerIDType2) {
        this.customerIDType2 = customerIDType2;
    }

    public String getCustomerIDRef2() {
        return this.customerIDRef2;
    }

    public CustCreditProfile customerIDRef2(String customerIDRef2) {
        this.customerIDRef2 = customerIDRef2;
        return this;
    }

    public void setCustomerIDRef2(String customerIDRef2) {
        this.customerIDRef2 = customerIDRef2;
    }

    public Long getCreditCardNumber() {
        return this.creditCardNumber;
    }

    public CustCreditProfile creditCardNumber(Long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
        return this;
    }

    public void setCreditCardNumber(Long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Instant getCreditProfileData() {
        return this.creditProfileData;
    }

    public CustCreditProfile creditProfileData(Instant creditProfileData) {
        this.creditProfileData = creditProfileData;
        return this;
    }

    public void setCreditProfileData(Instant creditProfileData) {
        this.creditProfileData = creditProfileData;
    }

    public String getCreditRiskRating() {
        return this.creditRiskRating;
    }

    public CustCreditProfile creditRiskRating(String creditRiskRating) {
        this.creditRiskRating = creditRiskRating;
        return this;
    }

    public void setCreditRiskRating(String creditRiskRating) {
        this.creditRiskRating = creditRiskRating;
    }

    public String getCreditRiskRatingDesc() {
        return this.creditRiskRatingDesc;
    }

    public CustCreditProfile creditRiskRatingDesc(String creditRiskRatingDesc) {
        this.creditRiskRatingDesc = creditRiskRatingDesc;
        return this;
    }

    public void setCreditRiskRatingDesc(String creditRiskRatingDesc) {
        this.creditRiskRatingDesc = creditRiskRatingDesc;
    }

    public Integer getCreditScore() {
        return this.creditScore;
    }

    public CustCreditProfile creditScore(Integer creditScore) {
        this.creditScore = creditScore;
        return this;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public Instant getValidUntil() {
        return this.validUntil;
    }

    public CustCreditProfile validUntil(Instant validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Integer getCustomerId() {
        return this.customerId;
    }

    public CustCreditProfile customerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustCreditProfile customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setCustomer(Customer customer) {
        if (this.customer != null) {
            this.customer.setCustCreditProfile(null);
        }
        if (customer != null) {
            customer.setCustCreditProfile(this);
        }
        this.customer = customer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustCreditProfile)) {
            return false;
        }
        return id != null && id.equals(((CustCreditProfile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustCreditProfile{" +
            "id=" + getId() +
            ", customerIDType1='" + getCustomerIDType1() + "'" +
            ", customerIDRef1='" + getCustomerIDRef1() + "'" +
            ", customerIDType2='" + getCustomerIDType2() + "'" +
            ", customerIDRef2='" + getCustomerIDRef2() + "'" +
            ", creditCardNumber=" + getCreditCardNumber() +
            ", creditProfileData='" + getCreditProfileData() + "'" +
            ", creditRiskRating='" + getCreditRiskRating() + "'" +
            ", creditRiskRatingDesc='" + getCreditRiskRatingDesc() + "'" +
            ", creditScore=" + getCreditScore() +
            ", validUntil='" + getValidUntil() + "'" +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
