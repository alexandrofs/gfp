package com.alexandrofs.gfp.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_INVESTIMENTO")
public class Investimento {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "NOME", nullable = false)
	private String nome;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_APLICACAO", nullable = false)
	private Date dataAplicacao;

	@NotNull
	@Column(name = "QTDE_COTA", precision = 14, scale = 8)
	private BigDecimal qtdeCota;

	@NotNull
	@Column(name = "VLR_COTA", precision = 14, scale = 8)
	private BigDecimal vlrCota;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="ID_CARTEIRA")
	private Carteira carteira;

	@NotNull
	@ManyToOne
	@JoinColumn(name="ID_TIPO_INVESTIMENTO")
	private TipoInvestimento tipoInvestimento;
	
	@Transient
	private BigDecimal rendimentoLiquido;

	@Transient
	private BigDecimal saldo;

	@Transient
	private BigDecimal rendimentoUltMes;

	@Transient
	private BigDecimal rendimentoMedio;

	@Transient
	private BigDecimal rendimentoTotal;

	@OneToMany(mappedBy = "investimento")
	private List<HistoricoCotas> historicoCotas;

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

	public BigDecimal getRendimentoLiquido() {
		return rendimentoLiquido;
	}

	public void setRendimentoLiquido(BigDecimal rendimentoLiquido) {
		this.rendimentoLiquido = rendimentoLiquido;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getRendimentoUltMes() {
		return rendimentoUltMes;
	}

	public void setRendimentoUltMes(BigDecimal rendimentoUltMes) {
		this.rendimentoUltMes = rendimentoUltMes;
	}

	public BigDecimal getRendimentoMedio() {
		return rendimentoMedio;
	}

	public void setRendimentoMedio(BigDecimal rendimentoMedio) {
		this.rendimentoMedio = rendimentoMedio;
	}

	public BigDecimal getRendimentoTotal() {
		return rendimentoTotal;
	}

	public void setRendimentoTotal(BigDecimal rendimentoTotal) {
		this.rendimentoTotal = rendimentoTotal;
	}
	
	public List<HistoricoCotas> getHistoricoCotas() {
		return historicoCotas;
	}

	public void setHistoricoCotas(List<HistoricoCotas> historicoCotas) {
		this.historicoCotas = historicoCotas;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}

	public TipoInvestimento getTipoInvestimento() {
		return tipoInvestimento;
	}

	public void setTipoInvestimento(TipoInvestimento tipoInvestimento) {
		this.tipoInvestimento = tipoInvestimento;
	}

	
}
