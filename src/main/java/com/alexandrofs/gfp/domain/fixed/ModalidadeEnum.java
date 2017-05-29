package com.alexandrofs.gfp.domain.fixed;

public enum ModalidadeEnum {
	
	CDB("CDB"),
	LCI("LCI"),
	TESOURO("Tesouro Direto");
	
	private String descricao;
	
	private ModalidadeEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
