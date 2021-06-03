package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AssetTypeQuantity.
 */
@Entity
@Table(name = "asset_type_quantity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssetTypeQuantity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JsonIgnoreProperties(value = { "assetTypeQuantities", "organisations" }, allowSetters = true)
    private Organisation ownedAssets;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssetTypeQuantity id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public AssetTypeQuantity quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Organisation getOwnedAssets() {
        return this.ownedAssets;
    }

    public AssetTypeQuantity ownedAssets(Organisation organisation) {
        this.setOwnedAssets(organisation);
        return this;
    }

    public void setOwnedAssets(Organisation organisation) {
        this.ownedAssets = organisation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetTypeQuantity)) {
            return false;
        }
        return id != null && id.equals(((AssetTypeQuantity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetTypeQuantity{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            "}";
    }
}
