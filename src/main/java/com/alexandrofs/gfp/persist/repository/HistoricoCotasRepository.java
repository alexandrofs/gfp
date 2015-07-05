package com.alexandrofs.gfp.persist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexandrofs.gfp.model.HistoricoCotas;
import com.alexandrofs.gfp.model.Investimento;

public interface HistoricoCotasRepository extends JpaRepository<HistoricoCotas, Long>{
	
	public List<HistoricoCotas> findByInvestimentoOrderByDataCotaDesc(Investimento entity);

}
