package com.alexandrofs.gfp.web.rest;

import com.alexandrofs.gfp.GfpApp;
import com.alexandrofs.gfp.domain.Investimento;
import com.alexandrofs.gfp.repository.InvestimentoRepository;
import com.alexandrofs.gfp.service.InvestimentoService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the InvestimentoResource REST controller.
 *
 * @see InvestimentoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GfpApp.class)
@WebAppConfiguration
@IntegrationTest
public class InvestimentoResourceIntTest {


    private static final LocalDate DEFAULT_DATA_APLICACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_APLICACAO = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_QTDE_COTA = new BigDecimal(0);
    private static final BigDecimal UPDATED_QTDE_COTA = new BigDecimal(1);

    private static final BigDecimal DEFAULT_VLR_COTA = new BigDecimal(0);
    private static final BigDecimal UPDATED_VLR_COTA = new BigDecimal(1);

    private static final BigDecimal DEFAULT_PCT_PRE_POS_FIXADO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PCT_PRE_POS_FIXADO = new BigDecimal(2);

    @Inject
    private InvestimentoRepository investimentoRepository;

    @Inject
    private InvestimentoService investimentoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInvestimentoMockMvc;

    private Investimento investimento;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvestimentoResource investimentoResource = new InvestimentoResource();
        ReflectionTestUtils.setField(investimentoResource, "investimentoService", investimentoService);
        this.restInvestimentoMockMvc = MockMvcBuilders.standaloneSetup(investimentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        investimento = new Investimento();
        investimento.setDataAplicacao(DEFAULT_DATA_APLICACAO);
        investimento.setQtdeCota(DEFAULT_QTDE_COTA);
        investimento.setVlrCota(DEFAULT_VLR_COTA);
        investimento.setPctPrePosFixado(DEFAULT_PCT_PRE_POS_FIXADO);
    }

    @Test
    @Transactional
    public void createInvestimento() throws Exception {
        int databaseSizeBeforeCreate = investimentoRepository.findAll().size();

        // Create the Investimento

        restInvestimentoMockMvc.perform(post("/api/investimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(investimento)))
                .andExpect(status().isCreated());

        // Validate the Investimento in the database
        List<Investimento> investimentos = investimentoRepository.findAll();
        assertThat(investimentos).hasSize(databaseSizeBeforeCreate + 1);
        Investimento testInvestimento = investimentos.get(investimentos.size() - 1);
        assertThat(testInvestimento.getDataAplicacao()).isEqualTo(DEFAULT_DATA_APLICACAO);
        assertThat(testInvestimento.getQtdeCota()).isEqualTo(DEFAULT_QTDE_COTA);
        assertThat(testInvestimento.getVlrCota()).isEqualTo(DEFAULT_VLR_COTA);
        assertThat(testInvestimento.getPctPrePosFixado()).isEqualTo(DEFAULT_PCT_PRE_POS_FIXADO);
    }

    @Test
    @Transactional
    public void checkDataAplicacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = investimentoRepository.findAll().size();
        // set the field null
        investimento.setDataAplicacao(null);

        // Create the Investimento, which fails.

        restInvestimentoMockMvc.perform(post("/api/investimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(investimento)))
                .andExpect(status().isBadRequest());

        List<Investimento> investimentos = investimentoRepository.findAll();
        assertThat(investimentos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQtdeCotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = investimentoRepository.findAll().size();
        // set the field null
        investimento.setQtdeCota(null);

        // Create the Investimento, which fails.

        restInvestimentoMockMvc.perform(post("/api/investimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(investimento)))
                .andExpect(status().isBadRequest());

        List<Investimento> investimentos = investimentoRepository.findAll();
        assertThat(investimentos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVlrCotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = investimentoRepository.findAll().size();
        // set the field null
        investimento.setVlrCota(null);

        // Create the Investimento, which fails.

        restInvestimentoMockMvc.perform(post("/api/investimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(investimento)))
                .andExpect(status().isBadRequest());

        List<Investimento> investimentos = investimentoRepository.findAll();
        assertThat(investimentos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvestimentos() throws Exception {
        // Initialize the database
        investimentoRepository.saveAndFlush(investimento);

        // Get all the investimentos
        restInvestimentoMockMvc.perform(get("/api/investimentos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(investimento.getId().intValue())))
                .andExpect(jsonPath("$.[*].dataAplicacao").value(hasItem(DEFAULT_DATA_APLICACAO.toString())))
                .andExpect(jsonPath("$.[*].qtdeCota").value(hasItem(DEFAULT_QTDE_COTA.intValue())))
                .andExpect(jsonPath("$.[*].vlrCota").value(hasItem(DEFAULT_VLR_COTA.intValue())))
                .andExpect(jsonPath("$.[*].pctPrePosFixado").value(hasItem(DEFAULT_PCT_PRE_POS_FIXADO.intValue())));
    }

    @Test
    @Transactional
    public void getInvestimento() throws Exception {
        // Initialize the database
        investimentoRepository.saveAndFlush(investimento);

        // Get the investimento
        restInvestimentoMockMvc.perform(get("/api/investimentos/{id}", investimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(investimento.getId().intValue()))
            .andExpect(jsonPath("$.dataAplicacao").value(DEFAULT_DATA_APLICACAO.toString()))
            .andExpect(jsonPath("$.qtdeCota").value(DEFAULT_QTDE_COTA.intValue()))
            .andExpect(jsonPath("$.vlrCota").value(DEFAULT_VLR_COTA.intValue()))
            .andExpect(jsonPath("$.pctPrePosFixado").value(DEFAULT_PCT_PRE_POS_FIXADO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInvestimento() throws Exception {
        // Get the investimento
        restInvestimentoMockMvc.perform(get("/api/investimentos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvestimento() throws Exception {
        // Initialize the database
        investimentoService.save(investimento);

        int databaseSizeBeforeUpdate = investimentoRepository.findAll().size();

        // Update the investimento
        Investimento updatedInvestimento = new Investimento();
        updatedInvestimento.setId(investimento.getId());
        updatedInvestimento.setDataAplicacao(UPDATED_DATA_APLICACAO);
        updatedInvestimento.setQtdeCota(UPDATED_QTDE_COTA);
        updatedInvestimento.setVlrCota(UPDATED_VLR_COTA);
        updatedInvestimento.setPctPrePosFixado(UPDATED_PCT_PRE_POS_FIXADO);

        restInvestimentoMockMvc.perform(put("/api/investimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedInvestimento)))
                .andExpect(status().isOk());

        // Validate the Investimento in the database
        List<Investimento> investimentos = investimentoRepository.findAll();
        assertThat(investimentos).hasSize(databaseSizeBeforeUpdate);
        Investimento testInvestimento = investimentos.get(investimentos.size() - 1);
        assertThat(testInvestimento.getDataAplicacao()).isEqualTo(UPDATED_DATA_APLICACAO);
        assertThat(testInvestimento.getQtdeCota()).isEqualTo(UPDATED_QTDE_COTA);
        assertThat(testInvestimento.getVlrCota()).isEqualTo(UPDATED_VLR_COTA);
        assertThat(testInvestimento.getPctPrePosFixado()).isEqualTo(UPDATED_PCT_PRE_POS_FIXADO);
    }

    @Test
    @Transactional
    public void deleteInvestimento() throws Exception {
        // Initialize the database
        investimentoService.save(investimento);

        int databaseSizeBeforeDelete = investimentoRepository.findAll().size();

        // Get the investimento
        restInvestimentoMockMvc.perform(delete("/api/investimentos/{id}", investimento.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Investimento> investimentos = investimentoRepository.findAll();
        assertThat(investimentos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
