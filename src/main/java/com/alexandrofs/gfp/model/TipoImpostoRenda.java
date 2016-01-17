package com.alexandrofs.gfp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TB_TIPO_IMPOSTO_RENDA")
public class TipoImpostoRenda {
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;
	
	@NotNull
	@Column(name="CODIGO")
	private String codigo;
	
	@NotNull
	@Column(name="DESCRICAO")
	private String descricao;
	
	@OneToMany(mappedBy = "tipoImpostoRenda", fetch=FetchType.EAGER)
	private List<TabelaImpostoRenda> tabelaImposto;

	public TipoImpostoRenda(){
		tabelaImposto = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<TabelaImpostoRenda> getTabelaImposto() {
		return tabelaImposto;
	}

	public void setTabelaImposto(List<TabelaImpostoRenda> tabelaImposto) {
		this.tabelaImposto = tabelaImposto;
	}

	
}
