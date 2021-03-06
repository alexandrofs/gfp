package com.alexandrofs.gfp.service;

import com.alexandrofs.gfp.domain.IndiceSerieDi;
import com.alexandrofs.gfp.repository.IndiceSerieDiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing IndiceSerieDi.
 */
@Service
@Transactional
public class IndiceSerieDiService {

    private final Logger log = LoggerFactory.getLogger(IndiceSerieDiService.class);

    private final IndiceSerieDiRepository indiceSerieDiRepository;

    public IndiceSerieDiService(IndiceSerieDiRepository indiceSerieDiRepository) {
        this.indiceSerieDiRepository = indiceSerieDiRepository;
    }

    /**
     * Save a indiceSerieDi.
     *
     * @param indiceSerieDi the entity to save
     * @return the persisted entity
     */
    public IndiceSerieDi save(IndiceSerieDi indiceSerieDi) {
        log.debug("Request to save IndiceSerieDi : {}", indiceSerieDi);
        return indiceSerieDiRepository.save(indiceSerieDi);
    }

    /**
     * Get all the indiceSerieDis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IndiceSerieDi> findAll(Pageable pageable) {
        log.debug("Request to get all IndiceSerieDis");
        return indiceSerieDiRepository.findAll(pageable);
    }


    /**
     * Get one indiceSerieDi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<IndiceSerieDi> findOne(Long id) {
        log.debug("Request to get IndiceSerieDi : {}", id);
        return indiceSerieDiRepository.findById(id);
    }

    /**
     * Delete the indiceSerieDi by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IndiceSerieDi : {}", id);        indiceSerieDiRepository.deleteById(id);
    }
}
