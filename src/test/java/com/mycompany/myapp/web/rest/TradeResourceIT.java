package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Trade;
import com.mycompany.myapp.domain.enumeration.TradeStatus;
import com.mycompany.myapp.domain.enumeration.TradeType;
import com.mycompany.myapp.repository.TradeRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TradeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TradeResourceIT {

    private static final TradeStatus DEFAULT_TRADE_STATUS = TradeStatus.PENDING;
    private static final TradeStatus UPDATED_TRADE_STATUS = TradeStatus.PART_FULFILLED;

    private static final TradeType DEFAULT_TRADE_TYPE = TradeType.BUY;
    private static final TradeType UPDATED_TRADE_TYPE = TradeType.SELL;

    private static final Integer DEFAULT_ASSET_QTY = 1;
    private static final Integer UPDATED_ASSET_QTY = 2;

    private static final Integer DEFAULT_CREDITS = 1;
    private static final Integer UPDATED_CREDITS = 2;

    private static final String ENTITY_API_URL = "/api/trades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTradeMockMvc;

    private Trade trade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trade createEntity(EntityManager em) {
        Trade trade = new Trade()
            .tradeStatus(DEFAULT_TRADE_STATUS)
            .tradeType(DEFAULT_TRADE_TYPE)
            .assetQty(DEFAULT_ASSET_QTY)
            .credits(DEFAULT_CREDITS);
        return trade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trade createUpdatedEntity(EntityManager em) {
        Trade trade = new Trade()
            .tradeStatus(UPDATED_TRADE_STATUS)
            .tradeType(UPDATED_TRADE_TYPE)
            .assetQty(UPDATED_ASSET_QTY)
            .credits(UPDATED_CREDITS);
        return trade;
    }

    @BeforeEach
    public void initTest() {
        trade = createEntity(em);
    }

    @Test
    @Transactional
    void createTrade() throws Exception {
        int databaseSizeBeforeCreate = tradeRepository.findAll().size();
        // Create the Trade
        restTradeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trade)))
            .andExpect(status().isCreated());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeCreate + 1);
        Trade testTrade = tradeList.get(tradeList.size() - 1);
        assertThat(testTrade.getTradeStatus()).isEqualTo(DEFAULT_TRADE_STATUS);
        assertThat(testTrade.getTradeType()).isEqualTo(DEFAULT_TRADE_TYPE);
        assertThat(testTrade.getAssetQty()).isEqualTo(DEFAULT_ASSET_QTY);
        assertThat(testTrade.getCredits()).isEqualTo(DEFAULT_CREDITS);
    }

    @Test
    @Transactional
    void createTradeWithExistingId() throws Exception {
        // Create the Trade with an existing ID
        trade.setId(1L);

        int databaseSizeBeforeCreate = tradeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTradeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trade)))
            .andExpect(status().isBadRequest());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrades() throws Exception {
        // Initialize the database
        tradeRepository.saveAndFlush(trade);

        // Get all the tradeList
        restTradeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trade.getId().intValue())))
            .andExpect(jsonPath("$.[*].tradeStatus").value(hasItem(DEFAULT_TRADE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].tradeType").value(hasItem(DEFAULT_TRADE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].assetQty").value(hasItem(DEFAULT_ASSET_QTY)))
            .andExpect(jsonPath("$.[*].credits").value(hasItem(DEFAULT_CREDITS)));
    }

    @Test
    @Transactional
    void getTrade() throws Exception {
        // Initialize the database
        tradeRepository.saveAndFlush(trade);

        // Get the trade
        restTradeMockMvc
            .perform(get(ENTITY_API_URL_ID, trade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trade.getId().intValue()))
            .andExpect(jsonPath("$.tradeStatus").value(DEFAULT_TRADE_STATUS.toString()))
            .andExpect(jsonPath("$.tradeType").value(DEFAULT_TRADE_TYPE.toString()))
            .andExpect(jsonPath("$.assetQty").value(DEFAULT_ASSET_QTY))
            .andExpect(jsonPath("$.credits").value(DEFAULT_CREDITS));
    }

    @Test
    @Transactional
    void getNonExistingTrade() throws Exception {
        // Get the trade
        restTradeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTrade() throws Exception {
        // Initialize the database
        tradeRepository.saveAndFlush(trade);

        int databaseSizeBeforeUpdate = tradeRepository.findAll().size();

        // Update the trade
        Trade updatedTrade = tradeRepository.findById(trade.getId()).get();
        // Disconnect from session so that the updates on updatedTrade are not directly saved in db
        em.detach(updatedTrade);
        updatedTrade.tradeStatus(UPDATED_TRADE_STATUS).tradeType(UPDATED_TRADE_TYPE).assetQty(UPDATED_ASSET_QTY).credits(UPDATED_CREDITS);

        restTradeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTrade.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTrade))
            )
            .andExpect(status().isOk());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeUpdate);
        Trade testTrade = tradeList.get(tradeList.size() - 1);
        assertThat(testTrade.getTradeStatus()).isEqualTo(UPDATED_TRADE_STATUS);
        assertThat(testTrade.getTradeType()).isEqualTo(UPDATED_TRADE_TYPE);
        assertThat(testTrade.getAssetQty()).isEqualTo(UPDATED_ASSET_QTY);
        assertThat(testTrade.getCredits()).isEqualTo(UPDATED_CREDITS);
    }

    @Test
    @Transactional
    void putNonExistingTrade() throws Exception {
        int databaseSizeBeforeUpdate = tradeRepository.findAll().size();
        trade.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTradeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trade.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrade() throws Exception {
        int databaseSizeBeforeUpdate = tradeRepository.findAll().size();
        trade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTradeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrade() throws Exception {
        int databaseSizeBeforeUpdate = tradeRepository.findAll().size();
        trade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTradeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trade)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTradeWithPatch() throws Exception {
        // Initialize the database
        tradeRepository.saveAndFlush(trade);

        int databaseSizeBeforeUpdate = tradeRepository.findAll().size();

        // Update the trade using partial update
        Trade partialUpdatedTrade = new Trade();
        partialUpdatedTrade.setId(trade.getId());

        partialUpdatedTrade.tradeStatus(UPDATED_TRADE_STATUS);

        restTradeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrade))
            )
            .andExpect(status().isOk());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeUpdate);
        Trade testTrade = tradeList.get(tradeList.size() - 1);
        assertThat(testTrade.getTradeStatus()).isEqualTo(UPDATED_TRADE_STATUS);
        assertThat(testTrade.getTradeType()).isEqualTo(DEFAULT_TRADE_TYPE);
        assertThat(testTrade.getAssetQty()).isEqualTo(DEFAULT_ASSET_QTY);
        assertThat(testTrade.getCredits()).isEqualTo(DEFAULT_CREDITS);
    }

    @Test
    @Transactional
    void fullUpdateTradeWithPatch() throws Exception {
        // Initialize the database
        tradeRepository.saveAndFlush(trade);

        int databaseSizeBeforeUpdate = tradeRepository.findAll().size();

        // Update the trade using partial update
        Trade partialUpdatedTrade = new Trade();
        partialUpdatedTrade.setId(trade.getId());

        partialUpdatedTrade
            .tradeStatus(UPDATED_TRADE_STATUS)
            .tradeType(UPDATED_TRADE_TYPE)
            .assetQty(UPDATED_ASSET_QTY)
            .credits(UPDATED_CREDITS);

        restTradeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrade))
            )
            .andExpect(status().isOk());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeUpdate);
        Trade testTrade = tradeList.get(tradeList.size() - 1);
        assertThat(testTrade.getTradeStatus()).isEqualTo(UPDATED_TRADE_STATUS);
        assertThat(testTrade.getTradeType()).isEqualTo(UPDATED_TRADE_TYPE);
        assertThat(testTrade.getAssetQty()).isEqualTo(UPDATED_ASSET_QTY);
        assertThat(testTrade.getCredits()).isEqualTo(UPDATED_CREDITS);
    }

    @Test
    @Transactional
    void patchNonExistingTrade() throws Exception {
        int databaseSizeBeforeUpdate = tradeRepository.findAll().size();
        trade.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTradeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrade() throws Exception {
        int databaseSizeBeforeUpdate = tradeRepository.findAll().size();
        trade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTradeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrade() throws Exception {
        int databaseSizeBeforeUpdate = tradeRepository.findAll().size();
        trade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTradeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(trade)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrade() throws Exception {
        // Initialize the database
        tradeRepository.saveAndFlush(trade);

        int databaseSizeBeforeDelete = tradeRepository.findAll().size();

        // Delete the trade
        restTradeMockMvc
            .perform(delete(ENTITY_API_URL_ID, trade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
