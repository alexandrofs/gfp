package com.alexandrofs.gfp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.alexandrofs.gfp.domain.Despesa} entity. This class is used
 * in {@link com.alexandrofs.gfp.web.rest.DespesaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /despesas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DespesaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dataDespesa;

    private LocalDateFilter mesReferencia;

    private StringFilter descricao;

    private BigDecimalFilter valor;

    private StringFilter usuario;

    private LongFilter contaPagamentoId;

    private LongFilter categoriaDespesaId;

    public DespesaCriteria(){
    }

    public DespesaCriteria(DespesaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.dataDespesa = other.dataDespesa == null ? null : other.dataDespesa.copy();
        this.mesReferencia = other.mesReferencia == null ? null : other.mesReferencia.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.usuario = other.usuario == null ? null : other.usuario.copy();
        this.contaPagamentoId = other.contaPagamentoId == null ? null : other.contaPagamentoId.copy();
        this.categoriaDespesaId = other.categoriaDespesaId == null ? null : other.categoriaDespesaId.copy();
    }

    @Override
    public DespesaCriteria copy() {
        return new DespesaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDataDespesa() {
        return dataDespesa;
    }

    public void setDataDespesa(LocalDateFilter dataDespesa) {
        this.dataDespesa = dataDespesa;
    }

    public LocalDateFilter getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(LocalDateFilter mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public BigDecimalFilter getValor() {
        return valor;
    }

    public void setValor(BigDecimalFilter valor) {
        this.valor = valor;
    }

    public StringFilter getUsuario() {
        return usuario;
    }

    public void setUsuario(StringFilter usuario) {
        this.usuario = usuario;
    }

    public LongFilter getContaPagamentoId() {
        return contaPagamentoId;
    }

    public void setContaPagamentoId(LongFilter contaPagamentoId) {
        this.contaPagamentoId = contaPagamentoId;
    }

    public LongFilter getCategoriaDespesaId() {
        return categoriaDespesaId;
    }

    public void setCategoriaDespesaId(LongFilter categoriaDespesaId) {
        this.categoriaDespesaId = categoriaDespesaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DespesaCriteria that = (DespesaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dataDespesa, that.dataDespesa) &&
            Objects.equals(mesReferencia, that.mesReferencia) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(usuario, that.usuario) &&
            Objects.equals(contaPagamentoId, that.contaPagamentoId) &&
            Objects.equals(categoriaDespesaId, that.categoriaDespesaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dataDespesa,
        mesReferencia,
        descricao,
        valor,
        usuario,
        contaPagamentoId,
        categoriaDespesaId
        );
    }

    @Override
    public String toString() {
        return "DespesaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dataDespesa != null ? "dataDespesa=" + dataDespesa + ", " : "") +
                (mesReferencia != null ? "mesReferencia=" + mesReferencia + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (usuario != null ? "usuario=" + usuario + ", " : "") +
                (contaPagamentoId != null ? "contaPagamentoId=" + contaPagamentoId + ", " : "") +
                (categoriaDespesaId != null ? "categoriaDespesaId=" + categoriaDespesaId + ", " : "") +
            "}";
    }

}
