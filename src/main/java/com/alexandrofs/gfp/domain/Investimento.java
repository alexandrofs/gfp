package com.alexandrofs.gfp.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Investimento.
 */
@Entity
@Table(name = "tb_investimento")
public class Investimento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_aplicacao", nullable = false)
    private LocalDate dataAplicacao;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "qtde_cota", precision = 10, scale = 2, nullable = false)
    private BigDecimal qtdeCota;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "vlr_cota", precision = 10, scale = 2, nullable = false)
    private BigDecimal vlrCota;

    @Column(name = "pct_pre_pos_fixado", precision = 10, scale = 2)
    private BigDecimal pctPrePosFixado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("investimentos")
    private Carteira carteira;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("investimentos")
    private TipoInvestimento tipoInvestimento;

    @OneToMany(mappedBy = "investimento",cascade=CascadeType.ALL)
    private Set<HistoricoCotas> historicoCotas = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("investimentos")
    private Instituicao instituicao;
    
    @Transient
    @JsonProperty("vlrSaldoBruto")
    private BigDecimal vlrSaldoBruto;
    
    @Transient
    @JsonProperty("vlrSaldoLiquido")
    private BigDecimal vlrSaldoLiquido;
    
    @Transient
    @JsonProperty("vlrRendLiquido")
    private BigDecimal vlrRendLiquido;
    
    @Transient
    @JsonProperty("pctRendTotalLiquido")
    private BigDecimal pctRendTotalLiquido;
    
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getVlrSaldoBruto() {
		return vlrSaldoBruto;
	}

	public void setVlrSaldoBruto(BigDecimal vlrSaldoBruto) {
		this.vlrSaldoBruto = vlrSaldoBruto;
	}
	
	public BigDecimal getVlrSaldoLiquido() {
		return vlrSaldoLiquido;
	}

	public void setVlrSaldoLiquido(BigDecimal vlrSaldoLiquido) {
		this.vlrSaldoLiquido = vlrSaldoLiquido;
	}

	public BigDecimal getVlrRendLiquido() {
		return vlrRendLiquido;
	}

	public void setVlrRendLiquido(BigDecimal vlrRendLiquido) {
		this.vlrRendLiquido = vlrRendLiquido;
	}
	
	public BigDecimal getPctRendTotalLiquido() {
		return pctRendTotalLiquido;
	}

	public void setPctRendTotalLiquido(BigDecimal pctRendTotal) {
		this.pctRendTotalLiquido = pctRendTotal;
	}

	public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDate dataAplicacao) {
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

    public BigDecimal getPctPrePosFixado() {
        return pctPrePosFixado;
    }

    public void setPctPrePosFixado(BigDecimal pctPrePosFixado) {
        this.pctPrePosFixado = pctPrePosFixado;
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

    public Set<HistoricoCotas> getHistoricoCotas() {
        return historicoCotas;
    }

    public void setHistoricoCotas(Set<HistoricoCotas> historicoCotas) {
        this.historicoCotas = historicoCotas;
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Investimento investimento = (Investimento) o;
        if (investimento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), investimento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Investimento{" +
            "id=" + getId() +
            ", dataAplicacao='" + getDataAplicacao() + "'" +
            ", qtdeCota=" + getQtdeCota() +
            ", vlrCota=" + getVlrCota() +
            ", pctPrePosFixado=" + getPctPrePosFixado() +
            ", vlrSaldoBruto='" + vlrSaldoBruto + "'" +
            "}";
    }
}
