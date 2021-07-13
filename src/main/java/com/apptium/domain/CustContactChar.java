package com.apptium.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CustContactChar.
 */
@Entity
@Table(name = "cust_contact_char")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustContactChar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "street_1", nullable = false)
    private String street1;

    @NotNull
    @Column(name = "street_2", nullable = false)
    private String street2;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "state_or_province", nullable = false)
    private String stateOrProvince;

    @NotNull
    @Column(name = "country", nullable = false)
    private String country;

    @NotNull
    @Column(name = "post_code", nullable = false)
    private Long postCode;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private Long phoneNumber;

    @NotNull
    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @NotNull
    @Column(name = "fax_number", nullable = false)
    private Long faxNumber;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Float latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Float longitude;

    @NotNull
    @Column(name = "sv_contact_id", nullable = false)
    private Integer svContactId;

    @Column(name = "is_email_valid")
    private Integer isEmailValid;

    @NotNull
    @Column(name = "is_address_valid", nullable = false)
    private Boolean isAddressValid;

    @NotNull
    @Column(name = "cust_contact_medium_id", nullable = false)
    private Integer custContactMediumId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "geographicSiteRefs", "custContactChars", "customer" }, allowSetters = true)
    private CustContact custContact;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustContactChar id(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public CustContactChar type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStreet1() {
        return this.street1;
    }

    public CustContactChar street1(String street1) {
        this.street1 = street1;
        return this;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return this.street2;
    }

    public CustContactChar street2(String street2) {
        this.street2 = street2;
        return this;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return this.city;
    }

    public CustContactChar city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateOrProvince() {
        return this.stateOrProvince;
    }

    public CustContactChar stateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
        return this;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public String getCountry() {
        return this.country;
    }

    public CustContactChar country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getPostCode() {
        return this.postCode;
    }

    public CustContactChar postCode(Long postCode) {
        this.postCode = postCode;
        return this;
    }

    public void setPostCode(Long postCode) {
        this.postCode = postCode;
    }

    public Long getPhoneNumber() {
        return this.phoneNumber;
    }

    public CustContactChar phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public CustContactChar emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Long getFaxNumber() {
        return this.faxNumber;
    }

    public CustContactChar faxNumber(Long faxNumber) {
        this.faxNumber = faxNumber;
        return this;
    }

    public void setFaxNumber(Long faxNumber) {
        this.faxNumber = faxNumber;
    }

    public Float getLatitude() {
        return this.latitude;
    }

    public CustContactChar latitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return this.longitude;
    }

    public CustContactChar longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Integer getSvContactId() {
        return this.svContactId;
    }

    public CustContactChar svContactId(Integer svContactId) {
        this.svContactId = svContactId;
        return this;
    }

    public void setSvContactId(Integer svContactId) {
        this.svContactId = svContactId;
    }

    public Integer getIsEmailValid() {
        return this.isEmailValid;
    }

    public CustContactChar isEmailValid(Integer isEmailValid) {
        this.isEmailValid = isEmailValid;
        return this;
    }

    public void setIsEmailValid(Integer isEmailValid) {
        this.isEmailValid = isEmailValid;
    }

    public Boolean getIsAddressValid() {
        return this.isAddressValid;
    }

    public CustContactChar isAddressValid(Boolean isAddressValid) {
        this.isAddressValid = isAddressValid;
        return this;
    }

    public void setIsAddressValid(Boolean isAddressValid) {
        this.isAddressValid = isAddressValid;
    }

    public Integer getCustContactMediumId() {
        return this.custContactMediumId;
    }

    public CustContactChar custContactMediumId(Integer custContactMediumId) {
        this.custContactMediumId = custContactMediumId;
        return this;
    }

    public void setCustContactMediumId(Integer custContactMediumId) {
        this.custContactMediumId = custContactMediumId;
    }

    public CustContact getCustContact() {
        return this.custContact;
    }

    public CustContactChar custContact(CustContact custContact) {
        this.setCustContact(custContact);
        return this;
    }

    public void setCustContact(CustContact custContact) {
        this.custContact = custContact;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustContactChar)) {
            return false;
        }
        return id != null && id.equals(((CustContactChar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustContactChar{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", street1='" + getStreet1() + "'" +
            ", street2='" + getStreet2() + "'" +
            ", city='" + getCity() + "'" +
            ", stateOrProvince='" + getStateOrProvince() + "'" +
            ", country='" + getCountry() + "'" +
            ", postCode=" + getPostCode() +
            ", phoneNumber=" + getPhoneNumber() +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", faxNumber=" + getFaxNumber() +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", svContactId=" + getSvContactId() +
            ", isEmailValid=" + getIsEmailValid() +
            ", isAddressValid='" + getIsAddressValid() + "'" +
            ", custContactMediumId=" + getCustContactMediumId() +
            "}";
    }
}
