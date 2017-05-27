package com.alexandrofs.gfp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TipoInvestimento.
 */
@Entity
@Table(name = "tb_tipo_investimento")
public class TipoInvestimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Size(max = 25)
    @Column(name = "modalidade", length = 25, nullable = false)
    private String modalidade;

    @Size(max = 10)
    @Column(name = "tipo_indexador", length = 10, nullable = true)
    private String tipoIndexador;

    @Size(max = 10)
    @Column(name = "indice", length = 10, nullable = true)
    private String indice;

    @ManyToOne
    @NotNull
    private TipoImpostoRenda tipoImpostoRenda;

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

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getTipoIndexador() {
        return tipoIndexador;
    }

    public void setTipoIndexador(String tipoIndexador) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoInvestimento tipoInvestimento = (TipoInvestimento) o;
        if(tipoInvestimento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tipoInvestimento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoInvestimento{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", descricao='" + descricao + "'" +
            ", modalidade='" + modalidade + "'" +
            ", tipoIndexador='" + tipoIndexador + "'" +
            ", indice='" + indice + "'" +
            '}';
    }
}
