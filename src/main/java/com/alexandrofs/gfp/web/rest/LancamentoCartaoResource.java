package com.alexandrofs.gfp.web.rest;
import com.alexandrofs.gfp.domain.LancamentoCartao;
import com.alexandrofs.gfp.service.LancamentoCartaoService;
import com.alexandrofs.gfp.web.rest.errors.BadRequestAlertException;
import com.alexandrofs.gfp.web.rest.util.HeaderUtil;
import com.alexandrofs.gfp.web.rest.util.PaginationUtil;
import com.alexandrofs.gfp.service.dto.LancamentoCartaoCriteria;
import com.alexandrofs.gfp.service.LancamentoCartaoQueryService;
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
 * REST controller for managing LancamentoCartao.
 */
@RestController
@RequestMapping("/api")
public class LancamentoCartaoResource {

    private final Logger log = LoggerFactory.getLogger(LancamentoCartaoResource.class);

    private static final String ENTITY_NAME = "lancamentoCartao";

    private final LancamentoCartaoService lancamentoCartaoService;

    private final LancamentoCartaoQueryService lancamentoCartaoQueryService;

    public LancamentoCartaoResource(LancamentoCartaoService lancamentoCartaoService, LancamentoCartaoQueryService lancamentoCartaoQueryService) {
        this.lancamentoCartaoService = lancamentoCartaoService;
        this.lancamentoCartaoQueryService = lancamentoCartaoQueryService;
    }

    /**
     * POST  /lancamento-cartaos : Create a new lancamentoCartao.
     *
     * @param lancamentoCartao the lancamentoCartao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lancamentoCartao, or with status 400 (Bad Request) if the lancamentoCartao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lancamento-cartaos")
    public ResponseEntity<LancamentoCartao> createLancamentoCartao(@Valid @RequestBody LancamentoCartao lancamentoCartao) throws URISyntaxException {
        log.debug("REST request to save LancamentoCartao : {}", lancamentoCartao);
        if (lancamentoCartao.getId() != null) {
            throw new BadRequestAlertException("A new lancamentoCartao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LancamentoCartao result = lancamentoCartaoService.save(lancamentoCartao);
        return ResponseEntity.created(new URI("/api/lancamento-cartaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lancamento-cartaos : Updates an existing lancamentoCartao.
     *
     * @param lancamentoCartao the lancamentoCartao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lancamentoCartao,
     * or with status 400 (Bad Request) if the lancamentoCartao is not valid,
     * or with status 500 (Internal Server Error) if the lancamentoCartao couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lancamento-cartaos")
    public ResponseEntity<LancamentoCartao> updateLancamentoCartao(@Valid @RequestBody LancamentoCartao lancamentoCartao) throws URISyntaxException {
        log.debug("REST request to update LancamentoCartao : {}", lancamentoCartao);
        if (lancamentoCartao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LancamentoCartao result = lancamentoCartaoService.save(lancamentoCartao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lancamentoCartao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lancamento-cartaos : get all the lancamentoCartaos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of lancamentoCartaos in body
     */
    @GetMapping("/lancamento-cartaos")
    public ResponseEntity<List<LancamentoCartao>> getAllLancamentoCartaos(LancamentoCartaoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LancamentoCartaos by criteria: {}", criteria);
        Page<LancamentoCartao> page = lancamentoCartaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lancamento-cartaos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /lancamento-cartaos/count : count all the lancamentoCartaos.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/lancamento-cartaos/count")
    public ResponseEntity<Long> countLancamentoCartaos(LancamentoCartaoCriteria criteria) {
        log.debug("REST request to count LancamentoCartaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(lancamentoCartaoQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /lancamento-cartaos/:id : get the "id" lancamentoCartao.
     *
     * @param id the id of the lancamentoCartao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lancamentoCartao, or with status 404 (Not Found)
     */
    @GetMapping("/lancamento-cartaos/{id}")
    public ResponseEntity<LancamentoCartao> getLancamentoCartao(@PathVariable Long id) {
        log.debug("REST request to get LancamentoCartao : {}", id);
        Optional<LancamentoCartao> lancamentoCartao = lancamentoCartaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lancamentoCartao);
    }

    /**
     * DELETE  /lancamento-cartaos/:id : delete the "id" lancamentoCartao.
     *
     * @param id the id of the lancamentoCartao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lancamento-cartaos/{id}")
    public ResponseEntity<Void> deleteLancamentoCartao(@PathVariable Long id) {
        log.debug("REST request to delete LancamentoCartao : {}", id);
        lancamentoCartaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
