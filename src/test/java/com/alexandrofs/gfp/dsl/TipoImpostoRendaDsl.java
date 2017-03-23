package com.alexandrofs.gfp.dsl;

import com.alexandrofs.gfp.domain.TipoImpostoRenda;
import com.alexandrofs.gfp.repository.TipoImpostoRendaRepository;

public class TipoImpostoRendaDsl {
	
	private TipoImpostoRenda tipoImpostoRenda;
	
	private TipoImpostoRendaRepository tipoImpostoRendaRepository;
	
	public TipoImpostoRendaDsl(TipoImpostoRendaRepository tipoImpostoRendaRepository) {
		this.tipoImpostoRenda = new TipoImpostoRenda(); 
		this.setCodigo(String.valueOf(Math.random()));
		this.setDescricao("BBBB");
		this.tipoImpostoRendaRepository = tipoImpostoRendaRepository;
	}
	
	public TipoImpostoRendaDsl setCodigo(String codigo) {
		tipoImpostoRenda.setCodigo(codigo);
		return this;
	}

	public TipoImpostoRendaDsl setDescricao(String descricao) {
		tipoImpostoRenda.setDescricao(descricao);
		return this;
	}
	
	public TipoImpostoRenda salva() {
		return tipoImpostoRendaRepository.saveAndFlush(tipoImpostoRenda);
	}

}
