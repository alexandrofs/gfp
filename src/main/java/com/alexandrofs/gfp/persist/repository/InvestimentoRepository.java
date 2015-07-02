package com.alexandrofs.gfp.persist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexandrofs.gfp.model.InvestimentoEntity;

@Repository
public interface InvestimentoRepository extends JpaRepository<InvestimentoEntity, Long> {

}
