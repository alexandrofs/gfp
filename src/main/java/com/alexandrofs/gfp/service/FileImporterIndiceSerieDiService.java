package com.alexandrofs.gfp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexandrofs.gfp.GfpUtils;
import com.alexandrofs.gfp.domain.IndiceSerieDi;
import com.alexandrofs.gfp.repository.ImportaIndiceSerieDiRepository;

/**
 * Service Implementation for managing IndiceSerieDi.
 */
@Service
@Transactional
public class FileImporterIndiceSerieDiService implements FileImporterService {

    private final Logger log = LoggerFactory.getLogger(FileImporterIndiceSerieDiService.class);
    
    @Autowired
    private ImportaIndiceSerieDiRepository indiceSerieDiRepository;
    
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
    	
    	indiceSerieDiRepository.saveAll(lista);
	}
}
