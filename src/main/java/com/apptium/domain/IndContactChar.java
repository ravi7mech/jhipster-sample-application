package com.apptium.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IndContactChar.
 */
@Entity
@Table(name = "ind_contact_char")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IndContactChar implements Serializable {

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
    private Boolean isEmailValid;

    @NotNull
    @Column(name = "is_address_valid", nullable = false)
    private Boolean isAddressValid;

    @NotNull
    @Column(name = "individual_contact_id", nullable = false)
    private Integer individualContactId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "indContactChars", "individual" }, allowSetters = true)
    private IndContact indContact;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IndContactChar id(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public IndContactChar type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStreet1() {
        return this.street1;
    }

    public IndContactChar street1(String street1) {
        this.street1 = street1;
        return this;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return this.street2;
    }

    public IndContactChar street2(String street2) {
        this.street2 = street2;
        return this;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return this.city;
    }

    public IndContactChar city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateOrProvince() {
        return this.stateOrProvince;
    }

    public IndContactChar stateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
        return this;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public String getCountry() {
        return this.country;
    }

    public IndContactChar country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getPostCode() {
        return this.postCode;
    }

    public IndContactChar postCode(Long postCode) {
        this.postCode = postCode;
        return this;
    }

    public void setPostCode(Long postCode) {
        this.postCode = postCode;
    }

    public Long getPhoneNumber() {
        return this.phoneNumber;
    }

    public IndContactChar phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public IndContactChar emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Long getFaxNumber() {
        return this.faxNumber;
    }

    public IndContactChar faxNumber(Long faxNumber) {
        this.faxNumber = faxNumber;
        return this;
    }

    public void setFaxNumber(Long faxNumber) {
        this.faxNumber = faxNumber;
    }

    public Float getLatitude() {
        return this.latitude;
    }

    public IndContactChar latitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return this.longitude;
    }

    public IndContactChar longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Integer getSvContactId() {
        return this.svContactId;
    }

    public IndContactChar svContactId(Integer svContactId) {
        this.svContactId = svContactId;
        return this;
    }

    public void setSvContactId(Integer svContactId) {
        this.svContactId = svContactId;
    }

    public Boolean getIsEmailValid() {
        return this.isEmailValid;
    }

    public IndContactChar isEmailValid(Boolean isEmailValid) {
        this.isEmailValid = isEmailValid;
        return this;
    }

    public void setIsEmailValid(Boolean isEmailValid) {
        this.isEmailValid = isEmailValid;
    }

    public Boolean getIsAddressValid() {
        return this.isAddressValid;
    }

    public IndContactChar isAddressValid(Boolean isAddressValid) {
        this.isAddressValid = isAddressValid;
        return this;
    }

    public void setIsAddressValid(Boolean isAddressValid) {
        this.isAddressValid = isAddressValid;
    }

    public Integer getIndividualContactId() {
        return this.individualContactId;
    }

    public IndContactChar individualContactId(Integer individualContactId) {
        this.individualContactId = individualContactId;
        return this;
    }

    public void setIndividualContactId(Integer individualContactId) {
        this.individualContactId = individualContactId;
    }

    public IndContact getIndContact() {
        return this.indContact;
    }

    public IndContactChar indContact(IndContact indContact) {
        this.setIndContact(indContact);
        return this;
    }

    public void setIndContact(IndContact indContact) {
        this.indContact = indContact;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndContactChar)) {
            return false;
        }
        return id != null && id.equals(((IndContactChar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndContactChar{" +
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
            ", isEmailValid='" + getIsEmailValid() + "'" +
            ", isAddressValid='" + getIsAddressValid() + "'" +
            ", individualContactId=" + getIndividualContactId() +
            "}";
    }
}
