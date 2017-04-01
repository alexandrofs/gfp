package com.alexandrofs.gfp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Carteira.
 */
@Entity
@Table(name = "tb_carteira")
public class Carteira implements Serializable {

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

    @OneToMany(mappedBy = "carteira")
    @JsonIgnore
    private Set<Investimento> investimentos = new HashSet<>();

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

    public Set<Investimento> getInvestimentos() {
        return investimentos;
    }

    public void setInvestimentos(Set<Investimento> investimentos) {
        this.investimentos = investimentos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Carteira carteira = (Carteira) o;
        if(carteira.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, carteira.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Carteira{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", descricao='" + descricao + "'" +
            '}';
    }
}
