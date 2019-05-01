package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.Carteira;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Carteira entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

}
