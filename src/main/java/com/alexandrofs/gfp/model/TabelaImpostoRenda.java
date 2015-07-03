package com.alexandrofs.gfp.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TB_TABELA_IMPOSTO_RENDA")
public class TabelaImpostoRenda {
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;
	
	@NotNull
	@Column(name="NUM_DIAS")
	private Long numDias;
	
	@NotNull
	@Column(name="PCT_ALIQUOTA")
	private BigDecimal pctAliquota;
	
	@ManyToOne
	@JoinColumn(name="ID_TIPO_IMPOSTO_RENDA")
	private TipoImpostoRenda tipoImpostoRenda;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumDias() {
		return numDias;
	}

	public void setNumDias(Long numDias) {
		this.numDias = numDias;
	}

	public BigDecimal getPctAliquota() {
		return pctAliquota;
	}

	public void setPctAliquota(BigDecimal pctAliquota) {
		this.pctAliquota = pctAliquota;
	}

	public TipoImpostoRenda getTipoImpostoRenda() {
		return tipoImpostoRenda;
	}

	public void setTipoImpostoRenda(TipoImpostoRenda tipoImpostoRenda) {
		this.tipoImpostoRenda = tipoImpostoRenda;
	}
	
	
}
