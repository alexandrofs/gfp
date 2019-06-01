package com.alexandrofs.gfp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Despesa.
 */
@Entity
@Table(name = "tb_despesa")
public class Despesa implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_despesa", nullable = false)
    private LocalDate dataDespesa;

    @NotNull
    @Column(name = "mes_referencia", nullable = false)
    private LocalDate mesReferencia;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "valor", precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    @NotNull
    @Column(name = "usuario", nullable = false)
    private String usuario;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("despesas")
    private ContaPagamento contaPagamento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("despesas")
    private CategoriaDespesa categoriaDespesa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataDespesa() {
        return dataDespesa;
    }

    public Despesa dataDespesa(LocalDate dataDespesa) {
        this.dataDespesa = dataDespesa;
        return this;
    }

    public void setDataDespesa(LocalDate dataDespesa) {
        this.dataDespesa = dataDespesa;
    }

    public LocalDate getMesReferencia() {
        return mesReferencia;
    }

    public Despesa mesReferencia(LocalDate mesReferencia) {
        this.mesReferencia = mesReferencia;
        return this;
    }

    public void setMesReferencia(LocalDate mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public Despesa descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Despesa valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getUsuario() {
        return usuario;
    }

    public Despesa usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public ContaPagamento getContaPagamento() {
        return contaPagamento;
    }

    public Despesa contaPagamento(ContaPagamento contaPagamento) {
        this.contaPagamento = contaPagamento;
        return this;
    }

    public void setContaPagamento(ContaPagamento contaPagamento) {
        this.contaPagamento = contaPagamento;
    }

    public CategoriaDespesa getCategoriaDespesa() {
        return categoriaDespesa;
    }

    public Despesa categoriaDespesa(CategoriaDespesa categoriaDespesa) {
        this.categoriaDespesa = categoriaDespesa;
        return this;
    }

    public void setCategoriaDespesa(CategoriaDespesa categoriaDespesa) {
        this.categoriaDespesa = categoriaDespesa;
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
        Despesa despesa = (Despesa) o;
        if (despesa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), despesa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Despesa{" +
            "id=" + getId() +
            ", dataDespesa='" + getDataDespesa() + "'" +
            ", mesReferencia='" + getMesReferencia() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", valor=" + getValor() +
            ", usuario='" + getUsuario() + "'" +
            "}";
    }
}
