package com.alexandrofs.gfp.service;

import com.alexandrofs.gfp.domain.Investimento;
import com.alexandrofs.gfp.repository.InvestimentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Investimento.
 */
@Service
@Transactional
public class InvestimentoService {

    private final Logger log = LoggerFactory.getLogger(InvestimentoService.class);

    private final InvestimentoRepository investimentoRepository;

    public InvestimentoService(InvestimentoRepository investimentoRepository) {
        this.investimentoRepository = investimentoRepository;
    }

    /**
     * Save a investimento.
     *
     * @param investimento the entity to save
     * @return the persisted entity
     */
    public Investimento save(Investimento investimento) {
        log.debug("Request to save Investimento : {}", investimento);        return investimentoRepository.save(investimento);
    }

    /**
     * Get all the investimentos.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Investimento> findAll() {
        log.debug("Request to get all Investimentos");
        return investimentoRepository.findAll();
    }


    /**
     * Get one investimento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Investimento> findOne(Long id) {
        log.debug("Request to get Investimento : {}", id);
        return investimentoRepository.findById(id);
    }

    /**
     * Delete the investimento by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Investimento : {}", id);
        investimentoRepository.deleteById(id);
    }
}
