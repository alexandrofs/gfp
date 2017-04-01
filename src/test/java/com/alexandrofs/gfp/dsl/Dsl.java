package com.alexandrofs.gfp.dsl;

import javax.inject.Inject;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class Dsl {
	
	@Inject
	private PreparacaoAmbiente preparacaoAmbiente;

	public PreparacaoAmbiente dado() {
		return preparacaoAmbiente;
	}
	
}
