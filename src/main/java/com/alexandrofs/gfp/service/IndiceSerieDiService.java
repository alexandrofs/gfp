package com.alexandrofs.gfp.service;

import com.alexandrofs.gfp.domain.IndiceSerieDi;
import com.alexandrofs.gfp.repository.IndiceSerieDiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing IndiceSerieDi.
 */
@Service
@Transactional
public class IndiceSerieDiService {

    private final Logger log = LoggerFactory.getLogger(IndiceSerieDiService.class);
    
    @Inject
    private IndiceSerieDiRepository indiceSerieDiRepository;
    
    /**
     * Save a indiceSerieDi.
     * 
     * @param indiceSerieDi the entity to save
     * @return the persisted entity
     */
    public IndiceSerieDi save(IndiceSerieDi indiceSerieDi) {
        log.debug("Request to save IndiceSerieDi : {}", indiceSerieDi);
        IndiceSerieDi result = indiceSerieDiRepository.save(indiceSerieDi);
        return result;
    }

    /**
     *  Get all the indiceSerieDis.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<IndiceSerieDi> findAll(Pageable pageable) {
        log.debug("Request to get all IndiceSerieDis");
        Page<IndiceSerieDi> result = indiceSerieDiRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one indiceSerieDi by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public IndiceSerieDi findOne(Long id) {
        log.debug("Request to get IndiceSerieDi : {}", id);
        IndiceSerieDi indiceSerieDi = indiceSerieDiRepository.findOne(id);
        return indiceSerieDi;
    }

    /**
     *  Delete the  indiceSerieDi by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IndiceSerieDi : {}", id);
        indiceSerieDiRepository.delete(id);
    }
}
