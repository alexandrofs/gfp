package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.LancamentoCartao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LancamentoCartao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LancamentoCartaoRepository extends JpaRepository<LancamentoCartao, Long> {

}
