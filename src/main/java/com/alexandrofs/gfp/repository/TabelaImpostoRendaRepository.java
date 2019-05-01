package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.TabelaImpostoRenda;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TabelaImpostoRenda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TabelaImpostoRendaRepository extends JpaRepository<TabelaImpostoRenda, Long> {

}
