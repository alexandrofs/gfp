package com.alexandrofs.gfp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A LancamentoCartao.
 */
@Entity
@Table(name = "tb_lancamento_cartao")
public class LancamentoCartao implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_compra", nullable = false)
    private LocalDate dataCompra;

    @NotNull
    @Column(name = "mes_fatura", nullable = false)
    private LocalDate mesFatura;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "valor", precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(name = "usuario", nullable = false)
    private String usuario;

    @Min(value = 1)
    @Column(name = "quantidade_parcelas")
    private Integer quantidadeParcelas;

    @Min(value = 1)
    @Column(name = "parcela")
    private Integer parcela;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lancamentoCartaos")
    private ContaPagamento contaPagamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public LancamentoCartao dataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
        return this;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public LocalDate getMesFatura() {
        return mesFatura;
    }

    public LancamentoCartao mesFatura(LocalDate mesFatura) {
        this.mesFatura = mesFatura;
        return this;
    }

    public void setMesFatura(LocalDate mesFatura) {
        this.mesFatura = mesFatura;
    }

    public String getDescricao() {
        return descricao;
    }

    public LancamentoCartao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LancamentoCartao valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getUsuario() {
        return usuario;
    }

    public LancamentoCartao usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public LancamentoCartao quantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
        return this;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public Integer getParcela() {
        return parcela;
    }

    public LancamentoCartao parcela(Integer parcela) {
        this.parcela = parcela;
        return this;
    }

    public void setParcela(Integer parcela) {
        this.parcela = parcela;
    }

    public ContaPagamento getContaPagamento() {
        return contaPagamento;
    }

    public LancamentoCartao contaPagamento(ContaPagamento contaPagamento) {
        this.contaPagamento = contaPagamento;
        return this;
    }

    public void setContaPagamento(ContaPagamento contaPagamento) {
        this.contaPagamento = contaPagamento;
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
        LancamentoCartao lancamentoCartao = (LancamentoCartao) o;
        if (lancamentoCartao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lancamentoCartao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LancamentoCartao{" +
            "id=" + getId() +
            ", dataCompra='" + getDataCompra() + "'" +
            ", mesFatura='" + getMesFatura() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", valor=" + getValor() +
            ", usuario='" + getUsuario() + "'" +
            ", quantidadeParcelas=" + getQuantidadeParcelas() +
            ", parcela=" + getParcela() +
            "}";
    }
}
