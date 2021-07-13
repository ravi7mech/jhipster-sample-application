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
 * A Department.
 */
@Entity
@Table(name = "department")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "department_head", nullable = false)
    private String departmentHead;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "individual_id", nullable = false)
    private Integer individualId;

    @OneToMany(mappedBy = "department")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customer", "department", "roleTypeRef", "individual" }, allowSetters = true)
    private Set<CustRelParty> custRelParties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Department id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Department name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentHead() {
        return this.departmentHead;
    }

    public Department departmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
        return this;
    }

    public void setDepartmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
    }

    public String getStatus() {
        return this.status;
    }

    public Department status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIndividualId() {
        return this.individualId;
    }

    public Department individualId(Integer individualId) {
        this.individualId = individualId;
        return this;
    }

    public void setIndividualId(Integer individualId) {
        this.individualId = individualId;
    }

    public Set<CustRelParty> getCustRelParties() {
        return this.custRelParties;
    }

    public Department custRelParties(Set<CustRelParty> custRelParties) {
        this.setCustRelParties(custRelParties);
        return this;
    }

    public Department addCustRelParty(CustRelParty custRelParty) {
        this.custRelParties.add(custRelParty);
        custRelParty.setDepartment(this);
        return this;
    }

    public Department removeCustRelParty(CustRelParty custRelParty) {
        this.custRelParties.remove(custRelParty);
        custRelParty.setDepartment(null);
        return this;
    }

    public void setCustRelParties(Set<CustRelParty> custRelParties) {
        if (this.custRelParties != null) {
            this.custRelParties.forEach(i -> i.setDepartment(null));
        }
        if (custRelParties != null) {
            custRelParties.forEach(i -> i.setDepartment(this));
        }
        this.custRelParties = custRelParties;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Department)) {
            return false;
        }
        return id != null && id.equals(((Department) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Department{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", departmentHead='" + getDepartmentHead() + "'" +
            ", status='" + getStatus() + "'" +
            ", individualId=" + getIndividualId() +
            "}";
    }
}
