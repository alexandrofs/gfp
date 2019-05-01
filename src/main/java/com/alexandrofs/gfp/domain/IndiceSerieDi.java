package com.alexandrofs.gfp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A IndiceSerieDi.
 */
@Entity
@Table(name = "tb_indice_serie_di")
public class IndiceSerieDi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @NotNull
    @Column(name = "taxa_media_anual", precision = 10, scale = 2, nullable = false)
    private BigDecimal taxaMediaAnual;

    @NotNull
    @Column(name = "taxa_selic", precision = 10, scale = 2, nullable = false)
    private BigDecimal taxaSelic;

    @NotNull
    @Column(name = "fator_diario", precision = 10, scale = 2, nullable = false)
    private BigDecimal fatorDiario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getTaxaMediaAnual() {
        return taxaMediaAnual;
    }

    public void setTaxaMediaAnual(BigDecimal taxaMediaAnual) {
        this.taxaMediaAnual = taxaMediaAnual;
    }

    public BigDecimal getTaxaSelic() {
        return taxaSelic;
    }

    public void setTaxaSelic(BigDecimal taxaSelic) {
        this.taxaSelic = taxaSelic;
    }

    public BigDecimal getFatorDiario() {
        return fatorDiario;
    }

    public void setFatorDiario(BigDecimal fatorDiario) {
        this.fatorDiario = fatorDiario;
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
        IndiceSerieDi indiceSerieDi = (IndiceSerieDi) o;
        if (indiceSerieDi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), indiceSerieDi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IndiceSerieDi{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", taxaMediaAnual=" + getTaxaMediaAnual() +
            ", taxaSelic=" + getTaxaSelic() +
            ", fatorDiario=" + getFatorDiario() +
            "}";
    }
}
