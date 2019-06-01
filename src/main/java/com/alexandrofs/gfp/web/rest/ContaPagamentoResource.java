package com.alexandrofs.gfp.web.rest;

import com.alexandrofs.gfp.domain.ContaPagamento;
import com.alexandrofs.gfp.repository.ContaPagamentoRepository;
import com.alexandrofs.gfp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.alexandrofs.gfp.domain.ContaPagamento}.
 */
@RestController
@RequestMapping("/api")
public class ContaPagamentoResource {

    private final Logger log = LoggerFactory.getLogger(ContaPagamentoResource.class);

    private static final String ENTITY_NAME = "contaPagamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContaPagamentoRepository contaPagamentoRepository;

    public ContaPagamentoResource(ContaPagamentoRepository contaPagamentoRepository) {
        this.contaPagamentoRepository = contaPagamentoRepository;
    }

    /**
     * {@code POST  /conta-pagamentos} : Create a new contaPagamento.
     *
     * @param contaPagamento the contaPagamento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contaPagamento, or with status {@code 400 (Bad Request)} if the contaPagamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conta-pagamentos")
    public ResponseEntity<ContaPagamento> createContaPagamento(@Valid @RequestBody ContaPagamento contaPagamento) throws URISyntaxException {
        log.debug("REST request to save ContaPagamento : {}", contaPagamento);
        if (contaPagamento.getId() != null) {
            throw new BadRequestAlertException("A new contaPagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContaPagamento result = contaPagamentoRepository.save(contaPagamento);
        return ResponseEntity.created(new URI("/api/conta-pagamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conta-pagamentos} : Updates an existing contaPagamento.
     *
     * @param contaPagamento the contaPagamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contaPagamento,
     * or with status {@code 400 (Bad Request)} if the contaPagamento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contaPagamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conta-pagamentos")
    public ResponseEntity<ContaPagamento> updateContaPagamento(@Valid @RequestBody ContaPagamento contaPagamento) throws URISyntaxException {
        log.debug("REST request to update ContaPagamento : {}", contaPagamento);
        if (contaPagamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContaPagamento result = contaPagamentoRepository.save(contaPagamento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contaPagamento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /conta-pagamentos} : get all the contaPagamentos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contaPagamentos in body.
     */
    @GetMapping("/conta-pagamentos")
    public List<ContaPagamento> getAllContaPagamentos() {
        log.debug("REST request to get all ContaPagamentos");
        return contaPagamentoRepository.findAll();
    }

    /**
     * {@code GET  /conta-pagamentos/:id} : get the "id" contaPagamento.
     *
     * @param id the id of the contaPagamento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contaPagamento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conta-pagamentos/{id}")
    public ResponseEntity<ContaPagamento> getContaPagamento(@PathVariable Long id) {
        log.debug("REST request to get ContaPagamento : {}", id);
        Optional<ContaPagamento> contaPagamento = contaPagamentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contaPagamento);
    }

    /**
     * {@code DELETE  /conta-pagamentos/:id} : delete the "id" contaPagamento.
     *
     * @param id the id of the contaPagamento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conta-pagamentos/{id}")
    public ResponseEntity<Void> deleteContaPagamento(@PathVariable Long id) {
        log.debug("REST request to delete ContaPagamento : {}", id);
        contaPagamentoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
