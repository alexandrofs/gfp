package com.alexandrofs.gfp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CategoriaDespesa.
 */
@Entity
@Table(name = "tb_categoria_despesa")
public class CategoriaDespesa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "usuario", nullable = false)
    private String usuario;

    @OneToMany(mappedBy = "categoriaDespesa")
    private Set<Despesa> despesas = new HashSet<>();

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

    public CategoriaDespesa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public CategoriaDespesa usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Set<Despesa> getDespesas() {
        return despesas;
    }

    public CategoriaDespesa despesas(Set<Despesa> despesas) {
        this.despesas = despesas;
        return this;
    }

    public CategoriaDespesa addDespesa(Despesa despesa) {
        this.despesas.add(despesa);
        despesa.setCategoriaDespesa(this);
        return this;
    }

    public CategoriaDespesa removeDespesa(Despesa despesa) {
        this.despesas.remove(despesa);
        despesa.setCategoriaDespesa(null);
        return this;
    }

    public void setDespesas(Set<Despesa> despesas) {
        this.despesas = despesas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaDespesa)) {
            return false;
        }
        return id != null && id.equals(((CategoriaDespesa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CategoriaDespesa{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", usuario='" + getUsuario() + "'" +
            "}";
    }
}
