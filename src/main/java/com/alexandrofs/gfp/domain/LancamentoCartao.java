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
@Table(name = "tb_lancamento")
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
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    @NotNull
    @Column(name = "usuario", nullable = false)
    private String usuario;

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
        if (!(o instanceof LancamentoCartao)) {
            return false;
        }
        return id != null && id.equals(((LancamentoCartao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
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
            "}";
    }
}
