package com.alexandrofs.gfp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.alexandrofs.gfp.GfpApp;
import com.alexandrofs.gfp.domain.TabelaImpostoRenda;
import com.alexandrofs.gfp.domain.TipoImpostoRenda;
import com.alexandrofs.gfp.repository.TabelaImpostoRendaRepository;
import static org.hamcrest.Matchers.hasItem;

/**
 * Test class for the TabelaImpostoRendaResource REST controller.
 *
 * @see TabelaImpostoRendaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GfpApp.class)
public class TabelaImpostoRendaResourceIntTest {

    private static final Long DEFAULT_NUM_DIAS = 0L;
    private static final Long UPDATED_NUM_DIAS = 1L;

    private static final BigDecimal DEFAULT_PCT_ALIQUOTA = new BigDecimal(0);
    private static final BigDecimal UPDATED_PCT_ALIQUOTA = new BigDecimal(1);

    @Autowired
    private TabelaImpostoRendaRepository tabelaImpostoRendaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTabelaImpostoRendaMockMvc;

    private TabelaImpostoRenda tabelaImpostoRenda;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TabelaImpostoRendaResource tabelaImpostoRendaResource = new TabelaImpostoRendaResource(tabelaImpostoRendaRepository);
        this.restTabelaImpostoRendaMockMvc = MockMvcBuilders.standaloneSetup(tabelaImpostoRendaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TabelaImpostoRenda createEntity(EntityManager em) {
        TabelaImpostoRenda tabelaImpostoRenda = new TabelaImpostoRenda();
        tabelaImpostoRenda.setNumDias(DEFAULT_NUM_DIAS);
        tabelaImpostoRenda.setPctAliquota(DEFAULT_PCT_ALIQUOTA);
        // Add required entity
        TipoImpostoRenda tipoImpostoRenda = TipoImpostoRendaResourceIntTest.createEntity(em);
        em.persist(tipoImpostoRenda);
        em.flush();
        tabelaImpostoRenda.setTipoImpostoRenda(tipoImpostoRenda);
        return tabelaImpostoRenda;
    }

    @Before
    public void initTest() {
        tabelaImpostoRenda = createEntity(em);
    }

    @Test
    @Transactional
    public void createTabelaImpostoRenda() throws Exception {
        int databaseSizeBeforeCreate = tabelaImpostoRendaRepository.findAll().size();

        // Create the TabelaImpostoRenda

        restTabelaImpostoRendaMockMvc.perform(post("/api/tabela-imposto-rendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tabelaImpostoRenda)))
            .andExpect(status().isCreated());

        // Validate the TabelaImpostoRenda in the database
        List<TabelaImpostoRenda> tabelaImpostoRendaList = tabelaImpostoRendaRepository.findAll();
        assertThat(tabelaImpostoRendaList).hasSize(databaseSizeBeforeCreate + 1);
        TabelaImpostoRenda testTabelaImpostoRenda = tabelaImpostoRendaList.get(tabelaImpostoRendaList.size() - 1);
        assertThat(testTabelaImpostoRenda.getNumDias()).isEqualTo(DEFAULT_NUM_DIAS);
        assertThat(testTabelaImpostoRenda.getPctAliquota()).isEqualTo(DEFAULT_PCT_ALIQUOTA);
    }

    @Test
    @Transactional
    public void createTabelaImpostoRendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tabelaImpostoRendaRepository.findAll().size();

        // Create the TabelaImpostoRenda with an existing ID
        TabelaImpostoRenda existingTabelaImpostoRenda = new TabelaImpostoRenda();
        existingTabelaImpostoRenda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTabelaImpostoRendaMockMvc.perform(post("/api/tabela-imposto-rendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTabelaImpostoRenda)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TabelaImpostoRenda> tabelaImpostoRendaList = tabelaImpostoRendaRepository.findAll();
        assertThat(tabelaImpostoRendaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumDiasIsRequired() throws Exception {
        int databaseSizeBeforeTest = tabelaImpostoRendaRepository.findAll().size();
        // set the field null
        tabelaImpostoRenda.setNumDias(null);

        // Create the TabelaImpostoRenda, which fails.

        restTabelaImpostoRendaMockMvc.perform(post("/api/tabela-imposto-rendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tabelaImpostoRenda)))
            .andExpect(status().isBadRequest());

        List<TabelaImpostoRenda> tabelaImpostoRendaList = tabelaImpostoRendaRepository.findAll();
        assertThat(tabelaImpostoRendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPctAliquotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tabelaImpostoRendaRepository.findAll().size();
        // set the field null
        tabelaImpostoRenda.setPctAliquota(null);

        // Create the TabelaImpostoRenda, which fails.

        restTabelaImpostoRendaMockMvc.perform(post("/api/tabela-imposto-rendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tabelaImpostoRenda)))
            .andExpect(status().isBadRequest());

        List<TabelaImpostoRenda> tabelaImpostoRendaList = tabelaImpostoRendaRepository.findAll();
        assertThat(tabelaImpostoRendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTabelaImpostoRendas() throws Exception {
        // Initialize the database
        tabelaImpostoRendaRepository.saveAndFlush(tabelaImpostoRenda);

        // Get all the tabelaImpostoRendaList
        restTabelaImpostoRendaMockMvc.perform(get("/api/tabela-imposto-rendas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tabelaImpostoRenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].numDias").value(hasItem(DEFAULT_NUM_DIAS.intValue())))
            .andExpect(jsonPath("$.[*].pctAliquota").value(hasItem(DEFAULT_PCT_ALIQUOTA.intValue())));
    }

    @Test
    @Transactional
    public void getTabelaImpostoRenda() throws Exception {
        // Initialize the database
        tabelaImpostoRendaRepository.saveAndFlush(tabelaImpostoRenda);

        // Get the tabelaImpostoRenda
        restTabelaImpostoRendaMockMvc.perform(get("/api/tabela-imposto-rendas/{id}", tabelaImpostoRenda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tabelaImpostoRenda.getId().intValue()))
            .andExpect(jsonPath("$.numDias").value(DEFAULT_NUM_DIAS.intValue()))
            .andExpect(jsonPath("$.pctAliquota").value(DEFAULT_PCT_ALIQUOTA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTabelaImpostoRenda() throws Exception {
        // Get the tabelaImpostoRenda
        restTabelaImpostoRendaMockMvc.perform(get("/api/tabela-imposto-rendas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTabelaImpostoRenda() throws Exception {
        // Initialize the database
        tabelaImpostoRendaRepository.saveAndFlush(tabelaImpostoRenda);
        int databaseSizeBeforeUpdate = tabelaImpostoRendaRepository.findAll().size();

        // Update the tabelaImpostoRenda
        TabelaImpostoRenda updatedTabelaImpostoRenda = tabelaImpostoRendaRepository.findOne(tabelaImpostoRenda.getId());
        updatedTabelaImpostoRenda.setNumDias(UPDATED_NUM_DIAS);
        updatedTabelaImpostoRenda.setPctAliquota(UPDATED_PCT_ALIQUOTA);
        updatedTabelaImpostoRenda.setTipoImpostoRenda(tabelaImpostoRenda.getTipoImpostoRenda());

        restTabelaImpostoRendaMockMvc.perform(put("/api/tabela-imposto-rendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTabelaImpostoRenda)))
            .andExpect(status().isOk());

        // Validate the TabelaImpostoRenda in the database
        List<TabelaImpostoRenda> tabelaImpostoRendaList = tabelaImpostoRendaRepository.findAll();
        assertThat(tabelaImpostoRendaList).hasSize(databaseSizeBeforeUpdate);
        TabelaImpostoRenda testTabelaImpostoRenda = tabelaImpostoRendaList.get(tabelaImpostoRendaList.size() - 1);
        assertThat(testTabelaImpostoRenda.getNumDias()).isEqualTo(UPDATED_NUM_DIAS);
        assertThat(testTabelaImpostoRenda.getPctAliquota()).isEqualTo(UPDATED_PCT_ALIQUOTA);
    }

    @Test
    @Transactional
    public void updateNonExistingTabelaImpostoRenda() throws Exception {
        int databaseSizeBeforeUpdate = tabelaImpostoRendaRepository.findAll().size();

        // Create the TabelaImpostoRenda

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTabelaImpostoRendaMockMvc.perform(put("/api/tabela-imposto-rendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tabelaImpostoRenda)))
            .andExpect(status().isCreated());

        // Validate the TabelaImpostoRenda in the database
        List<TabelaImpostoRenda> tabelaImpostoRendaList = tabelaImpostoRendaRepository.findAll();
        assertThat(tabelaImpostoRendaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTabelaImpostoRenda() throws Exception {
        // Initialize the database
        tabelaImpostoRendaRepository.saveAndFlush(tabelaImpostoRenda);
        int databaseSizeBeforeDelete = tabelaImpostoRendaRepository.findAll().size();

        // Get the tabelaImpostoRenda
        restTabelaImpostoRendaMockMvc.perform(delete("/api/tabela-imposto-rendas/{id}", tabelaImpostoRenda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TabelaImpostoRenda> tabelaImpostoRendaList = tabelaImpostoRendaRepository.findAll();
        assertThat(tabelaImpostoRendaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TabelaImpostoRenda.class);
    }
}
