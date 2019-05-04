package com.alexandrofs.gfp.web.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alexandrofs.gfp.domain.fixed.ModalidadeEnum;

import io.micrometer.core.annotation.Timed;

/**
 * REST controller for managing TipoInvestimento.
 */
@RestController
@RequestMapping("/api")
public class ModalidadeResource {

    private final Logger log = LoggerFactory.getLogger(ModalidadeResource.class);
        
    /**
     * GET  /modalidades : get all the modalidades.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of modalidades in body
     */
    @RequestMapping(value = "/modalidades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Map<String,String> getAllModalidades() {
        log.debug("REST request to get all Modalidades");
        Map<String,String> retorno = new HashMap<>();
        
        for (ModalidadeEnum e : ModalidadeEnum.values()){
        	retorno.put(e.name(), e.getDescricao());
        }
        
        return retorno;
    }

}
