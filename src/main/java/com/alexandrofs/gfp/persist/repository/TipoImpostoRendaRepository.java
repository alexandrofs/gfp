package com.alexandrofs.gfp.persist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexandrofs.gfp.model.TipoImpostoRenda;

public interface TipoImpostoRendaRepository extends JpaRepository<TipoImpostoRenda, Long> {

	public TipoImpostoRenda findByCodigo (String codigo);
}
