package com.alexandrofs.gfp.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Investimento entity.
 */
public class InvestimentoDTO implements Serializable {

    private Long id;

    private String nome;

    @NotNull
    private LocalDate dataAplicacao;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal qtdeCota;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal vlrCota;


    private Long carteiraId;
    
    private Long tipoInvestimentoId;
    
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

    public Long getCarteiraId() {
        return carteiraId;
    }

    public void setCarteiraId(Long carteiraId) {
        this.carteiraId = carteiraId;
    }

    public Long getTipoInvestimentoId() {
        return tipoInvestimentoId;
    }

    public void setTipoInvestimentoId(Long tipoInvestimentoId) {
        this.tipoInvestimentoId = tipoInvestimentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InvestimentoDTO investimentoDTO = (InvestimentoDTO) o;

        if ( ! Objects.equals(id, investimentoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InvestimentoDTO{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", dataAplicacao='" + dataAplicacao + "'" +
            ", qtdeCota='" + qtdeCota + "'" +
            ", vlrCota='" + vlrCota + "'" +
            '}';
    }
}
