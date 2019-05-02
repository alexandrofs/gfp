package com.alexandrofs.gfp.web.rest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexandrofs.gfp.domain.Investimento;
import com.alexandrofs.gfp.service.InvestimentoService;
import com.alexandrofs.gfp.web.rest.errors.BadRequestAlertException;
import com.alexandrofs.gfp.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

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
    public ResponseEntity<Investimento> createInvestimento(@Valid @RequestBody Investimento investimento) throws URISyntaxException {
        log.debug("REST request to save Investimento : {}", investimento);
        if (investimento.getId() != null) {
            throw new BadRequestAlertException("A new investimento cannot already have an ID", ENTITY_NAME, "idexists");
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
     * or with status 500 (Internal Server Error) if the investimento couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/investimentos")
    public ResponseEntity<Investimento> updateInvestimento(@Valid @RequestBody Investimento investimento) throws URISyntaxException {
        log.debug("REST request to update Investimento : {}", investimento);
        if (investimento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
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
    public ResponseEntity<Investimento> getInvestimento(@PathVariable Long id) {
        log.debug("REST request to get Investimento : {}", id);
        Optional<Investimento> investimento = investimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(investimento);
    }

    /**
     * DELETE  /investimentos/:id : delete the "id" investimento.
     *
     * @param id the id of the investimento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/investimentos/{id}")
    public ResponseEntity<Void> deleteInvestimento(@PathVariable Long id) {
        log.debug("REST request to delete Investimento : {}", id);
        investimentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
