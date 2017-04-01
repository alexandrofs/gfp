package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.TipoImpostoRenda;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TipoImpostoRenda entity.
 */
@SuppressWarnings("unused")
public interface TipoImpostoRendaRepository extends JpaRepository<TipoImpostoRenda,Long> {

	TipoImpostoRenda findByCodigo(String codImposto);

}
