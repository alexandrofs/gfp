package com.alexandrofs.gfp.service;

import org.springframework.stereotype.Service;

import com.alexandrofs.gfp.domain.enumeration.FileImporterType;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class FileImporterServiceFactory {

	private FileImporterIndiceSerieDiService importaIndiceSerieDiService;
	
	public FileImporterService factory(FileImporterType fileImporterType) {
		
		log.info("Factoring file importer service {}", fileImporterType);
		
		if (FileImporterType.DI.equals(fileImporterType)) {
			
			return importaIndiceSerieDiService;
			
		} else {
			
			throw new IllegalArgumentException("");
			
		}
		
	}
}
