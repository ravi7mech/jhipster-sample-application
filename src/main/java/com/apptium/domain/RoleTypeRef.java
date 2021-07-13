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
 * A RoleTypeRef.
 */
@Entity
@Table(name = "role_type_ref")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleTypeRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "rolename", nullable = false)
    private String rolename;

    @NotNull
    @Column(name = "roletype", nullable = false)
    private String roletype;

    @OneToMany(mappedBy = "roleTypeRef")
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

    public RoleTypeRef id(Long id) {
        this.id = id;
        return this;
    }

    public String getRolename() {
        return this.rolename;
    }

    public RoleTypeRef rolename(String rolename) {
        this.rolename = rolename;
        return this;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRoletype() {
        return this.roletype;
    }

    public RoleTypeRef roletype(String roletype) {
        this.roletype = roletype;
        return this;
    }

    public void setRoletype(String roletype) {
        this.roletype = roletype;
    }

    public Set<CustRelParty> getCustRelParties() {
        return this.custRelParties;
    }

    public RoleTypeRef custRelParties(Set<CustRelParty> custRelParties) {
        this.setCustRelParties(custRelParties);
        return this;
    }

    public RoleTypeRef addCustRelParty(CustRelParty custRelParty) {
        this.custRelParties.add(custRelParty);
        custRelParty.setRoleTypeRef(this);
        return this;
    }

    public RoleTypeRef removeCustRelParty(CustRelParty custRelParty) {
        this.custRelParties.remove(custRelParty);
        custRelParty.setRoleTypeRef(null);
        return this;
    }

    public void setCustRelParties(Set<CustRelParty> custRelParties) {
        if (this.custRelParties != null) {
            this.custRelParties.forEach(i -> i.setRoleTypeRef(null));
        }
        if (custRelParties != null) {
            custRelParties.forEach(i -> i.setRoleTypeRef(this));
        }
        this.custRelParties = custRelParties;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleTypeRef)) {
            return false;
        }
        return id != null && id.equals(((RoleTypeRef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleTypeRef{" +
            "id=" + getId() +
            ", rolename='" + getRolename() + "'" +
            ", roletype='" + getRoletype() + "'" +
            "}";
    }
}
