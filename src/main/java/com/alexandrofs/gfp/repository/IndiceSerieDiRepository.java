package com.alexandrofs.gfp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexandrofs.gfp.domain.IndiceSerieDi;

/**
 * Spring Data JPA repository for the IndiceSerieDi entity.
 */
@SuppressWarnings("unused")
public interface IndiceSerieDiRepository extends JpaRepository<IndiceSerieDi,Long> {
	
	public IndiceSerieDi findByData(LocalDate data);
	
	public List<IndiceSerieDi> findByDataBetweenOrderByData(LocalDate dataI, LocalDate dataF);

}
