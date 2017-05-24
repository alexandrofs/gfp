package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.IndiceSerieDi;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the IndiceSerieDi entity.
 */
@SuppressWarnings("unused")
public interface IndiceSerieDiRepository extends JpaRepository<IndiceSerieDi,Long> {
	
	public IndiceSerieDi findByData(LocalDate data);

}
