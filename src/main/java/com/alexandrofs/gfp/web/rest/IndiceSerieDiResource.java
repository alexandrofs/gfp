package com.alexandrofs.gfp.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alexandrofs.gfp.domain.IndiceSerieDi;
import com.alexandrofs.gfp.service.IndiceSerieDiService;
import com.alexandrofs.gfp.web.rest.util.HeaderUtil;
import com.alexandrofs.gfp.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing IndiceSerieDi.
 */
@RestController
@RequestMapping("/api")
public class IndiceSerieDiResource {

    private final Logger log = LoggerFactory.getLogger(IndiceSerieDiResource.class);
        
    @Inject
    private IndiceSerieDiService indiceSerieDiService;
    
    /**
     * POST  /indice-serie-dis : Create a new indiceSerieDi.
     *
     * @param indiceSerieDi the indiceSerieDi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new indiceSerieDi, or with status 400 (Bad Request) if the indiceSerieDi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/indice-serie-dis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IndiceSerieDi> createIndiceSerieDi(@Valid @RequestBody IndiceSerieDi indiceSerieDi) throws URISyntaxException {
        log.debug("REST request to save IndiceSerieDi : {}", indiceSerieDi);
        if (indiceSerieDi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("indiceSerieDi", "idexists", "A new indiceSerieDi cannot already have an ID")).body(null);
        }
        IndiceSerieDi result = indiceSerieDiService.save(indiceSerieDi);
        return ResponseEntity.created(new URI("/api/indice-serie-dis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("indiceSerieDi", result.getId().toString()))
            .body(result);
    }

    @RequestMapping(value = "/indice-serie-dis/import",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Timed
    public ResponseEntity<Void> importaIndiceSerieDi(@RequestParam("file") MultipartFile file) throws URISyntaxException, IOException {
    	log.debug("REST request to import File : {}", file.getOriginalFilename());

    	indiceSerieDiService.importa(file.getInputStream());
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert("Arquivo importado com sucesso", file.getOriginalFilename()))
            .build();
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
    @RequestMapping(value = "/indice-serie-dis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IndiceSerieDi> updateIndiceSerieDi(@Valid @RequestBody IndiceSerieDi indiceSerieDi) throws URISyntaxException {
        log.debug("REST request to update IndiceSerieDi : {}", indiceSerieDi);
        if (indiceSerieDi.getId() == null) {
            return createIndiceSerieDi(indiceSerieDi);
        }
        IndiceSerieDi result = indiceSerieDiService.save(indiceSerieDi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("indiceSerieDi", indiceSerieDi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /indice-serie-dis : get all the indiceSerieDis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of indiceSerieDis in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/indice-serie-dis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IndiceSerieDi>> getAllIndiceSerieDis(Pageable pageable)
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
    @RequestMapping(value = "/indice-serie-dis/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IndiceSerieDi> getIndiceSerieDi(@PathVariable Long id) {
        log.debug("REST request to get IndiceSerieDi : {}", id);
        IndiceSerieDi indiceSerieDi = indiceSerieDiService.findOne(id);
        return Optional.ofNullable(indiceSerieDi)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /indice-serie-dis/:id : delete the "id" indiceSerieDi.
     *
     * @param id the id of the indiceSerieDi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/indice-serie-dis/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteIndiceSerieDi(@PathVariable Long id) {
        log.debug("REST request to delete IndiceSerieDi : {}", id);
        indiceSerieDiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("indiceSerieDi", id.toString())).build();
    }

}
