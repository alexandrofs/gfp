package com.alexandrofs.gfp.web.rest;
import com.alexandrofs.gfp.domain.TipoImpostoRenda;
import com.alexandrofs.gfp.repository.TipoImpostoRendaRepository;
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
 * REST controller for managing TipoImpostoRenda.
 */
@RestController
@RequestMapping("/api")
public class TipoImpostoRendaResource {

    private final Logger log = LoggerFactory.getLogger(TipoImpostoRendaResource.class);

    private static final String ENTITY_NAME = "tipoImpostoRenda";

    private final TipoImpostoRendaRepository tipoImpostoRendaRepository;

    public TipoImpostoRendaResource(TipoImpostoRendaRepository tipoImpostoRendaRepository) {
        this.tipoImpostoRendaRepository = tipoImpostoRendaRepository;
    }

    /**
     * POST  /tipo-imposto-rendas : Create a new tipoImpostoRenda.
     *
     * @param tipoImpostoRenda the tipoImpostoRenda to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoImpostoRenda, or with status 400 (Bad Request) if the tipoImpostoRenda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-imposto-rendas")
    public ResponseEntity<TipoImpostoRenda> createTipoImpostoRenda(@Valid @RequestBody TipoImpostoRenda tipoImpostoRenda) throws URISyntaxException {
        log.debug("REST request to save TipoImpostoRenda : {}", tipoImpostoRenda);
        if (tipoImpostoRenda.getId() != null) {
            throw new BadRequestAlertException("A new tipoImpostoRenda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoImpostoRenda result = tipoImpostoRendaRepository.save(tipoImpostoRenda);
        return ResponseEntity.created(new URI("/api/tipo-imposto-rendas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-imposto-rendas : Updates an existing tipoImpostoRenda.
     *
     * @param tipoImpostoRenda the tipoImpostoRenda to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoImpostoRenda,
     * or with status 400 (Bad Request) if the tipoImpostoRenda is not valid,
     * or with status 500 (Internal Server Error) if the tipoImpostoRenda couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-imposto-rendas")
    public ResponseEntity<TipoImpostoRenda> updateTipoImpostoRenda(@Valid @RequestBody TipoImpostoRenda tipoImpostoRenda) throws URISyntaxException {
        log.debug("REST request to update TipoImpostoRenda : {}", tipoImpostoRenda);
        if (tipoImpostoRenda.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoImpostoRenda result = tipoImpostoRendaRepository.save(tipoImpostoRenda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoImpostoRenda.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-imposto-rendas : get all the tipoImpostoRendas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoImpostoRendas in body
     */
    @GetMapping("/tipo-imposto-rendas")
    public List<TipoImpostoRenda> getAllTipoImpostoRendas() {
        log.debug("REST request to get all TipoImpostoRendas");
        return tipoImpostoRendaRepository.findAll();
    }

    /**
     * GET  /tipo-imposto-rendas/:id : get the "id" tipoImpostoRenda.
     *
     * @param id the id of the tipoImpostoRenda to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoImpostoRenda, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-imposto-rendas/{id}")
    public ResponseEntity<TipoImpostoRenda> getTipoImpostoRenda(@PathVariable Long id) {
        log.debug("REST request to get TipoImpostoRenda : {}", id);
        Optional<TipoImpostoRenda> tipoImpostoRenda = tipoImpostoRendaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoImpostoRenda);
    }

    /**
     * DELETE  /tipo-imposto-rendas/:id : delete the "id" tipoImpostoRenda.
     *
     * @param id the id of the tipoImpostoRenda to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-imposto-rendas/{id}")
    public ResponseEntity<Void> deleteTipoImpostoRenda(@PathVariable Long id) {
        log.debug("REST request to delete TipoImpostoRenda : {}", id);
        tipoImpostoRendaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
