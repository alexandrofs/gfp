package com.alexandrofs.gfp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A HistoricoCotas.
 */
@Entity
@Table(name = "tb_historico_cotas")
public class HistoricoCotas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_cota", nullable = false)
    private LocalDate dataCota;

    @NotNull
    @Column(name = "vlr_cota", precision = 10, scale = 2, nullable = false)
    private BigDecimal vlrCota;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("historicoCotas")
    private Investimento investimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataCota() {
        return dataCota;
    }

    public void setDataCota(LocalDate dataCota) {
        this.dataCota = dataCota;
    }

    public BigDecimal getVlrCota() {
        return vlrCota;
    }

    public void setVlrCota(BigDecimal vlrCota) {
        this.vlrCota = vlrCota;
    }

    public Investimento getInvestimento() {
        return investimento;
    }

    public void setInvestimento(Investimento investimento) {
        this.investimento = investimento;
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
        HistoricoCotas historicoCotas = (HistoricoCotas) o;
        if (historicoCotas.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), historicoCotas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HistoricoCotas{" +
            "id=" + getId() +
            ", dataCota='" + getDataCota() + "'" +
            ", vlrCota=" + getVlrCota() +
            "}";
    }
}
