package com.alexandrofs.gfp.service;

import com.alexandrofs.gfp.domain.Investimento;
import com.alexandrofs.gfp.repository.InvestimentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Investimento.
 */
@Service
@Transactional
public class InvestimentoService {

    private final Logger log = LoggerFactory.getLogger(InvestimentoService.class);
    
    @Inject
    private InvestimentoRepository investimentoRepository;
    
    /**
     * Save a investimento.
     * 
     * @param investimento the entity to save
     * @return the persisted entity
     */
    public Investimento save(Investimento investimento) {
        log.debug("Request to save Investimento : {}", investimento);
        Investimento result = investimentoRepository.save(investimento);
        return result;
    }

    /**
     *  Get all the investimentos.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Investimento> findAll() {
        log.debug("Request to get all Investimentos");
        List<Investimento> result = investimentoRepository.findAll();
        return result;
    }

    /**
     *  Get one investimento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Investimento findOne(Long id) {
        log.debug("Request to get Investimento : {}", id);
        Investimento investimento = investimentoRepository.findOne(id);
        return investimento;
    }

    /**
     *  Delete the  investimento by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Investimento : {}", id);
        investimentoRepository.delete(id);
    }
}
