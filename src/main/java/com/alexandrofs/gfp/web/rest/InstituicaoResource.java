package com.alexandrofs.gfp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.alexandrofs.gfp.domain.Instituicao;

import com.alexandrofs.gfp.repository.InstituicaoRepository;
import com.alexandrofs.gfp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Instituicao.
 */
@RestController
@RequestMapping("/api")
public class InstituicaoResource {

    private final Logger log = LoggerFactory.getLogger(InstituicaoResource.class);
        
    @Inject
    private InstituicaoRepository instituicaoRepository;

    /**
     * POST  /instituicaos : Create a new instituicao.
     *
     * @param instituicao the instituicao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new instituicao, or with status 400 (Bad Request) if the instituicao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/instituicaos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Instituicao> createInstituicao(@RequestBody Instituicao instituicao) throws URISyntaxException {
        log.debug("REST request to save Instituicao : {}", instituicao);
        if (instituicao.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("instituicao", "idexists", "A new instituicao cannot already have an ID")).body(null);
        }
        Instituicao result = instituicaoRepository.save(instituicao);
        return ResponseEntity.created(new URI("/api/instituicaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instituicao", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instituicaos : Updates an existing instituicao.
     *
     * @param instituicao the instituicao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated instituicao,
     * or with status 400 (Bad Request) if the instituicao is not valid,
     * or with status 500 (Internal Server Error) if the instituicao couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/instituicaos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Instituicao> updateInstituicao(@RequestBody Instituicao instituicao) throws URISyntaxException {
        log.debug("REST request to update Instituicao : {}", instituicao);
        if (instituicao.getId() == null) {
            return createInstituicao(instituicao);
        }
        Instituicao result = instituicaoRepository.save(instituicao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instituicao", instituicao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instituicaos : get all the instituicaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of instituicaos in body
     */
    @RequestMapping(value = "/instituicaos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Instituicao> getAllInstituicaos() {
        log.debug("REST request to get all Instituicaos");
        List<Instituicao> instituicaos = instituicaoRepository.findAll();
        return instituicaos;
    }

    /**
     * GET  /instituicaos/:id : get the "id" instituicao.
     *
     * @param id the id of the instituicao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the instituicao, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/instituicaos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Instituicao> getInstituicao(@PathVariable Long id) {
        log.debug("REST request to get Instituicao : {}", id);
        Instituicao instituicao = instituicaoRepository.findOne(id);
        return Optional.ofNullable(instituicao)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instituicaos/:id : delete the "id" instituicao.
     *
     * @param id the id of the instituicao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/instituicaos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstituicao(@PathVariable Long id) {
        log.debug("REST request to delete Instituicao : {}", id);
        instituicaoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instituicao", id.toString())).build();
    }

}
