package com.alexandrofs.gfp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import io.github.jhipster.web.util.ResponseUtil;
import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alexandrofs.gfp.domain.Investimento;
import com.alexandrofs.gfp.service.InvestimentoService;
import com.alexandrofs.gfp.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing Investimento.
 */
@RestController
@RequestMapping("/api")
public class InvestimentoResource {

    private final Logger log = LoggerFactory.getLogger(InvestimentoResource.class);

    private static final String ENTITY_NAME = "investimento";
        
    private final InvestimentoService investimentoService;

    public InvestimentoResource(InvestimentoService investimentoService) {
        this.investimentoService = investimentoService;
    }

    /**
     * POST  /investimentos : Create a new investimento.
     *
     * @param investimento the investimento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new investimento, or with status 400 (Bad Request) if the investimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/investimentos")
    @Timed
    public ResponseEntity<Investimento> createInvestimento(@Valid @RequestBody Investimento investimento) throws URISyntaxException {
        log.debug("REST request to save Investimento : {}", investimento);
        if (investimento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new investimento cannot already have an ID")).body(null);
        }
        Investimento result = investimentoService.save(investimento);
        return ResponseEntity.created(new URI("/api/investimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /investimentos : Updates an existing investimento.
     *
     * @param investimento the investimento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated investimento,
     * or with status 400 (Bad Request) if the investimento is not valid,
     * or with status 500 (Internal Server Error) if the investimento couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/investimentos")
    @Timed
    public ResponseEntity<Investimento> updateInvestimento(@Valid @RequestBody Investimento investimento) throws URISyntaxException {
        log.debug("REST request to update Investimento : {}", investimento);
        if (investimento.getId() == null) {
            return createInvestimento(investimento);
        }
        Investimento result = investimentoService.save(investimento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, investimento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /investimentos : get all the investimentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of investimentos in body
     */
    @GetMapping("/investimentos")
    @Timed
    public List<Investimento> getAllInvestimentos() {
        log.debug("REST request to get all Investimentos");
		return investimentoService.findAll();
    }

    /**
     * GET  /investimentos/:id : get the "id" investimento.
     *
     * @param id the id of the investimento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the investimento, or with status 404 (Not Found)
     */
    @GetMapping("/investimentos/{id}")
    @Timed
    public ResponseEntity<Investimento> getInvestimento(@PathVariable Long id) {
        log.debug("REST request to get Investimento : {}", id);
        Investimento investimento = investimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(investimento));
    }

    /**
     * DELETE  /investimentos/:id : delete the "id" investimento.
     *
     * @param id the id of the investimento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/investimentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvestimento(@PathVariable Long id) {
        log.debug("REST request to delete Investimento : {}", id);
        investimentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
