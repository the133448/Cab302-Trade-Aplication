package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Trade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Trade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {}
