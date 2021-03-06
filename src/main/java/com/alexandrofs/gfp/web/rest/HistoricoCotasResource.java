package com.alexandrofs.gfp.web.rest;
import com.alexandrofs.gfp.domain.HistoricoCotas;
import com.alexandrofs.gfp.repository.HistoricoCotasRepository;
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
 * REST controller for managing HistoricoCotas.
 */
@RestController
@RequestMapping("/api")
public class HistoricoCotasResource {

    private final Logger log = LoggerFactory.getLogger(HistoricoCotasResource.class);

    private static final String ENTITY_NAME = "historicoCotas";

    private final HistoricoCotasRepository historicoCotasRepository;

    public HistoricoCotasResource(HistoricoCotasRepository historicoCotasRepository) {
        this.historicoCotasRepository = historicoCotasRepository;
    }

    /**
     * POST  /historico-cotas : Create a new historicoCotas.
     *
     * @param historicoCotas the historicoCotas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new historicoCotas, or with status 400 (Bad Request) if the historicoCotas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/historico-cotas")
    public ResponseEntity<HistoricoCotas> createHistoricoCotas(@Valid @RequestBody HistoricoCotas historicoCotas) throws URISyntaxException {
        log.debug("REST request to save HistoricoCotas : {}", historicoCotas);
        if (historicoCotas.getId() != null) {
            throw new BadRequestAlertException("A new historicoCotas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoricoCotas result = historicoCotasRepository.save(historicoCotas);
        return ResponseEntity.created(new URI("/api/historico-cotas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /historico-cotas : Updates an existing historicoCotas.
     *
     * @param historicoCotas the historicoCotas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated historicoCotas,
     * or with status 400 (Bad Request) if the historicoCotas is not valid,
     * or with status 500 (Internal Server Error) if the historicoCotas couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/historico-cotas")
    public ResponseEntity<HistoricoCotas> updateHistoricoCotas(@Valid @RequestBody HistoricoCotas historicoCotas) throws URISyntaxException {
        log.debug("REST request to update HistoricoCotas : {}", historicoCotas);
        if (historicoCotas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HistoricoCotas result = historicoCotasRepository.save(historicoCotas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, historicoCotas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /historico-cotas : get all the historicoCotas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of historicoCotas in body
     */
    @GetMapping("/historico-cotas")
    public List<HistoricoCotas> getAllHistoricoCotas() {
        log.debug("REST request to get all HistoricoCotas");
        return historicoCotasRepository.findAll();
    }

    /**
     * GET  /historico-cotas/:id : get the "id" historicoCotas.
     *
     * @param id the id of the historicoCotas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the historicoCotas, or with status 404 (Not Found)
     */
    @GetMapping("/historico-cotas/{id}")
    public ResponseEntity<HistoricoCotas> getHistoricoCotas(@PathVariable Long id) {
        log.debug("REST request to get HistoricoCotas : {}", id);
        Optional<HistoricoCotas> historicoCotas = historicoCotasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(historicoCotas);
    }

    /**
     * DELETE  /historico-cotas/:id : delete the "id" historicoCotas.
     *
     * @param id the id of the historicoCotas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/historico-cotas/{id}")
    public ResponseEntity<Void> deleteHistoricoCotas(@PathVariable Long id) {
        log.debug("REST request to delete HistoricoCotas : {}", id);
        historicoCotasRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
