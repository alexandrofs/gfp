package com.alexandrofs.gfp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexandrofs.gfp.domain.HistoricoCotas;
import com.alexandrofs.gfp.domain.Investimento;

/**
 * Spring Data JPA repository for the HistoricoCotas entity.
 */
@SuppressWarnings("unused")
public interface HistoricoCotasRepository extends JpaRepository<HistoricoCotas,Long> {

	List<HistoricoCotas> findByInvestimentoOrderByDataCotaDesc(Investimento investimento);

}
