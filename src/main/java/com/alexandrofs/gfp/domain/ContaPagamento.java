package com.alexandrofs.gfp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.alexandrofs.gfp.domain.enumeration.TipoConta;

/**
 * A ContaPagamento.
 */
@Entity
@Table(name = "tb_conta_pagamento")
public class ContaPagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta", nullable = false)
    private TipoConta tipoConta;

    @NotNull
    @Column(name = "usuario", nullable = false)
    private String usuario;

    @OneToMany(mappedBy = "contaPagamento")
    private Set<Lancamento> lancamentos = new HashSet<>();

    @OneToMany(mappedBy = "contaPagamento")
    private Set<LancamentoCartao> lancamentoCartaos = new HashSet<>();

    @OneToMany(mappedBy = "contaPagamento")
    private Set<Despesa> despesas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public ContaPagamento nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public ContaPagamento tipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
        return this;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getUsuario() {
        return usuario;
    }

    public ContaPagamento usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Set<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public ContaPagamento lancamentos(Set<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
        return this;
    }

    public ContaPagamento addLancamento(Lancamento lancamento) {
        this.lancamentos.add(lancamento);
        lancamento.setContaPagamento(this);
        return this;
    }

    public ContaPagamento removeLancamento(Lancamento lancamento) {
        this.lancamentos.remove(lancamento);
        lancamento.setContaPagamento(null);
        return this;
    }

    public void setLancamentos(Set<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public Set<LancamentoCartao> getLancamentoCartaos() {
        return lancamentoCartaos;
    }

    public ContaPagamento lancamentoCartaos(Set<LancamentoCartao> lancamentoCartaos) {
        this.lancamentoCartaos = lancamentoCartaos;
        return this;
    }

    public ContaPagamento addLancamentoCartao(LancamentoCartao lancamentoCartao) {
        this.lancamentoCartaos.add(lancamentoCartao);
        lancamentoCartao.setContaPagamento(this);
        return this;
    }

    public ContaPagamento removeLancamentoCartao(LancamentoCartao lancamentoCartao) {
        this.lancamentoCartaos.remove(lancamentoCartao);
        lancamentoCartao.setContaPagamento(null);
        return this;
    }

    public void setLancamentoCartaos(Set<LancamentoCartao> lancamentoCartaos) {
        this.lancamentoCartaos = lancamentoCartaos;
    }

    public Set<Despesa> getDespesas() {
        return despesas;
    }

    public ContaPagamento despesas(Set<Despesa> despesas) {
        this.despesas = despesas;
        return this;
    }

    public ContaPagamento addDespesa(Despesa despesa) {
        this.despesas.add(despesa);
        despesa.setContaPagamento(this);
        return this;
    }

    public ContaPagamento removeDespesa(Despesa despesa) {
        this.despesas.remove(despesa);
        despesa.setContaPagamento(null);
        return this;
    }

    public void setDespesas(Set<Despesa> despesas) {
        this.despesas = despesas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContaPagamento)) {
            return false;
        }
        return id != null && id.equals(((ContaPagamento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContaPagamento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tipoConta='" + getTipoConta() + "'" +
            ", usuario='" + getUsuario() + "'" +
            "}";
    }
}
