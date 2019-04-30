package com.alexandrofs.gfp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexandrofs.gfp.domain.HistoricoCotas;
import com.alexandrofs.gfp.domain.Investimento;
import com.alexandrofs.gfp.repository.HistoricoCotasRepository;
import com.alexandrofs.gfp.repository.InvestimentoRepository;

/**
 * Service Implementation for managing Investimento.
 */
@Service
@Transactional
public class InvestimentoService {

    private final Logger log = LoggerFactory.getLogger(InvestimentoService.class);
    
    @Inject
    private InvestimentoRepository investimentoRepository;
    
    @Autowired
    private HistoricoCotasRepository cotasRepository;
    
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
        return result.stream().map(this::calculaSaldoBruto).collect(Collectors.toList());
    }

	private Investimento calculaSaldoBruto(final Investimento i) {
		Optional<HistoricoCotas> cotaDesc = cotasRepository.findFirstByInvestimentoOrderByDataCotaDesc(i);
		if (cotaDesc.isPresent()) {
			i.setVlrSaldoBruto(cotaDesc.get().getVlrCota().multiply(i.getQtdeCota()));
		} else {
			i.setVlrSaldoBruto(i.getVlrCota().multiply(i.getQtdeCota()));
		}
		return i;
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
        return investimento != null ? calculaSaldoBruto(investimento) : investimento;
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
