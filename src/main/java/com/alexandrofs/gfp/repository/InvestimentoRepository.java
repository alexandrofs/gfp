package com.alexandrofs.gfp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexandrofs.gfp.model.Investimento;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Long> {

}
