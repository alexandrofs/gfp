package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.Instituicao;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Instituicao entity.
 */
@SuppressWarnings("unused")
public interface InstituicaoRepository extends JpaRepository<Instituicao,Long> {

}
