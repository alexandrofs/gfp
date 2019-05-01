package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.TipoInvestimento;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TipoInvestimento entity.
 */
@SuppressWarnings("unused")
public interface TipoInvestimentoRepository extends JpaRepository<TipoInvestimento,Long> {

}
