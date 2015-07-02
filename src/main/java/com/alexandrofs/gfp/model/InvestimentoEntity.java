package com.alexandrofs.gfp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_INVESTIMENTO")
public class InvestimentoEntity {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "NOME", nullable = false)
	private String nome;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_APLICACAO", nullable = false)
	private Date dataAplicacao;

	@Column(name = "QTDE_COTA", nullable = false, precision = 14, scale = 8)
	private BigDecimal qtdeCota;

	@Column(name = "VLR_COTA", nullable = false, precision = 14, scale = 8)
	private BigDecimal vlrCota;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataAplicacao() {
		return dataAplicacao;
	}

	public void setDataAplicacao(Date dataAplicacao) {
		this.dataAplicacao = dataAplicacao;
	}

	public BigDecimal getQtdeCota() {
		return qtdeCota;
	}

	public void setQtdeCota(BigDecimal qtdeCota) {
		this.qtdeCota = qtdeCota;
	}

	public BigDecimal getVlrCota() {
		return vlrCota;
	}

	public void setVlrCota(BigDecimal vlrCota) {
		this.vlrCota = vlrCota;
	}

}
