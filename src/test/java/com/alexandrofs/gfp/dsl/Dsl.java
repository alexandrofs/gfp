package com.alexandrofs.gfp.dsl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class Dsl {
	
	@Autowired
	private PreparacaoAmbiente preparacaoAmbiente;

	public PreparacaoAmbiente dado() {
		return preparacaoAmbiente;
	}
	
}
