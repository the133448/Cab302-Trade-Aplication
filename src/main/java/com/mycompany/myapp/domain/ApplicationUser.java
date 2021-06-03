package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ApplicationUser.
 */
@Entity
@Table(name = "application_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplicationUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private User internalUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "assetTypeQuantities", "organisations" }, allowSetters = true)
    private Organisation organisation;

    @OneToMany(mappedBy = "applicationUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assetType", "organisation", "applicationUser" }, allowSetters = true)
    private Set<Trade> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationUser id(Long id) {
        this.id = id;
        return this;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public ApplicationUser internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public Organisation getOrganisation() {
        return this.organisation;
    }

    public ApplicationUser organisation(Organisation organisation) {
        this.setOrganisation(organisation);
        return this;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Set<Trade> getUsers() {
        return this.users;
    }

    public ApplicationUser users(Set<Trade> trades) {
        this.setUsers(trades);
        return this;
    }

    public ApplicationUser addUser(Trade trade) {
        this.users.add(trade);
        trade.setApplicationUser(this);
        return this;
    }

    public ApplicationUser removeUser(Trade trade) {
        this.users.remove(trade);
        trade.setApplicationUser(null);
        return this;
    }

    public void setUsers(Set<Trade> trades) {
        if (this.users != null) {
            this.users.forEach(i -> i.setApplicationUser(null));
        }
        if (trades != null) {
            trades.forEach(i -> i.setApplicationUser(this));
        }
        this.users = trades;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUser)) {
            return false;
        }
        return id != null && id.equals(((ApplicationUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUser{" +
            "id=" + getId() +
            "}";
    }
}
