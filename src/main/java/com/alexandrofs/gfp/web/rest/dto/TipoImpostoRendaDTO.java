package com.alexandrofs.gfp.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the TipoImpostoRenda entity.
 */
public class TipoImpostoRendaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String codigo;

    @NotNull
    @Size(max = 255)
    private String descricao;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoImpostoRendaDTO tipoImpostoRendaDTO = (TipoImpostoRendaDTO) o;

        if ( ! Objects.equals(id, tipoImpostoRendaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoImpostoRendaDTO{" +
            "id=" + id +
            ", codigo='" + codigo + "'" +
            ", descricao='" + descricao + "'" +
            '}';
    }
}
