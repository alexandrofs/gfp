package com.alexandrofs.gfp.service.dto;

import java.io.Serializable;
import java.util.Objects;
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
 * Criteria class for the LancamentoCartao entity. This class is used in LancamentoCartaoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /lancamento-cartaos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LancamentoCartaoCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dataCompra;

    private LocalDateFilter mesFatura;

    private StringFilter descricao;

    private BigDecimalFilter valor;

    private StringFilter usuario;

    private IntegerFilter quantidadeParcelas;

    private IntegerFilter parcela;

    private LongFilter contaPagamentoId;

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

    public IntegerFilter getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(IntegerFilter quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public IntegerFilter getParcela() {
        return parcela;
    }

    public void setParcela(IntegerFilter parcela) {
        this.parcela = parcela;
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
            Objects.equals(quantidadeParcelas, that.quantidadeParcelas) &&
            Objects.equals(parcela, that.parcela) &&
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
        quantidadeParcelas,
        parcela,
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
                (quantidadeParcelas != null ? "quantidadeParcelas=" + quantidadeParcelas + ", " : "") +
                (parcela != null ? "parcela=" + parcela + ", " : "") +
                (contaPagamentoId != null ? "contaPagamentoId=" + contaPagamentoId + ", " : "") +
            "}";
    }

}
