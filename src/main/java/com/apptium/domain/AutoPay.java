package com.apptium.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AutoPay.
 */
@Entity
@Table(name = "auto_pay")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AutoPay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "channel", nullable = false)
    private String channel;

    @NotNull
    @Column(name = "auto_pay_id", nullable = false)
    private Integer autoPayId;

    @NotNull
    @Column(name = "debit_date", nullable = false)
    private Instant debitDate;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @NotNull
    @Column(name = "updated_date", nullable = false)
    private Instant updatedDate;

    @NotNull
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutoPay id(Long id) {
        this.id = id;
        return this;
    }

    public String getChannel() {
        return this.channel;
    }

    public AutoPay channel(String channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getAutoPayId() {
        return this.autoPayId;
    }

    public AutoPay autoPayId(Integer autoPayId) {
        this.autoPayId = autoPayId;
        return this;
    }

    public void setAutoPayId(Integer autoPayId) {
        this.autoPayId = autoPayId;
    }

    public Instant getDebitDate() {
        return this.debitDate;
    }

    public AutoPay debitDate(Instant debitDate) {
        this.debitDate = debitDate;
        return this;
    }

    public void setDebitDate(Instant debitDate) {
        this.debitDate = debitDate;
    }

    public String getStatus() {
        return this.status;
    }

    public AutoPay status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public AutoPay createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public AutoPay createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public AutoPay updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public AutoPay updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getCustomerId() {
        return this.customerId;
    }

    public AutoPay customerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AutoPay)) {
            return false;
        }
        return id != null && id.equals(((AutoPay) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutoPay{" +
            "id=" + getId() +
            ", channel='" + getChannel() + "'" +
            ", autoPayId=" + getAutoPayId() +
            ", debitDate='" + getDebitDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
