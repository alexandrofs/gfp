package com.alexandrofs.gfp.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.alexandrofs.gfp.domain.HistoricoCotas;
import com.alexandrofs.gfp.domain.Investimento;

/**
 * Spring Data  repository for the HistoricoCotas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoricoCotasRepository extends JpaRepository<HistoricoCotas, Long> {

	List<HistoricoCotas> findByInvestimentoOrderByDataCotaDesc(Investimento investimento);

	public Optional<HistoricoCotas> findFirstByInvestimentoOrderByDataCotaDesc(Investimento investimento);
	
}
