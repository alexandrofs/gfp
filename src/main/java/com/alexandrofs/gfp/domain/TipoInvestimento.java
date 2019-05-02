package com.alexandrofs.gfp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alexandrofs.gfp.domain.fixed.ModalidadeEnum;
import com.alexandrofs.gfp.domain.fixed.TipoIndexadorEnum;

/**
 * A TipoInvestimento.
 */
@Entity
@Table(name = "tb_tipo_investimento")
public class TipoInvestimento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "nome", length = 20, nullable = false)
    private String nome;

    @NotNull
    @Size(max = 255)
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "modalidade", length = 25, nullable = false)
    private ModalidadeEnum modalidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_indexador", length = 10, nullable = true)
    private TipoIndexadorEnum tipoIndexador;

    @Size(max = 10)
    @Column(name = "indice", length = 10, nullable = true)
    private String indice;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("tipoInvestimentos")
    private TipoImpostoRenda tipoImpostoRenda;

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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ModalidadeEnum getModalidade() {
        return modalidade;
    }

    public void setModalidade(ModalidadeEnum modalidade) {
        this.modalidade = modalidade;
    }

    public TipoIndexadorEnum getTipoIndexador() {
        return tipoIndexador;
    }

    public void setTipoIndexador(TipoIndexadorEnum tipoIndexador) {
        this.tipoIndexador = tipoIndexador;
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
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
        TipoInvestimento tipoInvestimento = (TipoInvestimento) o;
        if (tipoInvestimento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoInvestimento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoInvestimento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", modalidade='" + getModalidade() + "'" +
            ", tipoIndexador='" + getTipoIndexador() + "'" +
            ", indice='" + getIndice() + "'" +
            "}";
    }
}
