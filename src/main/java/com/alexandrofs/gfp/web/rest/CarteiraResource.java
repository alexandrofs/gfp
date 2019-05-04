package com.alexandrofs.gfp.web.rest;
import com.alexandrofs.gfp.domain.Carteira;
import com.alexandrofs.gfp.repository.CarteiraRepository;
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
    public ResponseEntity<Carteira> createCarteira(@Valid @RequestBody Carteira carteira) throws URISyntaxException {
        log.debug("REST request to save Carteira : {}", carteira);
        if (carteira.getId() != null) {
            throw new BadRequestAlertException("A new carteira cannot already have an ID", ENTITY_NAME, "idexists");
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
     * or with status 500 (Internal Server Error) if the carteira couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carteiras")
    public ResponseEntity<Carteira> updateCarteira(@Valid @RequestBody Carteira carteira) throws URISyntaxException {
        log.debug("REST request to update Carteira : {}", carteira);
        if (carteira.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
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
    public List<Carteira> getAllCarteiras() {
        log.debug("REST request to get all Carteiras");
        return carteiraRepository.findAll();
    }

    /**
     * GET  /carteiras/:id : get the "id" carteira.
     *
     * @param id the id of the carteira to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carteira, or with status 404 (Not Found)
     */
    @GetMapping("/carteiras/{id}")
    public ResponseEntity<Carteira> getCarteira(@PathVariable Long id) {
        log.debug("REST request to get Carteira : {}", id);
        Optional<Carteira> carteira = carteiraRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(carteira);
    }

    /**
     * DELETE  /carteiras/:id : delete the "id" carteira.
     *
     * @param id the id of the carteira to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carteiras/{id}")
    public ResponseEntity<Void> deleteCarteira(@PathVariable Long id) {
        log.debug("REST request to delete Carteira : {}", id);
        carteiraRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
