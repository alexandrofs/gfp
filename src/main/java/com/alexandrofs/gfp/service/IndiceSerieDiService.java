package com.alexandrofs.gfp.service;

import com.alexandrofs.gfp.GfpUtils;
import com.alexandrofs.gfp.domain.IndiceSerieDi;
import com.alexandrofs.gfp.repository.IndiceSerieDiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing IndiceSerieDi.
 */
@Service
@Transactional
public class IndiceSerieDiService {

    private final Logger log = LoggerFactory.getLogger(IndiceSerieDiService.class);
    
    @Inject
    private IndiceSerieDiRepository indiceSerieDiRepository;
    
    /**
     * Save a indiceSerieDi.
     * 
     * @param indiceSerieDi the entity to save
     * @return the persisted entity
     */
    public IndiceSerieDi save(IndiceSerieDi indiceSerieDi) {
        log.debug("Request to save IndiceSerieDi : {}", indiceSerieDi);
        IndiceSerieDi result = indiceSerieDiRepository.save(indiceSerieDi);
        return result;
    }

    /**
     *  Get all the indiceSerieDis.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<IndiceSerieDi> findAll(Pageable pageable) {
        log.debug("Request to get all IndiceSerieDis");
        Page<IndiceSerieDi> result = indiceSerieDiRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one indiceSerieDi by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public IndiceSerieDi findOne(Long id) {
        log.debug("Request to get IndiceSerieDi : {}", id);
        IndiceSerieDi indiceSerieDi = indiceSerieDiRepository.findOne(id);
        return indiceSerieDi;
    }

    /**
     *  Delete the  indiceSerieDi by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IndiceSerieDi : {}", id);
        indiceSerieDiRepository.delete(id);
    }

	public void importa(InputStream inputStream) throws IOException {
		log.debug("Request to import file");
		
		List<IndiceSerieDi> lista = new ArrayList<>();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    	
    	String line = null;
    	
    	Boolean achouCabecalho = Boolean.FALSE;
    	
    	while((line = reader.readLine()) != null) {
    		
    	    if (line.contains("Data\t")) {
    	    	
    	    	achouCabecalho = Boolean.TRUE;
    	    	
    	    } else if (achouCabecalho) {
    	    	
    	    	log.debug("Salvando linha: {}", line);
    	    	
    	    	String[] colunas = line.split("\t");
    	    	
    	    	LocalDate data = LocalDate.parse(colunas[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    	    	
    	    	IndiceSerieDi serie = indiceSerieDiRepository.findByData(data);
    	    	
    	    	if (serie == null) {
    	    		serie = new IndiceSerieDi();
    	    		serie.setData(data);
    	    	}
				
    	    	serie.setTaxaMediaAnual(GfpUtils.converteStringToBigDecimal(colunas[3]));
    	    	serie.setFatorDiario(GfpUtils.converteStringToBigDecimal(colunas[4]));
    	    	serie.setTaxaSelic(GfpUtils.converteStringToBigDecimal(colunas[9]));
    	    	
    	    	lista.add(serie);
    	    }
    	}
    	
    	if (!achouCabecalho) {
    		throw new RuntimeException("Arquivo com formato inválido");
    	}
    	
    	indiceSerieDiRepository.save(lista);
	}
}
