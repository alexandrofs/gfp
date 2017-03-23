package com.alexandrofs.gfp.dsl;

import com.alexandrofs.gfp.domain.Carteira;
import com.alexandrofs.gfp.repository.CarteiraRepository;

public class CarteiraDsl {
	
	private Carteira carteira;
	private CarteiraRepository repository;
	
	public CarteiraDsl(CarteiraRepository repository) {
		super();
		this.repository = repository;
		carteira = new Carteira();
		carteira.setNome("AAA");
		carteira.setDescricao("DDDDD");
	}

	public Carteira salva() {
		return repository.saveAndFlush(carteira);
	}

	public CarteiraDsl setNome(String nome) {
		carteira.setNome(nome);
		return this;
	}

	public CarteiraDsl setDescricao(String descricao) {
		carteira.setDescricao(descricao);
		return this;
	}
	
	

}
