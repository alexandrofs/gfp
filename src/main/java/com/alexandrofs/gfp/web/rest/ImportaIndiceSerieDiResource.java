package com.alexandrofs.gfp.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alexandrofs.gfp.service.ImportaIndiceSerieDiService;
import com.alexandrofs.gfp.web.rest.util.HeaderUtil;

import io.micrometer.core.annotation.Timed;

/**
 * REST controller for managing IndiceSerieDi.
 */
@RestController
@RequestMapping("/api")
public class ImportaIndiceSerieDiResource {

    private final Logger log = LoggerFactory.getLogger(ImportaIndiceSerieDiResource.class);
        
    @Autowired
    private ImportaIndiceSerieDiService indiceSerieDiService;
    
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
    
}
