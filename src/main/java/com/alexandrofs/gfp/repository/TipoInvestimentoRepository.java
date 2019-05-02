package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.TipoInvestimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoInvestimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoInvestimentoRepository extends JpaRepository<TipoInvestimento, Long> {

}
