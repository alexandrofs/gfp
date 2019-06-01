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

import com.alexandrofs.gfp.domain.Despesa;
import com.alexandrofs.gfp.domain.*; // for static metamodels
import com.alexandrofs.gfp.repository.DespesaRepository;
import com.alexandrofs.gfp.service.dto.DespesaCriteria;

/**
 * Service for executing complex queries for {@link Despesa} entities in the database.
 * The main input is a {@link DespesaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Despesa} or a {@link Page} of {@link Despesa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DespesaQueryService extends QueryService<Despesa> {

    private final Logger log = LoggerFactory.getLogger(DespesaQueryService.class);

    private final DespesaRepository despesaRepository;

    public DespesaQueryService(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    /**
     * Return a {@link List} of {@link Despesa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Despesa> findByCriteria(DespesaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Despesa> specification = createSpecification(criteria);
        return despesaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Despesa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Despesa> findByCriteria(DespesaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Despesa> specification = createSpecification(criteria);
        return despesaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DespesaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Despesa> specification = createSpecification(criteria);
        return despesaRepository.count(specification);
    }

    /**
     * Function to convert DespesaCriteria to a {@link Specification}.
     */
    private Specification<Despesa> createSpecification(DespesaCriteria criteria) {
        Specification<Despesa> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Despesa_.id));
            }
            if (criteria.getDataDespesa() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataDespesa(), Despesa_.dataDespesa));
            }
            if (criteria.getMesReferencia() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMesReferencia(), Despesa_.mesReferencia));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Despesa_.descricao));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), Despesa_.valor));
            }
            if (criteria.getUsuario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuario(), Despesa_.usuario));
            }
            if (criteria.getContaPagamentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaPagamentoId(),
                    root -> root.join(Despesa_.contaPagamento, JoinType.LEFT).get(ContaPagamento_.id)));
            }
            if (criteria.getCategoriaDespesaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoriaDespesaId(),
                    root -> root.join(Despesa_.categoriaDespesa, JoinType.LEFT).get(CategoriaDespesa_.id)));
            }
        }
        return specification;
    }
}
