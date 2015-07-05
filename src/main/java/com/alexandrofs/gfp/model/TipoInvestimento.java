package com.alexandrofs.gfp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_TIPO_INVESTIMENTO")
public class TipoInvestimento {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@NotNull
	@Column(name = "NOME")
	private String nome;

	@NotNull
	@Column(name = "DESCRICAO")
	private String descricao;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_IMPOSTO_RENDA")
	private TipoImpostoRenda tipoImpostoRenda;

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public TipoImpostoRenda getTipoImpostoRenda() {
		return tipoImpostoRenda;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setTipoImpostoRenda(TipoImpostoRenda tipoImpostoRenda) {
		this.tipoImpostoRenda = tipoImpostoRenda;
	}
	
}
