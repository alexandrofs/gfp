package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.HistoricoCotas;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HistoricoCotas entity.
 */
@SuppressWarnings("unused")
public interface HistoricoCotasRepository extends JpaRepository<HistoricoCotas,Long> {

}
