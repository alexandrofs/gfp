package com.alexandrofs.gfp.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the TipoInvestimento entity.
 */
public class TipoInvestimentoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String nome;

    @NotNull
    @Size(max = 255)
    private String descricao;


    private Long tipoImpostoRendaId;
    
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

    public Long getTipoImpostoRendaId() {
        return tipoImpostoRendaId;
    }

    public void setTipoImpostoRendaId(Long tipoImpostoRendaId) {
        this.tipoImpostoRendaId = tipoImpostoRendaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoInvestimentoDTO tipoInvestimentoDTO = (TipoInvestimentoDTO) o;

        if ( ! Objects.equals(id, tipoInvestimentoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoInvestimentoDTO{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", descricao='" + descricao + "'" +
            '}';
    }
}
