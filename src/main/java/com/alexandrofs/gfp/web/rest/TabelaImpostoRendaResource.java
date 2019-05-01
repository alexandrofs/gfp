package com.alexandrofs.gfp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.alexandrofs.gfp.domain.TabelaImpostoRenda;
import com.alexandrofs.gfp.repository.TabelaImpostoRendaRepository;
import com.alexandrofs.gfp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
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
        
    @Inject
    private TabelaImpostoRendaRepository tabelaImpostoRendaRepository;
    
    /**
     * POST  /tabela-imposto-rendas : Create a new tabelaImpostoRenda.
     *
     * @param tabelaImpostoRenda the tabelaImpostoRenda to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tabelaImpostoRenda, or with status 400 (Bad Request) if the tabelaImpostoRenda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tabela-imposto-rendas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TabelaImpostoRenda> createTabelaImpostoRenda(@Valid @RequestBody TabelaImpostoRenda tabelaImpostoRenda) throws URISyntaxException {
        log.debug("REST request to save TabelaImpostoRenda : {}", tabelaImpostoRenda);
        if (tabelaImpostoRenda.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tabelaImpostoRenda", "idexists", "A new tabelaImpostoRenda cannot already have an ID")).body(null);
        }
        TabelaImpostoRenda result = tabelaImpostoRendaRepository.save(tabelaImpostoRenda);
        return ResponseEntity.created(new URI("/api/tabela-imposto-rendas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tabelaImpostoRenda", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tabela-imposto-rendas : Updates an existing tabelaImpostoRenda.
     *
     * @param tabelaImpostoRenda the tabelaImpostoRenda to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tabelaImpostoRenda,
     * or with status 400 (Bad Request) if the tabelaImpostoRenda is not valid,
     * or with status 500 (Internal Server Error) if the tabelaImpostoRenda couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tabela-imposto-rendas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TabelaImpostoRenda> updateTabelaImpostoRenda(@Valid @RequestBody TabelaImpostoRenda tabelaImpostoRenda) throws URISyntaxException {
        log.debug("REST request to update TabelaImpostoRenda : {}", tabelaImpostoRenda);
        if (tabelaImpostoRenda.getId() == null) {
            return createTabelaImpostoRenda(tabelaImpostoRenda);
        }
        TabelaImpostoRenda result = tabelaImpostoRendaRepository.save(tabelaImpostoRenda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tabelaImpostoRenda", tabelaImpostoRenda.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tabela-imposto-rendas : get all the tabelaImpostoRendas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tabelaImpostoRendas in body
     */
    @RequestMapping(value = "/tabela-imposto-rendas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TabelaImpostoRenda> getAllTabelaImpostoRendas() {
        log.debug("REST request to get all TabelaImpostoRendas");
        List<TabelaImpostoRenda> tabelaImpostoRendas = tabelaImpostoRendaRepository.findAll();
        return tabelaImpostoRendas;
    }

    /**
     * GET  /tabela-imposto-rendas/:id : get the "id" tabelaImpostoRenda.
     *
     * @param id the id of the tabelaImpostoRenda to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tabelaImpostoRenda, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tabela-imposto-rendas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TabelaImpostoRenda> getTabelaImpostoRenda(@PathVariable Long id) {
        log.debug("REST request to get TabelaImpostoRenda : {}", id);
        TabelaImpostoRenda tabelaImpostoRenda = tabelaImpostoRendaRepository.findOne(id);
        return Optional.ofNullable(tabelaImpostoRenda)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tabela-imposto-rendas/:id : delete the "id" tabelaImpostoRenda.
     *
     * @param id the id of the tabelaImpostoRenda to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tabela-imposto-rendas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTabelaImpostoRenda(@PathVariable Long id) {
        log.debug("REST request to delete TabelaImpostoRenda : {}", id);
        tabelaImpostoRendaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tabelaImpostoRenda", id.toString())).build();
    }

}
