package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.TradeStatus;
import com.mycompany.myapp.domain.enumeration.TradeType;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Trade.
 */
@Entity
@Table(name = "trade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Trade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_status")
    private TradeStatus tradeStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_type")
    private TradeType tradeType;

    @Column(name = "asset_qty")
    private Integer assetQty;

    @Column(name = "credits")
    private Integer credits;

    @ManyToOne
    @JsonIgnoreProperties(value = { "assetType", "assetTypes" }, allowSetters = true)
    private AssetType assetType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "assetTypeQuantities", "organisations" }, allowSetters = true)
    private Organisation organisation;

    @ManyToOne
    @JsonIgnoreProperties(value = { "internalUser", "organisation", "users" }, allowSetters = true)
    private ApplicationUser applicationUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trade id(Long id) {
        this.id = id;
        return this;
    }

    public TradeStatus getTradeStatus() {
        return this.tradeStatus;
    }

    public Trade tradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
        return this;
    }

    public void setTradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public TradeType getTradeType() {
        return this.tradeType;
    }

    public Trade tradeType(TradeType tradeType) {
        this.tradeType = tradeType;
        return this;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getAssetQty() {
        return this.assetQty;
    }

    public Trade assetQty(Integer assetQty) {
        this.assetQty = assetQty;
        return this;
    }

    public void setAssetQty(Integer assetQty) {
        this.assetQty = assetQty;
    }

    public Integer getCredits() {
        return this.credits;
    }

    public Trade credits(Integer credits) {
        this.credits = credits;
        return this;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public AssetType getAssetType() {
        return this.assetType;
    }

    public Trade assetType(AssetType assetType) {
        this.setAssetType(assetType);
        return this;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public Organisation getOrganisation() {
        return this.organisation;
    }

    public Trade organisation(Organisation organisation) {
        this.setOrganisation(organisation);
        return this;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public ApplicationUser getApplicationUser() {
        return this.applicationUser;
    }

    public Trade applicationUser(ApplicationUser applicationUser) {
        this.setApplicationUser(applicationUser);
        return this;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trade)) {
            return false;
        }
        return id != null && id.equals(((Trade) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trade{" +
            "id=" + getId() +
            ", tradeStatus='" + getTradeStatus() + "'" +
            ", tradeType='" + getTradeType() + "'" +
            ", assetQty=" + getAssetQty() +
            ", credits=" + getCredits() +
            "}";
    }
}
