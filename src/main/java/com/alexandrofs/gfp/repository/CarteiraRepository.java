package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.Carteira;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Carteira entity.
 */
@SuppressWarnings("unused")
public interface CarteiraRepository extends JpaRepository<Carteira,Long> {

}
