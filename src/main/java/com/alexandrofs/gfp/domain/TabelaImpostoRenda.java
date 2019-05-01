package com.alexandrofs.gfp.domain;


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
    @Min(value = 0)
    @Column(name = "num_dias", nullable = false)
    private Long numDias;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "pct_aliquota", precision=10, scale=2, nullable = false)
    private BigDecimal pctAliquota;

    @ManyToOne(optional = false)
    @NotNull
    private TipoImpostoRenda tipoImpostoRenda;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TabelaImpostoRenda tabelaImpostoRenda = (TabelaImpostoRenda) o;
        if (tabelaImpostoRenda.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tabelaImpostoRenda.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TabelaImpostoRenda{" +
            "id=" + id +
            ", numDias='" + numDias + "'" +
            ", pctAliquota='" + pctAliquota + "'" +
            '}';
    }
}
