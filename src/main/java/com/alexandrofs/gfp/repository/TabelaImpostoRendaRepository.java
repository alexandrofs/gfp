package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.TabelaImpostoRenda;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TabelaImpostoRenda entity.
 */
@SuppressWarnings("unused")
public interface TabelaImpostoRendaRepository extends JpaRepository<TabelaImpostoRenda,Long> {

}
