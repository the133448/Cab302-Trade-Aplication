package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.AssetTypeQuantity;
import com.mycompany.myapp.repository.AssetTypeQuantityRepository;
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
 * Integration tests for the {@link AssetTypeQuantityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssetTypeQuantityResourceIT {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String ENTITY_API_URL = "/api/asset-type-quantities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AssetTypeQuantityRepository assetTypeQuantityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetTypeQuantityMockMvc;

    private AssetTypeQuantity assetTypeQuantity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetTypeQuantity createEntity(EntityManager em) {
        AssetTypeQuantity assetTypeQuantity = new AssetTypeQuantity().quantity(DEFAULT_QUANTITY);
        return assetTypeQuantity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetTypeQuantity createUpdatedEntity(EntityManager em) {
        AssetTypeQuantity assetTypeQuantity = new AssetTypeQuantity().quantity(UPDATED_QUANTITY);
        return assetTypeQuantity;
    }

    @BeforeEach
    public void initTest() {
        assetTypeQuantity = createEntity(em);
    }

    @Test
    @Transactional
    void createAssetTypeQuantity() throws Exception {
        int databaseSizeBeforeCreate = assetTypeQuantityRepository.findAll().size();
        // Create the AssetTypeQuantity
        restAssetTypeQuantityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetTypeQuantity))
            )
            .andExpect(status().isCreated());

        // Validate the AssetTypeQuantity in the database
        List<AssetTypeQuantity> assetTypeQuantityList = assetTypeQuantityRepository.findAll();
        assertThat(assetTypeQuantityList).hasSize(databaseSizeBeforeCreate + 1);
        AssetTypeQuantity testAssetTypeQuantity = assetTypeQuantityList.get(assetTypeQuantityList.size() - 1);
        assertThat(testAssetTypeQuantity.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void createAssetTypeQuantityWithExistingId() throws Exception {
        // Create the AssetTypeQuantity with an existing ID
        assetTypeQuantity.setId(1L);

        int databaseSizeBeforeCreate = assetTypeQuantityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetTypeQuantityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetTypeQuantity))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetTypeQuantity in the database
        List<AssetTypeQuantity> assetTypeQuantityList = assetTypeQuantityRepository.findAll();
        assertThat(assetTypeQuantityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAssetTypeQuantities() throws Exception {
        // Initialize the database
        assetTypeQuantityRepository.saveAndFlush(assetTypeQuantity);

        // Get all the assetTypeQuantityList
        restAssetTypeQuantityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetTypeQuantity.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    void getAssetTypeQuantity() throws Exception {
        // Initialize the database
        assetTypeQuantityRepository.saveAndFlush(assetTypeQuantity);

        // Get the assetTypeQuantity
        restAssetTypeQuantityMockMvc
            .perform(get(ENTITY_API_URL_ID, assetTypeQuantity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assetTypeQuantity.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    void getNonExistingAssetTypeQuantity() throws Exception {
        // Get the assetTypeQuantity
        restAssetTypeQuantityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAssetTypeQuantity() throws Exception {
        // Initialize the database
        assetTypeQuantityRepository.saveAndFlush(assetTypeQuantity);

        int databaseSizeBeforeUpdate = assetTypeQuantityRepository.findAll().size();

        // Update the assetTypeQuantity
        AssetTypeQuantity updatedAssetTypeQuantity = assetTypeQuantityRepository.findById(assetTypeQuantity.getId()).get();
        // Disconnect from session so that the updates on updatedAssetTypeQuantity are not directly saved in db
        em.detach(updatedAssetTypeQuantity);
        updatedAssetTypeQuantity.quantity(UPDATED_QUANTITY);

        restAssetTypeQuantityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAssetTypeQuantity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAssetTypeQuantity))
            )
            .andExpect(status().isOk());

        // Validate the AssetTypeQuantity in the database
        List<AssetTypeQuantity> assetTypeQuantityList = assetTypeQuantityRepository.findAll();
        assertThat(assetTypeQuantityList).hasSize(databaseSizeBeforeUpdate);
        AssetTypeQuantity testAssetTypeQuantity = assetTypeQuantityList.get(assetTypeQuantityList.size() - 1);
        assertThat(testAssetTypeQuantity.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void putNonExistingAssetTypeQuantity() throws Exception {
        int databaseSizeBeforeUpdate = assetTypeQuantityRepository.findAll().size();
        assetTypeQuantity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetTypeQuantityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetTypeQuantity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetTypeQuantity))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetTypeQuantity in the database
        List<AssetTypeQuantity> assetTypeQuantityList = assetTypeQuantityRepository.findAll();
        assertThat(assetTypeQuantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssetTypeQuantity() throws Exception {
        int databaseSizeBeforeUpdate = assetTypeQuantityRepository.findAll().size();
        assetTypeQuantity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetTypeQuantityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetTypeQuantity))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetTypeQuantity in the database
        List<AssetTypeQuantity> assetTypeQuantityList = assetTypeQuantityRepository.findAll();
        assertThat(assetTypeQuantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssetTypeQuantity() throws Exception {
        int databaseSizeBeforeUpdate = assetTypeQuantityRepository.findAll().size();
        assetTypeQuantity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetTypeQuantityMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetTypeQuantity))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetTypeQuantity in the database
        List<AssetTypeQuantity> assetTypeQuantityList = assetTypeQuantityRepository.findAll();
        assertThat(assetTypeQuantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssetTypeQuantityWithPatch() throws Exception {
        // Initialize the database
        assetTypeQuantityRepository.saveAndFlush(assetTypeQuantity);

        int databaseSizeBeforeUpdate = assetTypeQuantityRepository.findAll().size();

        // Update the assetTypeQuantity using partial update
        AssetTypeQuantity partialUpdatedAssetTypeQuantity = new AssetTypeQuantity();
        partialUpdatedAssetTypeQuantity.setId(assetTypeQuantity.getId());

        restAssetTypeQuantityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetTypeQuantity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssetTypeQuantity))
            )
            .andExpect(status().isOk());

        // Validate the AssetTypeQuantity in the database
        List<AssetTypeQuantity> assetTypeQuantityList = assetTypeQuantityRepository.findAll();
        assertThat(assetTypeQuantityList).hasSize(databaseSizeBeforeUpdate);
        AssetTypeQuantity testAssetTypeQuantity = assetTypeQuantityList.get(assetTypeQuantityList.size() - 1);
        assertThat(testAssetTypeQuantity.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void fullUpdateAssetTypeQuantityWithPatch() throws Exception {
        // Initialize the database
        assetTypeQuantityRepository.saveAndFlush(assetTypeQuantity);

        int databaseSizeBeforeUpdate = assetTypeQuantityRepository.findAll().size();

        // Update the assetTypeQuantity using partial update
        AssetTypeQuantity partialUpdatedAssetTypeQuantity = new AssetTypeQuantity();
        partialUpdatedAssetTypeQuantity.setId(assetTypeQuantity.getId());

        partialUpdatedAssetTypeQuantity.quantity(UPDATED_QUANTITY);

        restAssetTypeQuantityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetTypeQuantity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssetTypeQuantity))
            )
            .andExpect(status().isOk());

        // Validate the AssetTypeQuantity in the database
        List<AssetTypeQuantity> assetTypeQuantityList = assetTypeQuantityRepository.findAll();
        assertThat(assetTypeQuantityList).hasSize(databaseSizeBeforeUpdate);
        AssetTypeQuantity testAssetTypeQuantity = assetTypeQuantityList.get(assetTypeQuantityList.size() - 1);
        assertThat(testAssetTypeQuantity.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void patchNonExistingAssetTypeQuantity() throws Exception {
        int databaseSizeBeforeUpdate = assetTypeQuantityRepository.findAll().size();
        assetTypeQuantity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetTypeQuantityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assetTypeQuantity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetTypeQuantity))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetTypeQuantity in the database
        List<AssetTypeQuantity> assetTypeQuantityList = assetTypeQuantityRepository.findAll();
        assertThat(assetTypeQuantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssetTypeQuantity() throws Exception {
        int databaseSizeBeforeUpdate = assetTypeQuantityRepository.findAll().size();
        assetTypeQuantity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetTypeQuantityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetTypeQuantity))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetTypeQuantity in the database
        List<AssetTypeQuantity> assetTypeQuantityList = assetTypeQuantityRepository.findAll();
        assertThat(assetTypeQuantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssetTypeQuantity() throws Exception {
        int databaseSizeBeforeUpdate = assetTypeQuantityRepository.findAll().size();
        assetTypeQuantity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetTypeQuantityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetTypeQuantity))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetTypeQuantity in the database
        List<AssetTypeQuantity> assetTypeQuantityList = assetTypeQuantityRepository.findAll();
        assertThat(assetTypeQuantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssetTypeQuantity() throws Exception {
        // Initialize the database
        assetTypeQuantityRepository.saveAndFlush(assetTypeQuantity);

        int databaseSizeBeforeDelete = assetTypeQuantityRepository.findAll().size();

        // Delete the assetTypeQuantity
        restAssetTypeQuantityMockMvc
            .perform(delete(ENTITY_API_URL_ID, assetTypeQuantity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssetTypeQuantity> assetTypeQuantityList = assetTypeQuantityRepository.findAll();
        assertThat(assetTypeQuantityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
