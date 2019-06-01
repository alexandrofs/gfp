package com.alexandrofs.gfp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.alexandrofs.gfp.domain.Lancamento;
import com.alexandrofs.gfp.domain.*; // for static metamodels
import com.alexandrofs.gfp.repository.LancamentoRepository;
import com.alexandrofs.gfp.service.dto.LancamentoCriteria;

/**
 * Service for executing complex queries for {@link Lancamento} entities in the database.
 * The main input is a {@link LancamentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Lancamento} or a {@link Page} of {@link Lancamento} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LancamentoQueryService extends QueryService<Lancamento> {

    private final Logger log = LoggerFactory.getLogger(LancamentoQueryService.class);

    private final LancamentoRepository lancamentoRepository;

    public LancamentoQueryService(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    /**
     * Return a {@link List} of {@link Lancamento} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Lancamento> findByCriteria(LancamentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Lancamento> specification = createSpecification(criteria);
        return lancamentoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Lancamento} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Lancamento> findByCriteria(LancamentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Lancamento> specification = createSpecification(criteria);
        return lancamentoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LancamentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Lancamento> specification = createSpecification(criteria);
        return lancamentoRepository.count(specification);
    }

    /**
     * Function to convert LancamentoCriteria to a {@link Specification}.
     */
    private Specification<Lancamento> createSpecification(LancamentoCriteria criteria) {
        Specification<Lancamento> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Lancamento_.id));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), Lancamento_.data));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Lancamento_.descricao));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), Lancamento_.valor));
            }
            if (criteria.getUsuario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuario(), Lancamento_.usuario));
            }
            if (criteria.getContaPagamentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaPagamentoId(),
                    root -> root.join(Lancamento_.contaPagamento, JoinType.LEFT).get(ContaPagamento_.id)));
            }
        }
        return specification;
    }
}
