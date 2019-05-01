package com.alexandrofs.gfp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alexandrofs.gfp.domain.Carteira;
import com.alexandrofs.gfp.repository.CarteiraRepository;
import com.alexandrofs.gfp.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing Carteira.
 */
@RestController
@RequestMapping("/api")
public class CarteiraResource {

    private final Logger log = LoggerFactory.getLogger(CarteiraResource.class);
        
    @Inject
    private CarteiraRepository carteiraRepository;

    /**
     * POST  /carteiras : Create a new carteira.
     *
     * @param carteira the carteira to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carteira, or with status 400 (Bad Request) if the carteira has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/carteiras",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Carteira> createCarteira(@Valid @RequestBody Carteira carteira) throws URISyntaxException {
        log.debug("REST request to save Carteira : {}", carteira);
        if (carteira.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("carteira", "idexists", "A new carteira cannot already have an ID")).body(null);
        }
        Carteira result = carteiraRepository.save(carteira);
        return ResponseEntity.created(new URI("/api/carteiras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("carteira", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carteiras : Updates an existing carteira.
     *
     * @param carteira the carteira to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carteira,
     * or with status 400 (Bad Request) if the carteira is not valid,
     * or with status 500 (Internal Server Error) if the carteira couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/carteiras",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Carteira> updateCarteira(@Valid @RequestBody Carteira carteira) throws URISyntaxException {
        log.debug("REST request to update Carteira : {}", carteira);
        if (carteira.getId() == null) {
            return createCarteira(carteira);
        }
        Carteira result = carteiraRepository.save(carteira);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("carteira", carteira.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carteiras : get all the carteiras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of carteiras in body
     */
    @RequestMapping(value = "/carteiras",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Carteira> getAllCarteiras() {
        log.debug("REST request to get all Carteiras");
        List<Carteira> carteiras = carteiraRepository.findAll();
        return carteiras;
    }

    /**
     * GET  /carteiras/:id : get the "id" carteira.
     *
     * @param id the id of the carteira to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carteira, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/carteiras/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Carteira> getCarteira(@PathVariable Long id) {
        log.debug("REST request to get Carteira : {}", id);
        Carteira carteira = carteiraRepository.findOne(id);
        return Optional.ofNullable(carteira)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /carteiras/:id : delete the "id" carteira.
     *
     * @param id the id of the carteira to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/carteiras/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCarteira(@PathVariable Long id) {
        log.debug("REST request to delete Carteira : {}", id);
        carteiraRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("carteira", id.toString())).build();
    }

}
