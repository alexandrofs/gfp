package com.alexandrofs.gfp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TipoImpostoRenda.
 */
@Entity
@Table(name = "tb_tipo_imposto_renda")
public class TipoImpostoRenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "codigo", length = 20, nullable = false)
    private String codigo;

    @NotNull
    @Size(max = 255)
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "tipoImpostoRenda")
    @JsonIgnore
    private Set<TabelaImpostoRenda> tabelaImpostoRendas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<TabelaImpostoRenda> getTabelaImpostoRendas() {
        return tabelaImpostoRendas;
    }

    public void setTabelaImpostoRendas(Set<TabelaImpostoRenda> tabelaImpostoRendas) {
        this.tabelaImpostoRendas = tabelaImpostoRendas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoImpostoRenda tipoImpostoRenda = (TipoImpostoRenda) o;
        if (tipoImpostoRenda.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tipoImpostoRenda.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoImpostoRenda{" +
            "id=" + id +
            ", codigo='" + codigo + "'" +
            ", descricao='" + descricao + "'" +
            '}';
    }
}
