package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.ContaPagamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContaPagamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContaPagamentoRepository extends JpaRepository<ContaPagamento, Long> {

}
