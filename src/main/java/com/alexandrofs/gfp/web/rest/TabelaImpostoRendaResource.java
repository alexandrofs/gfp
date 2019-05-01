package com.alexandrofs.gfp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.alexandrofs.gfp.domain.TabelaImpostoRenda;
import com.alexandrofs.gfp.repository.TabelaImpostoRendaRepository;
import com.alexandrofs.gfp.web.rest.errors.BadRequestAlertException;
import com.alexandrofs.gfp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TabelaImpostoRenda.
 */
@RestController
@RequestMapping("/api")
public class TabelaImpostoRendaResource {

    private final Logger log = LoggerFactory.getLogger(TabelaImpostoRendaResource.class);

    private static final String ENTITY_NAME = "tabelaImpostoRenda";

    private final TabelaImpostoRendaRepository tabelaImpostoRendaRepository;

    public TabelaImpostoRendaResource(TabelaImpostoRendaRepository tabelaImpostoRendaRepository) {
        this.tabelaImpostoRendaRepository = tabelaImpostoRendaRepository;
    }

    /**
     * POST  /tabela-imposto-rendas : Create a new tabelaImpostoRenda.
     *
     * @param tabelaImpostoRenda the tabelaImpostoRenda to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tabelaImpostoRenda, or with status 400 (Bad Request) if the tabelaImpostoRenda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tabela-imposto-rendas")
    @Timed
    public ResponseEntity<TabelaImpostoRenda> createTabelaImpostoRenda(@Valid @RequestBody TabelaImpostoRenda tabelaImpostoRenda) throws URISyntaxException {
        log.debug("REST request to save TabelaImpostoRenda : {}", tabelaImpostoRenda);
        if (tabelaImpostoRenda.getId() != null) {
            throw new BadRequestAlertException("A new tabelaImpostoRenda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TabelaImpostoRenda result = tabelaImpostoRendaRepository.save(tabelaImpostoRenda);
        return ResponseEntity.created(new URI("/api/tabela-imposto-rendas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tabela-imposto-rendas : Updates an existing tabelaImpostoRenda.
     *
     * @param tabelaImpostoRenda the tabelaImpostoRenda to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tabelaImpostoRenda,
     * or with status 400 (Bad Request) if the tabelaImpostoRenda is not valid,
     * or with status 500 (Internal Server Error) if the tabelaImpostoRenda couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tabela-imposto-rendas")
    @Timed
    public ResponseEntity<TabelaImpostoRenda> updateTabelaImpostoRenda(@Valid @RequestBody TabelaImpostoRenda tabelaImpostoRenda) throws URISyntaxException {
        log.debug("REST request to update TabelaImpostoRenda : {}", tabelaImpostoRenda);
        if (tabelaImpostoRenda.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TabelaImpostoRenda result = tabelaImpostoRendaRepository.save(tabelaImpostoRenda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tabelaImpostoRenda.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tabela-imposto-rendas : get all the tabelaImpostoRendas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tabelaImpostoRendas in body
     */
    @GetMapping("/tabela-imposto-rendas")
    @Timed
    public List<TabelaImpostoRenda> getAllTabelaImpostoRendas() {
        log.debug("REST request to get all TabelaImpostoRendas");
        return tabelaImpostoRendaRepository.findAll();
    }

    /**
     * GET  /tabela-imposto-rendas/:id : get the "id" tabelaImpostoRenda.
     *
     * @param id the id of the tabelaImpostoRenda to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tabelaImpostoRenda, or with status 404 (Not Found)
     */
    @GetMapping("/tabela-imposto-rendas/{id}")
    @Timed
    public ResponseEntity<TabelaImpostoRenda> getTabelaImpostoRenda(@PathVariable Long id) {
        log.debug("REST request to get TabelaImpostoRenda : {}", id);
        Optional<TabelaImpostoRenda> tabelaImpostoRenda = tabelaImpostoRendaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tabelaImpostoRenda);
    }

    /**
     * DELETE  /tabela-imposto-rendas/:id : delete the "id" tabelaImpostoRenda.
     *
     * @param id the id of the tabelaImpostoRenda to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tabela-imposto-rendas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTabelaImpostoRenda(@PathVariable Long id) {
        log.debug("REST request to delete TabelaImpostoRenda : {}", id);

        tabelaImpostoRendaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
