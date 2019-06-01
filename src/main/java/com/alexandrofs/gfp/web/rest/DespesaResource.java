package com.alexandrofs.gfp.web.rest;
import com.alexandrofs.gfp.domain.Despesa;
import com.alexandrofs.gfp.service.DespesaService;
import com.alexandrofs.gfp.web.rest.errors.BadRequestAlertException;
import com.alexandrofs.gfp.web.rest.util.HeaderUtil;
import com.alexandrofs.gfp.web.rest.util.PaginationUtil;
import com.alexandrofs.gfp.service.dto.DespesaCriteria;
import com.alexandrofs.gfp.service.DespesaQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Despesa.
 */
@RestController
@RequestMapping("/api")
public class DespesaResource {

    private final Logger log = LoggerFactory.getLogger(DespesaResource.class);

    private static final String ENTITY_NAME = "despesa";

    private final DespesaService despesaService;

    private final DespesaQueryService despesaQueryService;

    public DespesaResource(DespesaService despesaService, DespesaQueryService despesaQueryService) {
        this.despesaService = despesaService;
        this.despesaQueryService = despesaQueryService;
    }

    /**
     * POST  /despesas : Create a new despesa.
     *
     * @param despesa the despesa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new despesa, or with status 400 (Bad Request) if the despesa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/despesas")
    public ResponseEntity<Despesa> createDespesa(@Valid @RequestBody Despesa despesa) throws URISyntaxException {
        log.debug("REST request to save Despesa : {}", despesa);
        if (despesa.getId() != null) {
            throw new BadRequestAlertException("A new despesa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Despesa result = despesaService.save(despesa);
        return ResponseEntity.created(new URI("/api/despesas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /despesas : Updates an existing despesa.
     *
     * @param despesa the despesa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated despesa,
     * or with status 400 (Bad Request) if the despesa is not valid,
     * or with status 500 (Internal Server Error) if the despesa couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/despesas")
    public ResponseEntity<Despesa> updateDespesa(@Valid @RequestBody Despesa despesa) throws URISyntaxException {
        log.debug("REST request to update Despesa : {}", despesa);
        if (despesa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Despesa result = despesaService.save(despesa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, despesa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /despesas : get all the despesas.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of despesas in body
     */
    @GetMapping("/despesas")
    public ResponseEntity<List<Despesa>> getAllDespesas(DespesaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Despesas by criteria: {}", criteria);
        Page<Despesa> page = despesaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/despesas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /despesas/count : count all the despesas.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/despesas/count")
    public ResponseEntity<Long> countDespesas(DespesaCriteria criteria) {
        log.debug("REST request to count Despesas by criteria: {}", criteria);
        return ResponseEntity.ok().body(despesaQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /despesas/:id : get the "id" despesa.
     *
     * @param id the id of the despesa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the despesa, or with status 404 (Not Found)
     */
    @GetMapping("/despesas/{id}")
    public ResponseEntity<Despesa> getDespesa(@PathVariable Long id) {
        log.debug("REST request to get Despesa : {}", id);
        Optional<Despesa> despesa = despesaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(despesa);
    }

    /**
     * DELETE  /despesas/:id : delete the "id" despesa.
     *
     * @param id the id of the despesa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/despesas/{id}")
    public ResponseEntity<Void> deleteDespesa(@PathVariable Long id) {
        log.debug("REST request to delete Despesa : {}", id);
        despesaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
