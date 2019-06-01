package com.alexandrofs.gfp.service;

import com.alexandrofs.gfp.domain.Lancamento;
import com.alexandrofs.gfp.repository.LancamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Lancamento.
 */
@Service
@Transactional
public class LancamentoService {

    private final Logger log = LoggerFactory.getLogger(LancamentoService.class);

    private final LancamentoRepository lancamentoRepository;

    public LancamentoService(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    /**
     * Save a lancamento.
     *
     * @param lancamento the entity to save
     * @return the persisted entity
     */
    public Lancamento save(Lancamento lancamento) {
        log.debug("Request to save Lancamento : {}", lancamento);
        return lancamentoRepository.save(lancamento);
    }

    /**
     * Get all the lancamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Lancamento> findAll(Pageable pageable) {
        log.debug("Request to get all Lancamentos");
        return lancamentoRepository.findAll(pageable);
    }


    /**
     * Get one lancamento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Lancamento> findOne(Long id) {
        log.debug("Request to get Lancamento : {}", id);
        return lancamentoRepository.findById(id);
    }

    /**
     * Delete the lancamento by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Lancamento : {}", id);        lancamentoRepository.deleteById(id);
    }
}
