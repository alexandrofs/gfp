package com.alexandrofs.gfp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.alexandrofs.gfp.domain.Carteira;

import com.alexandrofs.gfp.repository.CarteiraRepository;
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
 * REST controller for managing Carteira.
 */
@RestController
@RequestMapping("/api")
public class CarteiraResource {

    private final Logger log = LoggerFactory.getLogger(CarteiraResource.class);

    private static final String ENTITY_NAME = "carteira";
        
    private final CarteiraRepository carteiraRepository;

    public CarteiraResource(CarteiraRepository carteiraRepository) {
        this.carteiraRepository = carteiraRepository;
    }

    /**
     * POST  /carteiras : Create a new carteira.
     *
     * @param carteira the carteira to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carteira, or with status 400 (Bad Request) if the carteira has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carteiras")
    @Timed
    public ResponseEntity<Carteira> createCarteira(@Valid @RequestBody Carteira carteira) throws URISyntaxException {
        log.debug("REST request to save Carteira : {}", carteira);
        if (carteira.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new carteira cannot already have an ID")).body(null);
        }
        Carteira result = carteiraRepository.save(carteira);
        return ResponseEntity.created(new URI("/api/carteiras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
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
    @PutMapping("/carteiras")
    @Timed
    public ResponseEntity<Carteira> updateCarteira(@Valid @RequestBody Carteira carteira) throws URISyntaxException {
        log.debug("REST request to update Carteira : {}", carteira);
        if (carteira.getId() == null) {
            return createCarteira(carteira);
        }
        Carteira result = carteiraRepository.save(carteira);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carteira.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carteiras : get all the carteiras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of carteiras in body
     */
    @GetMapping("/carteiras")
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
    @GetMapping("/carteiras/{id}")
    @Timed
    public ResponseEntity<Carteira> getCarteira(@PathVariable Long id) {
        log.debug("REST request to get Carteira : {}", id);
        Carteira carteira = carteiraRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carteira));
    }

    /**
     * DELETE  /carteiras/:id : delete the "id" carteira.
     *
     * @param id the id of the carteira to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carteiras/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarteira(@PathVariable Long id) {
        log.debug("REST request to delete Carteira : {}", id);
        carteiraRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
