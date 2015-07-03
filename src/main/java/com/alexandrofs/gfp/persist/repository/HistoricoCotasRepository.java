package com.alexandrofs.gfp.persist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexandrofs.gfp.model.HistoricoCotasEntity;
import com.alexandrofs.gfp.model.InvestimentoEntity;

public interface HistoricoCotasRepository extends JpaRepository<HistoricoCotasEntity, Long>{
	
	public List<HistoricoCotasEntity> findByInvestimentoOrderByDataCota(InvestimentoEntity entity);

}
