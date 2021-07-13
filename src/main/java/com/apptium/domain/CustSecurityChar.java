package com.apptium.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CustSecurityChar.
 */
@Entity
@Table(name = "cust_security_char")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustSecurityChar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "valuetype", nullable = false)
    private String valuetype;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustSecurityChar id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public CustSecurityChar name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public CustSecurityChar value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValuetype() {
        return this.valuetype;
    }

    public CustSecurityChar valuetype(String valuetype) {
        this.valuetype = valuetype;
        return this;
    }

    public void setValuetype(String valuetype) {
        this.valuetype = valuetype;
    }

    public Integer getCustomerId() {
        return this.customerId;
    }

    public CustSecurityChar customerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustSecurityChar customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustSecurityChar)) {
            return false;
        }
        return id != null && id.equals(((CustSecurityChar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustSecurityChar{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", valuetype='" + getValuetype() + "'" +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
