package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.AssetTypeQuantity;
import com.mycompany.myapp.repository.AssetTypeQuantityRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.AssetTypeQuantity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AssetTypeQuantityResource {

    private final Logger log = LoggerFactory.getLogger(AssetTypeQuantityResource.class);

    private static final String ENTITY_NAME = "assetTypeQuantity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetTypeQuantityRepository assetTypeQuantityRepository;

    public AssetTypeQuantityResource(AssetTypeQuantityRepository assetTypeQuantityRepository) {
        this.assetTypeQuantityRepository = assetTypeQuantityRepository;
    }

    /**
     * {@code POST  /asset-type-quantities} : Create a new assetTypeQuantity.
     *
     * @param assetTypeQuantity the assetTypeQuantity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assetTypeQuantity, or with status {@code 400 (Bad Request)} if the assetTypeQuantity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/asset-type-quantities")
    public ResponseEntity<AssetTypeQuantity> createAssetTypeQuantity(@RequestBody AssetTypeQuantity assetTypeQuantity)
        throws URISyntaxException {
        log.debug("REST request to save AssetTypeQuantity : {}", assetTypeQuantity);
        if (assetTypeQuantity.getId() != null) {
            throw new BadRequestAlertException("A new assetTypeQuantity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssetTypeQuantity result = assetTypeQuantityRepository.save(assetTypeQuantity);
        return ResponseEntity
            .created(new URI("/api/asset-type-quantities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /asset-type-quantities/:id} : Updates an existing assetTypeQuantity.
     *
     * @param id the id of the assetTypeQuantity to save.
     * @param assetTypeQuantity the assetTypeQuantity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetTypeQuantity,
     * or with status {@code 400 (Bad Request)} if the assetTypeQuantity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assetTypeQuantity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/asset-type-quantities/{id}")
    public ResponseEntity<AssetTypeQuantity> updateAssetTypeQuantity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssetTypeQuantity assetTypeQuantity
    ) throws URISyntaxException {
        log.debug("REST request to update AssetTypeQuantity : {}, {}", id, assetTypeQuantity);
        if (assetTypeQuantity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetTypeQuantity.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetTypeQuantityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AssetTypeQuantity result = assetTypeQuantityRepository.save(assetTypeQuantity);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assetTypeQuantity.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /asset-type-quantities/:id} : Partial updates given fields of an existing assetTypeQuantity, field will ignore if it is null
     *
     * @param id the id of the assetTypeQuantity to save.
     * @param assetTypeQuantity the assetTypeQuantity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetTypeQuantity,
     * or with status {@code 400 (Bad Request)} if the assetTypeQuantity is not valid,
     * or with status {@code 404 (Not Found)} if the assetTypeQuantity is not found,
     * or with status {@code 500 (Internal Server Error)} if the assetTypeQuantity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/asset-type-quantities/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AssetTypeQuantity> partialUpdateAssetTypeQuantity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssetTypeQuantity assetTypeQuantity
    ) throws URISyntaxException {
        log.debug("REST request to partial update AssetTypeQuantity partially : {}, {}", id, assetTypeQuantity);
        if (assetTypeQuantity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetTypeQuantity.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetTypeQuantityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssetTypeQuantity> result = assetTypeQuantityRepository
            .findById(assetTypeQuantity.getId())
            .map(
                existingAssetTypeQuantity -> {
                    if (assetTypeQuantity.getQuantity() != null) {
                        existingAssetTypeQuantity.setQuantity(assetTypeQuantity.getQuantity());
                    }

                    return existingAssetTypeQuantity;
                }
            )
            .map(assetTypeQuantityRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assetTypeQuantity.getId().toString())
        );
    }

    /**
     * {@code GET  /asset-type-quantities} : get all the assetTypeQuantities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assetTypeQuantities in body.
     */
    @GetMapping("/asset-type-quantities")
    public List<AssetTypeQuantity> getAllAssetTypeQuantities() {
        log.debug("REST request to get all AssetTypeQuantities");
        return assetTypeQuantityRepository.findAll();
    }

    /**
     * {@code GET  /asset-type-quantities/:id} : get the "id" assetTypeQuantity.
     *
     * @param id the id of the assetTypeQuantity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetTypeQuantity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/asset-type-quantities/{id}")
    public ResponseEntity<AssetTypeQuantity> getAssetTypeQuantity(@PathVariable Long id) {
        log.debug("REST request to get AssetTypeQuantity : {}", id);
        Optional<AssetTypeQuantity> assetTypeQuantity = assetTypeQuantityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(assetTypeQuantity);
    }

    /**
     * {@code DELETE  /asset-type-quantities/:id} : delete the "id" assetTypeQuantity.
     *
     * @param id the id of the assetTypeQuantity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/asset-type-quantities/{id}")
    public ResponseEntity<Void> deleteAssetTypeQuantity(@PathVariable Long id) {
        log.debug("REST request to delete AssetTypeQuantity : {}", id);
        assetTypeQuantityRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
