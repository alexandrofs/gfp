package com.alexandrofs.gfp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.alexandrofs.gfp.domain.TipoInvestimento;

import com.alexandrofs.gfp.repository.TipoInvestimentoRepository;
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
 * REST controller for managing TipoInvestimento.
 */
@RestController
@RequestMapping("/api")
public class TipoInvestimentoResource {

    private final Logger log = LoggerFactory.getLogger(TipoInvestimentoResource.class);

    private static final String ENTITY_NAME = "tipoInvestimento";
        
    private final TipoInvestimentoRepository tipoInvestimentoRepository;

    public TipoInvestimentoResource(TipoInvestimentoRepository tipoInvestimentoRepository) {
        this.tipoInvestimentoRepository = tipoInvestimentoRepository;
    }

    /**
     * POST  /tipo-investimentos : Create a new tipoInvestimento.
     *
     * @param tipoInvestimento the tipoInvestimento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoInvestimento, or with status 400 (Bad Request) if the tipoInvestimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-investimentos")
    @Timed
    public ResponseEntity<TipoInvestimento> createTipoInvestimento(@Valid @RequestBody TipoInvestimento tipoInvestimento) throws URISyntaxException {
        log.debug("REST request to save TipoInvestimento : {}", tipoInvestimento);
        if (tipoInvestimento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tipoInvestimento cannot already have an ID")).body(null);
        }
        TipoInvestimento result = tipoInvestimentoRepository.save(tipoInvestimento);
        return ResponseEntity.created(new URI("/api/tipo-investimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-investimentos : Updates an existing tipoInvestimento.
     *
     * @param tipoInvestimento the tipoInvestimento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoInvestimento,
     * or with status 400 (Bad Request) if the tipoInvestimento is not valid,
     * or with status 500 (Internal Server Error) if the tipoInvestimento couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-investimentos")
    @Timed
    public ResponseEntity<TipoInvestimento> updateTipoInvestimento(@Valid @RequestBody TipoInvestimento tipoInvestimento) throws URISyntaxException {
        log.debug("REST request to update TipoInvestimento : {}", tipoInvestimento);
        if (tipoInvestimento.getId() == null) {
            return createTipoInvestimento(tipoInvestimento);
        }
        TipoInvestimento result = tipoInvestimentoRepository.save(tipoInvestimento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoInvestimento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-investimentos : get all the tipoInvestimentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoInvestimentos in body
     */
    @GetMapping("/tipo-investimentos")
    @Timed
    public List<TipoInvestimento> getAllTipoInvestimentos() {
        log.debug("REST request to get all TipoInvestimentos");
        List<TipoInvestimento> tipoInvestimentos = tipoInvestimentoRepository.findAll();
        return tipoInvestimentos;
    }

    /**
     * GET  /tipo-investimentos/:id : get the "id" tipoInvestimento.
     *
     * @param id the id of the tipoInvestimento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoInvestimento, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-investimentos/{id}")
    @Timed
    public ResponseEntity<TipoInvestimento> getTipoInvestimento(@PathVariable Long id) {
        log.debug("REST request to get TipoInvestimento : {}", id);
        TipoInvestimento tipoInvestimento = tipoInvestimentoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoInvestimento));
    }

    /**
     * DELETE  /tipo-investimentos/:id : delete the "id" tipoInvestimento.
     *
     * @param id the id of the tipoInvestimento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-investimentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoInvestimento(@PathVariable Long id) {
        log.debug("REST request to delete TipoInvestimento : {}", id);
        tipoInvestimentoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
