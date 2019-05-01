package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.HistoricoCotas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HistoricoCotas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoricoCotasRepository extends JpaRepository<HistoricoCotas, Long> {

}
