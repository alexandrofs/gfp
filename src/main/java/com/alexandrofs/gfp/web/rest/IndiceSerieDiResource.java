package com.alexandrofs.gfp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.alexandrofs.gfp.domain.IndiceSerieDi;
import com.alexandrofs.gfp.service.IndiceSerieDiService;
import com.alexandrofs.gfp.web.rest.util.HeaderUtil;
import com.alexandrofs.gfp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
    @Timed
    public ResponseEntity<IndiceSerieDi> createIndiceSerieDi(@Valid @RequestBody IndiceSerieDi indiceSerieDi) throws URISyntaxException {
        log.debug("REST request to save IndiceSerieDi : {}", indiceSerieDi);
        if (indiceSerieDi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new indiceSerieDi cannot already have an ID")).body(null);
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
     * or with status 500 (Internal Server Error) if the indiceSerieDi couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/indice-serie-dis")
    @Timed
    public ResponseEntity<IndiceSerieDi> updateIndiceSerieDi(@Valid @RequestBody IndiceSerieDi indiceSerieDi) throws URISyntaxException {
        log.debug("REST request to update IndiceSerieDi : {}", indiceSerieDi);
        if (indiceSerieDi.getId() == null) {
            return createIndiceSerieDi(indiceSerieDi);
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
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/indice-serie-dis")
    @Timed
    public ResponseEntity<List<IndiceSerieDi>> getAllIndiceSerieDis(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of IndiceSerieDis");
        Page<IndiceSerieDi> page = indiceSerieDiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/indice-serie-dis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /indice-serie-dis/:id : get the "id" indiceSerieDi.
     *
     * @param id the id of the indiceSerieDi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the indiceSerieDi, or with status 404 (Not Found)
     */
    @GetMapping("/indice-serie-dis/{id}")
    @Timed
    public ResponseEntity<IndiceSerieDi> getIndiceSerieDi(@PathVariable Long id) {
        log.debug("REST request to get IndiceSerieDi : {}", id);
        IndiceSerieDi indiceSerieDi = indiceSerieDiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(indiceSerieDi));
    }

    /**
     * DELETE  /indice-serie-dis/:id : delete the "id" indiceSerieDi.
     *
     * @param id the id of the indiceSerieDi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/indice-serie-dis/{id}")
    @Timed
    public ResponseEntity<Void> deleteIndiceSerieDi(@PathVariable Long id) {
        log.debug("REST request to delete IndiceSerieDi : {}", id);
        indiceSerieDiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
