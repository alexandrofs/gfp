package com.alexandrofs.gfp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.alexandrofs.gfp.domain.HistoricoCotas;
import com.alexandrofs.gfp.repository.HistoricoCotasRepository;
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
 * REST controller for managing HistoricoCotas.
 */
@RestController
@RequestMapping("/api")
public class HistoricoCotasResource {

    private final Logger log = LoggerFactory.getLogger(HistoricoCotasResource.class);
        
    @Inject
    private HistoricoCotasRepository historicoCotasRepository;
    
    /**
     * POST  /historico-cotas : Create a new historicoCotas.
     *
     * @param historicoCotas the historicoCotas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new historicoCotas, or with status 400 (Bad Request) if the historicoCotas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/historico-cotas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HistoricoCotas> createHistoricoCotas(@Valid @RequestBody HistoricoCotas historicoCotas) throws URISyntaxException {
        log.debug("REST request to save HistoricoCotas : {}", historicoCotas);
        if (historicoCotas.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("historicoCotas", "idexists", "A new historicoCotas cannot already have an ID")).body(null);
        }
        HistoricoCotas result = historicoCotasRepository.save(historicoCotas);
        return ResponseEntity.created(new URI("/api/historico-cotas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("historicoCotas", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /historico-cotas : Updates an existing historicoCotas.
     *
     * @param historicoCotas the historicoCotas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated historicoCotas,
     * or with status 400 (Bad Request) if the historicoCotas is not valid,
     * or with status 500 (Internal Server Error) if the historicoCotas couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/historico-cotas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HistoricoCotas> updateHistoricoCotas(@Valid @RequestBody HistoricoCotas historicoCotas) throws URISyntaxException {
        log.debug("REST request to update HistoricoCotas : {}", historicoCotas);
        if (historicoCotas.getId() == null) {
            return createHistoricoCotas(historicoCotas);
        }
        HistoricoCotas result = historicoCotasRepository.save(historicoCotas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("historicoCotas", historicoCotas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /historico-cotas : get all the historicoCotas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of historicoCotas in body
     */
    @RequestMapping(value = "/historico-cotas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HistoricoCotas> getAllHistoricoCotas() {
        log.debug("REST request to get all HistoricoCotas");
        List<HistoricoCotas> historicoCotas = historicoCotasRepository.findAll();
        return historicoCotas;
    }

    /**
     * GET  /historico-cotas/:id : get the "id" historicoCotas.
     *
     * @param id the id of the historicoCotas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the historicoCotas, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/historico-cotas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HistoricoCotas> getHistoricoCotas(@PathVariable Long id) {
        log.debug("REST request to get HistoricoCotas : {}", id);
        HistoricoCotas historicoCotas = historicoCotasRepository.findOne(id);
        return Optional.ofNullable(historicoCotas)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /historico-cotas/:id : delete the "id" historicoCotas.
     *
     * @param id the id of the historicoCotas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/historico-cotas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHistoricoCotas(@PathVariable Long id) {
        log.debug("REST request to delete HistoricoCotas : {}", id);
        historicoCotasRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("historicoCotas", id.toString())).build();
    }

}
