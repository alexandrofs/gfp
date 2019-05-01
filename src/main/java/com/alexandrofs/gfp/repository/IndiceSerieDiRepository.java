package com.alexandrofs.gfp.repository;

import com.alexandrofs.gfp.domain.IndiceSerieDi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IndiceSerieDi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndiceSerieDiRepository extends JpaRepository<IndiceSerieDi, Long> {

}
