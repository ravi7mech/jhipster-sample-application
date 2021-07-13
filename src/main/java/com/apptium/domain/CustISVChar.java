package com.apptium.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CustISVChar.
 */
@Entity
@Table(name = "cust_isv_char")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustISVChar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "value", nullable = false)
    private Integer value;

    @NotNull
    @Column(name = "valuetype", nullable = false)
    private String valuetype;

    @NotNull
    @Column(name = "customer_isv_id", nullable = false)
    private Integer customerISVId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "custISVChars", "customer" }, allowSetters = true)
    private CustISVRef custISVRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustISVChar id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public CustISVChar name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return this.value;
    }

    public CustISVChar value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getValuetype() {
        return this.valuetype;
    }

    public CustISVChar valuetype(String valuetype) {
        this.valuetype = valuetype;
        return this;
    }

    public void setValuetype(String valuetype) {
        this.valuetype = valuetype;
    }

    public Integer getCustomerISVId() {
        return this.customerISVId;
    }

    public CustISVChar customerISVId(Integer customerISVId) {
        this.customerISVId = customerISVId;
        return this;
    }

    public void setCustomerISVId(Integer customerISVId) {
        this.customerISVId = customerISVId;
    }

    public CustISVRef getCustISVRef() {
        return this.custISVRef;
    }

    public CustISVChar custISVRef(CustISVRef custISVRef) {
        this.setCustISVRef(custISVRef);
        return this;
    }

    public void setCustISVRef(CustISVRef custISVRef) {
        this.custISVRef = custISVRef;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustISVChar)) {
            return false;
        }
        return id != null && id.equals(((CustISVChar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustISVChar{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value=" + getValue() +
            ", valuetype='" + getValuetype() + "'" +
            ", customerISVId=" + getCustomerISVId() +
            "}";
    }
}
