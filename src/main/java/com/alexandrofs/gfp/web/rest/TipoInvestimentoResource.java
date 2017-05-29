package com.alexandrofs.gfp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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

import com.alexandrofs.gfp.domain.TipoInvestimento;
import com.alexandrofs.gfp.domain.fixed.ModalidadeEnum;
import com.alexandrofs.gfp.domain.fixed.TipoIndexadorEnum;
import com.alexandrofs.gfp.repository.TipoInvestimentoRepository;
import com.alexandrofs.gfp.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing TipoInvestimento.
 */
@RestController
@RequestMapping("/api")
public class TipoInvestimentoResource {

    private final Logger log = LoggerFactory.getLogger(TipoInvestimentoResource.class);
        
    @Inject
    private TipoInvestimentoRepository tipoInvestimentoRepository;
    
    /**
     * POST  /tipo-investimentos : Create a new tipoInvestimento.
     *
     * @param tipoInvestimento the tipoInvestimento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoInvestimento, or with status 400 (Bad Request) if the tipoInvestimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tipo-investimentos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TipoInvestimento> createTipoInvestimento(@Valid @RequestBody TipoInvestimento tipoInvestimento) throws URISyntaxException {
        log.debug("REST request to save TipoInvestimento : {}", tipoInvestimento);
        if (tipoInvestimento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tipoInvestimento", "idexists", "A new tipoInvestimento cannot already have an ID")).body(null);
        }
        if (ModalidadeEnum.CDB.equals(tipoInvestimento.getModalidade()) || ModalidadeEnum.LCI.equals(tipoInvestimento.getModalidade())) {
        	if (tipoInvestimento.getTipoIndexador() == null) {
        		return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("O campo Tipo Indexador é obrigatório quando a modalidade for igual a CDB ou LCI.", "")).body(null);
        	}
        	if (TipoIndexadorEnum.POS.equals(tipoInvestimento.getTipoIndexador()) && tipoInvestimento.getIndice() == null) {
        		return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("O campo Indice é obrigatório quando Tipo Indexador for igual a POS.", "")).body(null);	
        	}
        }
        TipoInvestimento result = tipoInvestimentoRepository.save(tipoInvestimento);
        return ResponseEntity.created(new URI("/api/tipo-investimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tipoInvestimento", result.getId().toString()))
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
    @RequestMapping(value = "/tipo-investimentos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TipoInvestimento> updateTipoInvestimento(@Valid @RequestBody TipoInvestimento tipoInvestimento) throws URISyntaxException {
        log.debug("REST request to update TipoInvestimento : {}", tipoInvestimento);
        if (tipoInvestimento.getId() == null) {
            return createTipoInvestimento(tipoInvestimento);
        }
        TipoInvestimento result = tipoInvestimentoRepository.save(tipoInvestimento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tipoInvestimento", tipoInvestimento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-investimentos : get all the tipoInvestimentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoInvestimentos in body
     */
    @RequestMapping(value = "/tipo-investimentos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
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
    @RequestMapping(value = "/tipo-investimentos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TipoInvestimento> getTipoInvestimento(@PathVariable Long id) {
        log.debug("REST request to get TipoInvestimento : {}", id);
        TipoInvestimento tipoInvestimento = tipoInvestimentoRepository.findOne(id);
        return Optional.ofNullable(tipoInvestimento)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tipo-investimentos/:id : delete the "id" tipoInvestimento.
     *
     * @param id the id of the tipoInvestimento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tipo-investimentos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTipoInvestimento(@PathVariable Long id) {
        log.debug("REST request to delete TipoInvestimento : {}", id);
        tipoInvestimentoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tipoInvestimento", id.toString())).build();
    }

}
