package com.alexandrofs.gfp.web.rest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexandrofs.gfp.domain.IndiceSerieDi;
import com.alexandrofs.gfp.service.IndiceSerieDiService;
import com.alexandrofs.gfp.web.rest.errors.BadRequestAlertException;
import com.alexandrofs.gfp.web.rest.util.HeaderUtil;
import com.alexandrofs.gfp.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing IndiceSerieDi.
 */
@RestController
@RequestMapping("/api")
public class IndiceSerieDiResource {

    private final Logger log = LoggerFactory.getLogger(IndiceSerieDiResource.class);

    private static final String ENTITY_NAME = "indiceSerieDi";

    private final IndiceSerieDiService indiceSerieDiService;

    public IndiceSerieDiResource(IndiceSerieDiService indiceSerieDiService) {
        this.indiceSerieDiService = indiceSerieDiService;
    }

    /**
     * POST  /indice-serie-dis : Create a new indiceSerieDi.
     *
     * @param indiceSerieDi the indiceSerieDi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new indiceSerieDi, or with status 400 (Bad Request) if the indiceSerieDi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/indice-serie-dis")
    public ResponseEntity<IndiceSerieDi> createIndiceSerieDi(@Valid @RequestBody IndiceSerieDi indiceSerieDi) throws URISyntaxException {
        log.debug("REST request to save IndiceSerieDi : {}", indiceSerieDi);
        if (indiceSerieDi.getId() != null) {
            throw new BadRequestAlertException("A new indiceSerieDi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndiceSerieDi result = indiceSerieDiService.save(indiceSerieDi);
        return ResponseEntity.created(new URI("/api/indice-serie-dis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /indice-serie-dis : Updates an existing indiceSerieDi.
     *
     * @param indiceSerieDi the indiceSerieDi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated indiceSerieDi,
     * or with status 400 (Bad Request) if the indiceSerieDi is not valid,
     * or with status 500 (Internal Server Error) if the indiceSerieDi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/indice-serie-dis")
    public ResponseEntity<IndiceSerieDi> updateIndiceSerieDi(@Valid @RequestBody IndiceSerieDi indiceSerieDi) throws URISyntaxException {
        log.debug("REST request to update IndiceSerieDi : {}", indiceSerieDi);
        if (indiceSerieDi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IndiceSerieDi result = indiceSerieDiService.save(indiceSerieDi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, indiceSerieDi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /indice-serie-dis : get all the indiceSerieDis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of indiceSerieDis in body
     */
    @GetMapping("/indice-serie-dis")
    public ResponseEntity<List<IndiceSerieDi>> getAllIndiceSerieDis(Pageable pageable) {
        log.debug("REST request to get a page of IndiceSerieDis");
        Page<IndiceSerieDi> page = indiceSerieDiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/indice-serie-dis");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /indice-serie-dis/:id : get the "id" indiceSerieDi.
     *
     * @param id the id of the indiceSerieDi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the indiceSerieDi, or with status 404 (Not Found)
     */
    @GetMapping("/indice-serie-dis/{id}")
    public ResponseEntity<IndiceSerieDi> getIndiceSerieDi(@PathVariable Long id) {
        log.debug("REST request to get IndiceSerieDi : {}", id);
        Optional<IndiceSerieDi> indiceSerieDi = indiceSerieDiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indiceSerieDi);
    }

    /**
     * DELETE  /indice-serie-dis/:id : delete the "id" indiceSerieDi.
     *
     * @param id the id of the indiceSerieDi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/indice-serie-dis/{id}")
    public ResponseEntity<Void> deleteIndiceSerieDi(@PathVariable Long id) {
        log.debug("REST request to delete IndiceSerieDi : {}", id);
        indiceSerieDiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
