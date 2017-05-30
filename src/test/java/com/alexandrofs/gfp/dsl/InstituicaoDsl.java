package com.alexandrofs.gfp.dsl;

import com.alexandrofs.gfp.domain.Instituicao;
import com.alexandrofs.gfp.repository.InstituicaoRepository;

public class InstituicaoDsl {
	
	private Instituicao instituicao;
	private InstituicaoRepository repository;
	
	public InstituicaoDsl(InstituicaoRepository repository) {
		super();
		this.repository = repository;
		instituicao = new Instituicao();
		instituicao.setNome("AAA");
	}

	public Instituicao salva() {
		return repository.saveAndFlush(instituicao);
	}

	public InstituicaoDsl setNome(String nome) {
		instituicao.setNome(nome);
		return this;
	}


}
