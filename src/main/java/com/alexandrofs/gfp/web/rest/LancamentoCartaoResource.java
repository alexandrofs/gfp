package com.alexandrofs.gfp.web.rest;

import com.alexandrofs.gfp.domain.LancamentoCartao;
import com.alexandrofs.gfp.service.LancamentoCartaoService;
import com.alexandrofs.gfp.web.rest.errors.BadRequestAlertException;
import com.alexandrofs.gfp.service.dto.LancamentoCartaoCriteria;
import com.alexandrofs.gfp.service.LancamentoCartaoQueryService;

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
 * REST controller for managing {@link com.alexandrofs.gfp.domain.LancamentoCartao}.
 */
@RestController
@RequestMapping("/api")
public class LancamentoCartaoResource {

    private final Logger log = LoggerFactory.getLogger(LancamentoCartaoResource.class);

    private static final String ENTITY_NAME = "lancamentoCartao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LancamentoCartaoService lancamentoCartaoService;

    private final LancamentoCartaoQueryService lancamentoCartaoQueryService;

    public LancamentoCartaoResource(LancamentoCartaoService lancamentoCartaoService, LancamentoCartaoQueryService lancamentoCartaoQueryService) {
        this.lancamentoCartaoService = lancamentoCartaoService;
        this.lancamentoCartaoQueryService = lancamentoCartaoQueryService;
    }

    /**
     * {@code POST  /lancamento-cartaos} : Create a new lancamentoCartao.
     *
     * @param lancamentoCartao the lancamentoCartao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lancamentoCartao, or with status {@code 400 (Bad Request)} if the lancamentoCartao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lancamento-cartaos")
    public ResponseEntity<LancamentoCartao> createLancamentoCartao(@Valid @RequestBody LancamentoCartao lancamentoCartao) throws URISyntaxException {
        log.debug("REST request to save LancamentoCartao : {}", lancamentoCartao);
        if (lancamentoCartao.getId() != null) {
            throw new BadRequestAlertException("A new lancamentoCartao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LancamentoCartao result = lancamentoCartaoService.save(lancamentoCartao);
        return ResponseEntity.created(new URI("/api/lancamento-cartaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lancamento-cartaos} : Updates an existing lancamentoCartao.
     *
     * @param lancamentoCartao the lancamentoCartao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lancamentoCartao,
     * or with status {@code 400 (Bad Request)} if the lancamentoCartao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lancamentoCartao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lancamento-cartaos")
    public ResponseEntity<LancamentoCartao> updateLancamentoCartao(@Valid @RequestBody LancamentoCartao lancamentoCartao) throws URISyntaxException {
        log.debug("REST request to update LancamentoCartao : {}", lancamentoCartao);
        if (lancamentoCartao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LancamentoCartao result = lancamentoCartaoService.save(lancamentoCartao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lancamentoCartao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lancamento-cartaos} : get all the lancamentoCartaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lancamentoCartaos in body.
     */
    @GetMapping("/lancamento-cartaos")
    public ResponseEntity<List<LancamentoCartao>> getAllLancamentoCartaos(LancamentoCartaoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get LancamentoCartaos by criteria: {}", criteria);
        Page<LancamentoCartao> page = lancamentoCartaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /lancamento-cartaos/count} : count all the lancamentoCartaos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/lancamento-cartaos/count")
    public ResponseEntity<Long> countLancamentoCartaos(LancamentoCartaoCriteria criteria) {
        log.debug("REST request to count LancamentoCartaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(lancamentoCartaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /lancamento-cartaos/:id} : get the "id" lancamentoCartao.
     *
     * @param id the id of the lancamentoCartao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lancamentoCartao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lancamento-cartaos/{id}")
    public ResponseEntity<LancamentoCartao> getLancamentoCartao(@PathVariable Long id) {
        log.debug("REST request to get LancamentoCartao : {}", id);
        Optional<LancamentoCartao> lancamentoCartao = lancamentoCartaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lancamentoCartao);
    }

    /**
     * {@code DELETE  /lancamento-cartaos/:id} : delete the "id" lancamentoCartao.
     *
     * @param id the id of the lancamentoCartao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lancamento-cartaos/{id}")
    public ResponseEntity<Void> deleteLancamentoCartao(@PathVariable Long id) {
        log.debug("REST request to delete LancamentoCartao : {}", id);
        lancamentoCartaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
