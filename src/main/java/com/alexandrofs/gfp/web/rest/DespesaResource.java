package com.alexandrofs.gfp.web.rest;

import com.alexandrofs.gfp.domain.Despesa;
import com.alexandrofs.gfp.service.DespesaService;
import com.alexandrofs.gfp.web.rest.errors.BadRequestAlertException;
import com.alexandrofs.gfp.service.dto.DespesaCriteria;
import com.alexandrofs.gfp.service.DespesaQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.alexandrofs.gfp.domain.Despesa}.
 */
@RestController
@RequestMapping("/api")
public class DespesaResource {

    private final Logger log = LoggerFactory.getLogger(DespesaResource.class);

    private static final String ENTITY_NAME = "despesa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DespesaService despesaService;

    private final DespesaQueryService despesaQueryService;

    public DespesaResource(DespesaService despesaService, DespesaQueryService despesaQueryService) {
        this.despesaService = despesaService;
        this.despesaQueryService = despesaQueryService;
    }

    /**
     * {@code POST  /despesas} : Create a new despesa.
     *
     * @param despesa the despesa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new despesa, or with status {@code 400 (Bad Request)} if the despesa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/despesas")
    public ResponseEntity<Despesa> createDespesa(@Valid @RequestBody Despesa despesa) throws URISyntaxException {
        log.debug("REST request to save Despesa : {}", despesa);
        if (despesa.getId() != null) {
            throw new BadRequestAlertException("A new despesa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Despesa result = despesaService.save(despesa);
        return ResponseEntity.created(new URI("/api/despesas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /despesas} : Updates an existing despesa.
     *
     * @param despesa the despesa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated despesa,
     * or with status {@code 400 (Bad Request)} if the despesa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the despesa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/despesas")
    public ResponseEntity<Despesa> updateDespesa(@Valid @RequestBody Despesa despesa) throws URISyntaxException {
        log.debug("REST request to update Despesa : {}", despesa);
        if (despesa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Despesa result = despesaService.save(despesa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, despesa.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /despesas} : get all the despesas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of despesas in body.
     */
    @GetMapping("/despesas")
    public ResponseEntity<List<Despesa>> getAllDespesas(DespesaCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Despesas by criteria: {}", criteria);
        Page<Despesa> page = despesaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /despesas/count} : count all the despesas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/despesas/count")
    public ResponseEntity<Long> countDespesas(DespesaCriteria criteria) {
        log.debug("REST request to count Despesas by criteria: {}", criteria);
        return ResponseEntity.ok().body(despesaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /despesas/:id} : get the "id" despesa.
     *
     * @param id the id of the despesa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the despesa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/despesas/{id}")
    public ResponseEntity<Despesa> getDespesa(@PathVariable Long id) {
        log.debug("REST request to get Despesa : {}", id);
        Optional<Despesa> despesa = despesaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(despesa);
    }

    /**
     * {@code DELETE  /despesas/:id} : delete the "id" despesa.
     *
     * @param id the id of the despesa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/despesas/{id}")
    public ResponseEntity<Void> deleteDespesa(@PathVariable Long id) {
        log.debug("REST request to delete Despesa : {}", id);
        despesaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
