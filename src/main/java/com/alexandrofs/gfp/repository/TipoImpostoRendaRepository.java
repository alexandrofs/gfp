package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.TipoImpostoRenda;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoImpostoRenda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoImpostoRendaRepository extends JpaRepository<TipoImpostoRenda, Long> {

}
