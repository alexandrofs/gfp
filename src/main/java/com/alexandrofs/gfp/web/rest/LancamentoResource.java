package com.alexandrofs.gfp.web.rest;
import com.alexandrofs.gfp.domain.Lancamento;
import com.alexandrofs.gfp.service.LancamentoService;
import com.alexandrofs.gfp.web.rest.errors.BadRequestAlertException;
import com.alexandrofs.gfp.web.rest.util.HeaderUtil;
import com.alexandrofs.gfp.web.rest.util.PaginationUtil;
import com.alexandrofs.gfp.service.dto.LancamentoCriteria;
import com.alexandrofs.gfp.service.LancamentoQueryService;
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
 * REST controller for managing Lancamento.
 */
@RestController
@RequestMapping("/api")
public class LancamentoResource {

    private final Logger log = LoggerFactory.getLogger(LancamentoResource.class);

    private static final String ENTITY_NAME = "lancamento";

    private final LancamentoService lancamentoService;

    private final LancamentoQueryService lancamentoQueryService;

    public LancamentoResource(LancamentoService lancamentoService, LancamentoQueryService lancamentoQueryService) {
        this.lancamentoService = lancamentoService;
        this.lancamentoQueryService = lancamentoQueryService;
    }

    /**
     * POST  /lancamentos : Create a new lancamento.
     *
     * @param lancamento the lancamento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lancamento, or with status 400 (Bad Request) if the lancamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lancamentos")
    public ResponseEntity<Lancamento> createLancamento(@Valid @RequestBody Lancamento lancamento) throws URISyntaxException {
        log.debug("REST request to save Lancamento : {}", lancamento);
        if (lancamento.getId() != null) {
            throw new BadRequestAlertException("A new lancamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lancamento result = lancamentoService.save(lancamento);
        return ResponseEntity.created(new URI("/api/lancamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lancamentos : Updates an existing lancamento.
     *
     * @param lancamento the lancamento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lancamento,
     * or with status 400 (Bad Request) if the lancamento is not valid,
     * or with status 500 (Internal Server Error) if the lancamento couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lancamentos")
    public ResponseEntity<Lancamento> updateLancamento(@Valid @RequestBody Lancamento lancamento) throws URISyntaxException {
        log.debug("REST request to update Lancamento : {}", lancamento);
        if (lancamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Lancamento result = lancamentoService.save(lancamento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lancamento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lancamentos : get all the lancamentos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of lancamentos in body
     */
    @GetMapping("/lancamentos")
    public ResponseEntity<List<Lancamento>> getAllLancamentos(LancamentoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Lancamentos by criteria: {}", criteria);
        Page<Lancamento> page = lancamentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lancamentos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /lancamentos/count : count all the lancamentos.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/lancamentos/count")
    public ResponseEntity<Long> countLancamentos(LancamentoCriteria criteria) {
        log.debug("REST request to count Lancamentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(lancamentoQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /lancamentos/:id : get the "id" lancamento.
     *
     * @param id the id of the lancamento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lancamento, or with status 404 (Not Found)
     */
    @GetMapping("/lancamentos/{id}")
    public ResponseEntity<Lancamento> getLancamento(@PathVariable Long id) {
        log.debug("REST request to get Lancamento : {}", id);
        Optional<Lancamento> lancamento = lancamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lancamento);
    }

    /**
     * DELETE  /lancamentos/:id : delete the "id" lancamento.
     *
     * @param id the id of the lancamento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lancamentos/{id}")
    public ResponseEntity<Void> deleteLancamento(@PathVariable Long id) {
        log.debug("REST request to delete Lancamento : {}", id);
        lancamentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
