package com.alexandrofs.gfp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A TabelaImpostoRenda.
 */
@Entity
@Table(name = "tb_tabela_imposto_renda")
public class TabelaImpostoRenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0L)
    @Column(name = "num_dias", nullable = false)
    private Long numDias;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "pct_aliquota", precision = 10, scale = 2, nullable = false)
    private BigDecimal pctAliquota;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("tabelaImpostoRendas")
    private TipoImpostoRenda tipoImpostoRenda;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumDias() {
        return numDias;
    }

    public void setNumDias(Long numDias) {
        this.numDias = numDias;
    }

    public BigDecimal getPctAliquota() {
        return pctAliquota;
    }

    public void setPctAliquota(BigDecimal pctAliquota) {
        this.pctAliquota = pctAliquota;
    }

    public TipoImpostoRenda getTipoImpostoRenda() {
        return tipoImpostoRenda;
    }

    public void setTipoImpostoRenda(TipoImpostoRenda tipoImpostoRenda) {
        this.tipoImpostoRenda = tipoImpostoRenda;
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
        TabelaImpostoRenda tabelaImpostoRenda = (TabelaImpostoRenda) o;
        if (tabelaImpostoRenda.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tabelaImpostoRenda.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TabelaImpostoRenda{" +
            "id=" + getId() +
            ", numDias=" + getNumDias() +
            ", pctAliquota=" + getPctAliquota() +
            "}";
    }
}
