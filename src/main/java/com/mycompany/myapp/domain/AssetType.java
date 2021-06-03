package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AssetType.
 */
@Entity
@Table(name = "asset_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssetType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ownedAssets" }, allowSetters = true)
    private AssetTypeQuantity assetType;

    @OneToMany(mappedBy = "assetType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assetType", "organisation", "applicationUser" }, allowSetters = true)
    private Set<Trade> assetTypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssetType id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public AssetType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AssetTypeQuantity getAssetType() {
        return this.assetType;
    }

    public AssetType assetType(AssetTypeQuantity assetTypeQuantity) {
        this.setAssetType(assetTypeQuantity);
        return this;
    }

    public void setAssetType(AssetTypeQuantity assetTypeQuantity) {
        this.assetType = assetTypeQuantity;
    }

    public Set<Trade> getAssetTypes() {
        return this.assetTypes;
    }

    public AssetType assetTypes(Set<Trade> trades) {
        this.setAssetTypes(trades);
        return this;
    }

    public AssetType addAssetType(Trade trade) {
        this.assetTypes.add(trade);
        trade.setAssetType(this);
        return this;
    }

    public AssetType removeAssetType(Trade trade) {
        this.assetTypes.remove(trade);
        trade.setAssetType(null);
        return this;
    }

    public void setAssetTypes(Set<Trade> trades) {
        if (this.assetTypes != null) {
            this.assetTypes.forEach(i -> i.setAssetType(null));
        }
        if (trades != null) {
            trades.forEach(i -> i.setAssetType(this));
        }
        this.assetTypes = trades;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetType)) {
            return false;
        }
        return id != null && id.equals(((AssetType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
