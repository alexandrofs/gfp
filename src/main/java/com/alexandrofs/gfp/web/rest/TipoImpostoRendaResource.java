package com.alexandrofs.gfp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.alexandrofs.gfp.domain.TipoImpostoRenda;
import com.alexandrofs.gfp.repository.TipoImpostoRendaRepository;
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
 * REST controller for managing TipoImpostoRenda.
 */
@RestController
@RequestMapping("/api")
public class TipoImpostoRendaResource {

    private final Logger log = LoggerFactory.getLogger(TipoImpostoRendaResource.class);
        
    @Inject
    private TipoImpostoRendaRepository tipoImpostoRendaRepository;
    
    /**
     * POST  /tipo-imposto-rendas : Create a new tipoImpostoRenda.
     *
     * @param tipoImpostoRenda the tipoImpostoRenda to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoImpostoRenda, or with status 400 (Bad Request) if the tipoImpostoRenda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tipo-imposto-rendas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TipoImpostoRenda> createTipoImpostoRenda(@Valid @RequestBody TipoImpostoRenda tipoImpostoRenda) throws URISyntaxException {
        log.debug("REST request to save TipoImpostoRenda : {}", tipoImpostoRenda);
        if (tipoImpostoRenda.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tipoImpostoRenda", "idexists", "A new tipoImpostoRenda cannot already have an ID")).body(null);
        }
        TipoImpostoRenda result = tipoImpostoRendaRepository.save(tipoImpostoRenda);
        return ResponseEntity.created(new URI("/api/tipo-imposto-rendas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tipoImpostoRenda", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-imposto-rendas : Updates an existing tipoImpostoRenda.
     *
     * @param tipoImpostoRenda the tipoImpostoRenda to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoImpostoRenda,
     * or with status 400 (Bad Request) if the tipoImpostoRenda is not valid,
     * or with status 500 (Internal Server Error) if the tipoImpostoRenda couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tipo-imposto-rendas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TipoImpostoRenda> updateTipoImpostoRenda(@Valid @RequestBody TipoImpostoRenda tipoImpostoRenda) throws URISyntaxException {
        log.debug("REST request to update TipoImpostoRenda : {}", tipoImpostoRenda);
        if (tipoImpostoRenda.getId() == null) {
            return createTipoImpostoRenda(tipoImpostoRenda);
        }
        TipoImpostoRenda result = tipoImpostoRendaRepository.save(tipoImpostoRenda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tipoImpostoRenda", tipoImpostoRenda.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-imposto-rendas : get all the tipoImpostoRendas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoImpostoRendas in body
     */
    @RequestMapping(value = "/tipo-imposto-rendas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TipoImpostoRenda> getAllTipoImpostoRendas() {
        log.debug("REST request to get all TipoImpostoRendas");
        List<TipoImpostoRenda> tipoImpostoRendas = tipoImpostoRendaRepository.findAll();
        return tipoImpostoRendas;
    }

    /**
     * GET  /tipo-imposto-rendas/:id : get the "id" tipoImpostoRenda.
     *
     * @param id the id of the tipoImpostoRenda to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoImpostoRenda, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tipo-imposto-rendas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TipoImpostoRenda> getTipoImpostoRenda(@PathVariable Long id) {
        log.debug("REST request to get TipoImpostoRenda : {}", id);
        TipoImpostoRenda tipoImpostoRenda = tipoImpostoRendaRepository.findOne(id);
        return Optional.ofNullable(tipoImpostoRenda)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tipo-imposto-rendas/:id : delete the "id" tipoImpostoRenda.
     *
     * @param id the id of the tipoImpostoRenda to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tipo-imposto-rendas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTipoImpostoRenda(@PathVariable Long id) {
        log.debug("REST request to delete TipoImpostoRenda : {}", id);
        tipoImpostoRendaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tipoImpostoRenda", id.toString())).build();
    }

}
