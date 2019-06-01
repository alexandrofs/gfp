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
 * Criteria class for the {@link com.alexandrofs.gfp.domain.LancamentoCartao} entity. This class is used
 * in {@link com.alexandrofs.gfp.web.rest.LancamentoCartaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /lancamento-cartaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LancamentoCartaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dataCompra;

    private LocalDateFilter mesFatura;

    private StringFilter descricao;

    private BigDecimalFilter valor;

    private StringFilter usuario;

    private LongFilter contaPagamentoId;

    public LancamentoCartaoCriteria(){
    }

    public LancamentoCartaoCriteria(LancamentoCartaoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.dataCompra = other.dataCompra == null ? null : other.dataCompra.copy();
        this.mesFatura = other.mesFatura == null ? null : other.mesFatura.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.usuario = other.usuario == null ? null : other.usuario.copy();
        this.contaPagamentoId = other.contaPagamentoId == null ? null : other.contaPagamentoId.copy();
    }

    @Override
    public LancamentoCartaoCriteria copy() {
        return new LancamentoCartaoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateFilter dataCompra) {
        this.dataCompra = dataCompra;
    }

    public LocalDateFilter getMesFatura() {
        return mesFatura;
    }

    public void setMesFatura(LocalDateFilter mesFatura) {
        this.mesFatura = mesFatura;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LancamentoCartaoCriteria that = (LancamentoCartaoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dataCompra, that.dataCompra) &&
            Objects.equals(mesFatura, that.mesFatura) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(usuario, that.usuario) &&
            Objects.equals(contaPagamentoId, that.contaPagamentoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dataCompra,
        mesFatura,
        descricao,
        valor,
        usuario,
        contaPagamentoId
        );
    }

    @Override
    public String toString() {
        return "LancamentoCartaoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dataCompra != null ? "dataCompra=" + dataCompra + ", " : "") +
                (mesFatura != null ? "mesFatura=" + mesFatura + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (usuario != null ? "usuario=" + usuario + ", " : "") +
                (contaPagamentoId != null ? "contaPagamentoId=" + contaPagamentoId + ", " : "") +
            "}";
    }

}
