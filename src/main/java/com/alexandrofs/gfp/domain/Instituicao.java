package com.alexandrofs.gfp.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Instituicao.
 */
@Entity
@Table(name = "tb_instituicao")
public class Instituicao implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Instituicao instituicao = (Instituicao) o;
        if (instituicao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), instituicao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Instituicao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
