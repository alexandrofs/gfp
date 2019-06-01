package com.alexandrofs.gfp.service;

import com.alexandrofs.gfp.domain.LancamentoCartao;
import com.alexandrofs.gfp.repository.LancamentoCartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing LancamentoCartao.
 */
@Service
@Transactional
public class LancamentoCartaoService {

    private final Logger log = LoggerFactory.getLogger(LancamentoCartaoService.class);

    private final LancamentoCartaoRepository lancamentoCartaoRepository;

    public LancamentoCartaoService(LancamentoCartaoRepository lancamentoCartaoRepository) {
        this.lancamentoCartaoRepository = lancamentoCartaoRepository;
    }

    /**
     * Save a lancamentoCartao.
     *
     * @param lancamentoCartao the entity to save
     * @return the persisted entity
     */
    public LancamentoCartao save(LancamentoCartao lancamentoCartao) {
        log.debug("Request to save LancamentoCartao : {}", lancamentoCartao);
        return lancamentoCartaoRepository.save(lancamentoCartao);
    }

    /**
     * Get all the lancamentoCartaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LancamentoCartao> findAll(Pageable pageable) {
        log.debug("Request to get all LancamentoCartaos");
        return lancamentoCartaoRepository.findAll(pageable);
    }


    /**
     * Get one lancamentoCartao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<LancamentoCartao> findOne(Long id) {
        log.debug("Request to get LancamentoCartao : {}", id);
        return lancamentoCartaoRepository.findById(id);
    }

    /**
     * Delete the lancamentoCartao by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LancamentoCartao : {}", id);        lancamentoCartaoRepository.deleteById(id);
    }
}
