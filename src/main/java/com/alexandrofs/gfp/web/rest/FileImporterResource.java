package com.alexandrofs.gfp.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alexandrofs.gfp.domain.enumeration.FileImporterType;
import com.alexandrofs.gfp.service.FileImporterService;
import com.alexandrofs.gfp.service.FileImporterServiceFactory;
import com.alexandrofs.gfp.web.rest.util.HeaderUtil;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing IndiceSerieDi.
 */
@RestController
@RequestMapping("/api")
@Slf4j
@AllArgsConstructor
public class FileImporterResource {

    private FileImporterServiceFactory factory;
    
    @RequestMapping(value = "/importer/{fileImporterType}",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Timed
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("fileImporterType") FileImporterType fileImporterType) throws URISyntaxException, IOException {

    	log.info("REST request to import File : {}", file.getOriginalFilename());
    	
    	FileImporterService fileImporterService = factory.factory(fileImporterType);

    	fileImporterService.importa(file.getInputStream());
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert("Arquivo importado com sucesso", file.getOriginalFilename()))
            .build();
    }
    
}
