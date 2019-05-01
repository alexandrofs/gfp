package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.Investimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Investimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Long> {

}
