package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.CategoriaDespesa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CategoriaDespesa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaDespesaRepository extends JpaRepository<CategoriaDespesa, Long> {

}
