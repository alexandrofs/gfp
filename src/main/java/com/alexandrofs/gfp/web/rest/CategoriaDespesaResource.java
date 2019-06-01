package com.alexandrofs.gfp.web.rest;
import com.alexandrofs.gfp.domain.CategoriaDespesa;
import com.alexandrofs.gfp.repository.CategoriaDespesaRepository;
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
 * REST controller for managing CategoriaDespesa.
 */
@RestController
@RequestMapping("/api")
public class CategoriaDespesaResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaDespesaResource.class);

    private static final String ENTITY_NAME = "categoriaDespesa";

    private final CategoriaDespesaRepository categoriaDespesaRepository;

    public CategoriaDespesaResource(CategoriaDespesaRepository categoriaDespesaRepository) {
        this.categoriaDespesaRepository = categoriaDespesaRepository;
    }

    /**
     * POST  /categoria-despesas : Create a new categoriaDespesa.
     *
     * @param categoriaDespesa the categoriaDespesa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categoriaDespesa, or with status 400 (Bad Request) if the categoriaDespesa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/categoria-despesas")
    public ResponseEntity<CategoriaDespesa> createCategoriaDespesa(@Valid @RequestBody CategoriaDespesa categoriaDespesa) throws URISyntaxException {
        log.debug("REST request to save CategoriaDespesa : {}", categoriaDespesa);
        if (categoriaDespesa.getId() != null) {
            throw new BadRequestAlertException("A new categoriaDespesa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaDespesa result = categoriaDespesaRepository.save(categoriaDespesa);
        return ResponseEntity.created(new URI("/api/categoria-despesas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /categoria-despesas : Updates an existing categoriaDespesa.
     *
     * @param categoriaDespesa the categoriaDespesa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categoriaDespesa,
     * or with status 400 (Bad Request) if the categoriaDespesa is not valid,
     * or with status 500 (Internal Server Error) if the categoriaDespesa couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/categoria-despesas")
    public ResponseEntity<CategoriaDespesa> updateCategoriaDespesa(@Valid @RequestBody CategoriaDespesa categoriaDespesa) throws URISyntaxException {
        log.debug("REST request to update CategoriaDespesa : {}", categoriaDespesa);
        if (categoriaDespesa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoriaDespesa result = categoriaDespesaRepository.save(categoriaDespesa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categoriaDespesa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /categoria-despesas : get all the categoriaDespesas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categoriaDespesas in body
     */
    @GetMapping("/categoria-despesas")
    public List<CategoriaDespesa> getAllCategoriaDespesas() {
        log.debug("REST request to get all CategoriaDespesas");
        return categoriaDespesaRepository.findAll();
    }

    /**
     * GET  /categoria-despesas/:id : get the "id" categoriaDespesa.
     *
     * @param id the id of the categoriaDespesa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categoriaDespesa, or with status 404 (Not Found)
     */
    @GetMapping("/categoria-despesas/{id}")
    public ResponseEntity<CategoriaDespesa> getCategoriaDespesa(@PathVariable Long id) {
        log.debug("REST request to get CategoriaDespesa : {}", id);
        Optional<CategoriaDespesa> categoriaDespesa = categoriaDespesaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(categoriaDespesa);
    }

    /**
     * DELETE  /categoria-despesas/:id : delete the "id" categoriaDespesa.
     *
     * @param id the id of the categoriaDespesa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/categoria-despesas/{id}")
    public ResponseEntity<Void> deleteCategoriaDespesa(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaDespesa : {}", id);
        categoriaDespesaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
