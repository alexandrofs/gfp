package com.alexandrofs.gfp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexandrofs.gfp.domain.IndiceSerieDi;


/**
 * Spring Data  repository for the IndiceSerieDi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImportaIndiceSerieDiRepository extends JpaRepository<IndiceSerieDi, Long> {

	public IndiceSerieDi findByData(LocalDate data);
	
	public List<IndiceSerieDi> findByDataBetweenOrderByData(LocalDate dataI, LocalDate dataF);
	
}
