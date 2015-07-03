package com.alexandrofs.gfp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TB_HISTORICO_COTAS")
public class HistoricoCotasEntity {

	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name="DT_COTA")
	private Date dataCota;
	
	@NotNull
	@Column(name="VLR_COTA", precision = 14, scale = 8)
	private BigDecimal vlrCota;

	@NotNull
	@ManyToOne
	@JoinColumn(name="ID_INVESTIMENTO")
	private InvestimentoEntity investimento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCota() {
		return dataCota;
	}

	public void setDataCota(Date dataCota) {
		this.dataCota = dataCota;
	}

	public BigDecimal getVlrCota() {
		return vlrCota;
	}

	public void setVlrCota(BigDecimal vlrCota) {
		this.vlrCota = vlrCota;
	}

	public InvestimentoEntity getInvestimento() {
		return investimento;
	}

	public void setInvestimento(InvestimentoEntity investimento) {
		this.investimento = investimento;
	}

}