package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.Investimento;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Investimento entity.
 */
@SuppressWarnings("unused")
public interface InvestimentoRepository extends JpaRepository<Investimento,Long> {

}
