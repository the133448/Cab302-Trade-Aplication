package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Trade;
import com.mycompany.myapp.repository.TradeRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Trade}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TradeResource {

    private final Logger log = LoggerFactory.getLogger(TradeResource.class);

    private static final String ENTITY_NAME = "trade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TradeRepository tradeRepository;

    public TradeResource(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * {@code POST  /trades} : Create a new trade.
     *
     * @param trade the trade to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trade, or with status {@code 400 (Bad Request)} if the trade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trades")
    public ResponseEntity<Trade> createTrade(@RequestBody Trade trade) throws URISyntaxException {
        log.debug("REST request to save Trade : {}", trade);
        if (trade.getId() != null) {
            throw new BadRequestAlertException("A new trade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Trade result = tradeRepository.save(trade);
        return ResponseEntity
            .created(new URI("/api/trades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trades/:id} : Updates an existing trade.
     *
     * @param id the id of the trade to save.
     * @param trade the trade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trade,
     * or with status {@code 400 (Bad Request)} if the trade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trades/{id}")
    public ResponseEntity<Trade> updateTrade(@PathVariable(value = "id", required = false) final Long id, @RequestBody Trade trade)
        throws URISyntaxException {
        log.debug("REST request to update Trade : {}, {}", id, trade);
        if (trade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trade.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tradeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Trade result = tradeRepository.save(trade);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trade.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trades/:id} : Partial updates given fields of an existing trade, field will ignore if it is null
     *
     * @param id the id of the trade to save.
     * @param trade the trade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trade,
     * or with status {@code 400 (Bad Request)} if the trade is not valid,
     * or with status {@code 404 (Not Found)} if the trade is not found,
     * or with status {@code 500 (Internal Server Error)} if the trade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trades/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Trade> partialUpdateTrade(@PathVariable(value = "id", required = false) final Long id, @RequestBody Trade trade)
        throws URISyntaxException {
        log.debug("REST request to partial update Trade partially : {}, {}", id, trade);
        if (trade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trade.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tradeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Trade> result = tradeRepository
            .findById(trade.getId())
            .map(
                existingTrade -> {
                    if (trade.getTradeStatus() != null) {
                        existingTrade.setTradeStatus(trade.getTradeStatus());
                    }
                    if (trade.getTradeType() != null) {
                        existingTrade.setTradeType(trade.getTradeType());
                    }
                    if (trade.getAssetQty() != null) {
                        existingTrade.setAssetQty(trade.getAssetQty());
                    }
                    if (trade.getCredits() != null) {
                        existingTrade.setCredits(trade.getCredits());
                    }

                    return existingTrade;
                }
            )
            .map(tradeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trade.getId().toString())
        );
    }

    /**
     * {@code GET  /trades} : get all the trades.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trades in body.
     */
    @GetMapping("/trades")
    public List<Trade> getAllTrades() {
        log.debug("REST request to get all Trades");
        return tradeRepository.findAll();
    }

    /**
     * {@code GET  /trades/:id} : get the "id" trade.
     *
     * @param id the id of the trade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trades/{id}")
    public ResponseEntity<Trade> getTrade(@PathVariable Long id) {
        log.debug("REST request to get Trade : {}", id);
        Optional<Trade> trade = tradeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trade);
    }

    /**
     * {@code DELETE  /trades/:id} : delete the "id" trade.
     *
     * @param id the id of the trade to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trades/{id}")
    public ResponseEntity<Void> deleteTrade(@PathVariable Long id) {
        log.debug("REST request to delete Trade : {}", id);
        tradeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
