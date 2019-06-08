package com.alexandrofs.gfp.service;

import java.io.IOException;
import java.io.InputStream;

public interface FileImporterService {

	public void importa(InputStream inputStream) throws IOException;
	
}
