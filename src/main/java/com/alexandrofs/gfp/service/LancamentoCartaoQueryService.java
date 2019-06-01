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

import com.alexandrofs.gfp.domain.LancamentoCartao;
import com.alexandrofs.gfp.domain.*; // for static metamodels
import com.alexandrofs.gfp.repository.LancamentoCartaoRepository;
import com.alexandrofs.gfp.service.dto.LancamentoCartaoCriteria;

/**
 * Service for executing complex queries for {@link LancamentoCartao} entities in the database.
 * The main input is a {@link LancamentoCartaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LancamentoCartao} or a {@link Page} of {@link LancamentoCartao} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LancamentoCartaoQueryService extends QueryService<LancamentoCartao> {

    private final Logger log = LoggerFactory.getLogger(LancamentoCartaoQueryService.class);

    private final LancamentoCartaoRepository lancamentoCartaoRepository;

    public LancamentoCartaoQueryService(LancamentoCartaoRepository lancamentoCartaoRepository) {
        this.lancamentoCartaoRepository = lancamentoCartaoRepository;
    }

    /**
     * Return a {@link List} of {@link LancamentoCartao} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LancamentoCartao> findByCriteria(LancamentoCartaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LancamentoCartao> specification = createSpecification(criteria);
        return lancamentoCartaoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LancamentoCartao} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LancamentoCartao> findByCriteria(LancamentoCartaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LancamentoCartao> specification = createSpecification(criteria);
        return lancamentoCartaoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LancamentoCartaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LancamentoCartao> specification = createSpecification(criteria);
        return lancamentoCartaoRepository.count(specification);
    }

    /**
     * Function to convert LancamentoCartaoCriteria to a {@link Specification}.
     */
    private Specification<LancamentoCartao> createSpecification(LancamentoCartaoCriteria criteria) {
        Specification<LancamentoCartao> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), LancamentoCartao_.id));
            }
            if (criteria.getDataCompra() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataCompra(), LancamentoCartao_.dataCompra));
            }
            if (criteria.getMesFatura() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMesFatura(), LancamentoCartao_.mesFatura));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), LancamentoCartao_.descricao));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), LancamentoCartao_.valor));
            }
            if (criteria.getUsuario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuario(), LancamentoCartao_.usuario));
            }
            if (criteria.getContaPagamentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaPagamentoId(),
                    root -> root.join(LancamentoCartao_.contaPagamento, JoinType.LEFT).get(ContaPagamento_.id)));
            }
        }
        return specification;
    }
}
