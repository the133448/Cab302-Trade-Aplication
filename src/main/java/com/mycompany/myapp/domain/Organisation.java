package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Organisation.
 */
@Entity
@Table(name = "organisation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "credits")
    private Integer credits;

    @Column(name = "buy_order_credits")
    private Integer buyOrderCredits;

    @OneToMany(mappedBy = "ownedAssets")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownedAssets" }, allowSetters = true)
    private Set<AssetTypeQuantity> assetTypeQuantities = new HashSet<>();

    @OneToMany(mappedBy = "organisation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assetType", "organisation", "applicationUser" }, allowSetters = true)
    private Set<Trade> organisations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organisation id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Organisation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredits() {
        return this.credits;
    }

    public Organisation credits(Integer credits) {
        this.credits = credits;
        return this;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getBuyOrderCredits() {
        return this.buyOrderCredits;
    }

    public Organisation buyOrderCredits(Integer buyOrderCredits) {
        this.buyOrderCredits = buyOrderCredits;
        return this;
    }

    public void setBuyOrderCredits(Integer buyOrderCredits) {
        this.buyOrderCredits = buyOrderCredits;
    }

    public Set<AssetTypeQuantity> getAssetTypeQuantities() {
        return this.assetTypeQuantities;
    }

    public Organisation assetTypeQuantities(Set<AssetTypeQuantity> assetTypeQuantities) {
        this.setAssetTypeQuantities(assetTypeQuantities);
        return this;
    }

    public Organisation addAssetTypeQuantity(AssetTypeQuantity assetTypeQuantity) {
        this.assetTypeQuantities.add(assetTypeQuantity);
        assetTypeQuantity.setOwnedAssets(this);
        return this;
    }

    public Organisation removeAssetTypeQuantity(AssetTypeQuantity assetTypeQuantity) {
        this.assetTypeQuantities.remove(assetTypeQuantity);
        assetTypeQuantity.setOwnedAssets(null);
        return this;
    }

    public void setAssetTypeQuantities(Set<AssetTypeQuantity> assetTypeQuantities) {
        if (this.assetTypeQuantities != null) {
            this.assetTypeQuantities.forEach(i -> i.setOwnedAssets(null));
        }
        if (assetTypeQuantities != null) {
            assetTypeQuantities.forEach(i -> i.setOwnedAssets(this));
        }
        this.assetTypeQuantities = assetTypeQuantities;
    }

    public Set<Trade> getOrganisations() {
        return this.organisations;
    }

    public Organisation organisations(Set<Trade> trades) {
        this.setOrganisations(trades);
        return this;
    }

    public Organisation addOrganisation(Trade trade) {
        this.organisations.add(trade);
        trade.setOrganisation(this);
        return this;
    }

    public Organisation removeOrganisation(Trade trade) {
        this.organisations.remove(trade);
        trade.setOrganisation(null);
        return this;
    }

    public void setOrganisations(Set<Trade> trades) {
        if (this.organisations != null) {
            this.organisations.forEach(i -> i.setOrganisation(null));
        }
        if (trades != null) {
            trades.forEach(i -> i.setOrganisation(this));
        }
        this.organisations = trades;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organisation)) {
            return false;
        }
        return id != null && id.equals(((Organisation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organisation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", credits=" + getCredits() +
            ", buyOrderCredits=" + getBuyOrderCredits() +
            "}";
    }
}
