package com.apptium.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CustISVRef.
 */
@Entity
@Table(name = "cust_isv_ref")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustISVRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "isvid", nullable = false)
    private Integer isvid;

    @NotNull
    @Column(name = "isvname", nullable = false)
    private String isvname;

    @NotNull
    @Column(name = "isvcustomer_id", nullable = false)
    private Integer isvcustomerId;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @OneToMany(mappedBy = "custISVRef")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "custISVRef" }, allowSetters = true)
    private Set<CustISVChar> custISVChars = new HashSet<>();

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

    public CustISVRef id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getIsvid() {
        return this.isvid;
    }

    public CustISVRef isvid(Integer isvid) {
        this.isvid = isvid;
        return this;
    }

    public void setIsvid(Integer isvid) {
        this.isvid = isvid;
    }

    public String getIsvname() {
        return this.isvname;
    }

    public CustISVRef isvname(String isvname) {
        this.isvname = isvname;
        return this;
    }

    public void setIsvname(String isvname) {
        this.isvname = isvname;
    }

    public Integer getIsvcustomerId() {
        return this.isvcustomerId;
    }

    public CustISVRef isvcustomerId(Integer isvcustomerId) {
        this.isvcustomerId = isvcustomerId;
        return this;
    }

    public void setIsvcustomerId(Integer isvcustomerId) {
        this.isvcustomerId = isvcustomerId;
    }

    public Integer getCustomerId() {
        return this.customerId;
    }

    public CustISVRef customerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Set<CustISVChar> getCustISVChars() {
        return this.custISVChars;
    }

    public CustISVRef custISVChars(Set<CustISVChar> custISVChars) {
        this.setCustISVChars(custISVChars);
        return this;
    }

    public CustISVRef addCustISVChar(CustISVChar custISVChar) {
        this.custISVChars.add(custISVChar);
        custISVChar.setCustISVRef(this);
        return this;
    }

    public CustISVRef removeCustISVChar(CustISVChar custISVChar) {
        this.custISVChars.remove(custISVChar);
        custISVChar.setCustISVRef(null);
        return this;
    }

    public void setCustISVChars(Set<CustISVChar> custISVChars) {
        if (this.custISVChars != null) {
            this.custISVChars.forEach(i -> i.setCustISVRef(null));
        }
        if (custISVChars != null) {
            custISVChars.forEach(i -> i.setCustISVRef(this));
        }
        this.custISVChars = custISVChars;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustISVRef customer(Customer customer) {
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
        if (!(o instanceof CustISVRef)) {
            return false;
        }
        return id != null && id.equals(((CustISVRef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustISVRef{" +
            "id=" + getId() +
            ", isvid=" + getIsvid() +
            ", isvname='" + getIsvname() + "'" +
            ", isvcustomerId=" + getIsvcustomerId() +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
