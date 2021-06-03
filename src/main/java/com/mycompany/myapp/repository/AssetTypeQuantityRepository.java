package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AssetTypeQuantity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AssetTypeQuantity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetTypeQuantityRepository extends JpaRepository<AssetTypeQuantity, Long> {}
