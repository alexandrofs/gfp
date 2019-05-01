package com.alexandrofs.gfp.web.rest;

import com.alexandrofs.gfp.GfpApp;

import com.alexandrofs.gfp.domain.TipoInvestimento;
import com.alexandrofs.gfp.domain.TipoImpostoRenda;
import com.alexandrofs.gfp.repository.TipoInvestimentoRepository;
import com.alexandrofs.gfp.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;


import static com.alexandrofs.gfp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TipoInvestimentoResource REST controller.
 *
 * @see TipoInvestimentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GfpApp.class)
public class TipoInvestimentoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_MODALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_MODALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_INDEXADOR = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_INDEXADOR = "BBBBBBBBBB";

    private static final String DEFAULT_INDICE = "AAAAAAAAAA";
    private static final String UPDATED_INDICE = "BBBBBBBBBB";

    @Autowired
    private TipoInvestimentoRepository tipoInvestimentoRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoInvestimentoMockMvc;

    private TipoInvestimento tipoInvestimento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoInvestimentoResource tipoInvestimentoResource = new TipoInvestimentoResource(tipoInvestimentoRepository);
        this.restTipoInvestimentoMockMvc = MockMvcBuilders.standaloneSetup(tipoInvestimentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoInvestimento createEntity(EntityManager em) {
        TipoInvestimento tipoInvestimento = new TipoInvestimento();
        tipoInvestimento.setNome(DEFAULT_NOME);
        tipoInvestimento.setDescricao(DEFAULT_DESCRICAO);
        tipoInvestimento.setModalidade(DEFAULT_MODALIDADE);
        tipoInvestimento.setTipoIndexador(DEFAULT_TIPO_INDEXADOR);
        tipoInvestimento.setIndice(DEFAULT_INDICE);
        // Add required entity
        TipoImpostoRenda tipoImpostoRenda = TipoImpostoRendaResourceIntTest.createEntity(em);
        em.persist(tipoImpostoRenda);
        em.flush();
        tipoInvestimento.setTipoImpostoRenda(tipoImpostoRenda);
        return tipoInvestimento;
    }

    @Before
    public void initTest() {
        tipoInvestimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoInvestimento() throws Exception {
        int databaseSizeBeforeCreate = tipoInvestimentoRepository.findAll().size();

        // Create the TipoInvestimento
        restTipoInvestimentoMockMvc.perform(post("/api/tipo-investimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoInvestimento)))
            .andExpect(status().isCreated());

        // Validate the TipoInvestimento in the database
        List<TipoInvestimento> tipoInvestimentoList = tipoInvestimentoRepository.findAll();
        assertThat(tipoInvestimentoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoInvestimento testTipoInvestimento = tipoInvestimentoList.get(tipoInvestimentoList.size() - 1);
        assertThat(testTipoInvestimento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTipoInvestimento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTipoInvestimento.getModalidade()).isEqualTo(DEFAULT_MODALIDADE);
        assertThat(testTipoInvestimento.getTipoIndexador()).isEqualTo(DEFAULT_TIPO_INDEXADOR);
        assertThat(testTipoInvestimento.getIndice()).isEqualTo(DEFAULT_INDICE);
    }

    @Test
    @Transactional
    public void createTipoInvestimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoInvestimentoRepository.findAll().size();

        // Create the TipoInvestimento with an existing ID
        tipoInvestimento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoInvestimentoMockMvc.perform(post("/api/tipo-investimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoInvestimento)))
            .andExpect(status().isBadRequest());

        // Validate the TipoInvestimento in the database
        List<TipoInvestimento> tipoInvestimentoList = tipoInvestimentoRepository.findAll();
        assertThat(tipoInvestimentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoInvestimentoRepository.findAll().size();
        // set the field null
        tipoInvestimento.setNome(null);

        // Create the TipoInvestimento, which fails.

        restTipoInvestimentoMockMvc.perform(post("/api/tipo-investimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoInvestimento)))
            .andExpect(status().isBadRequest());

        List<TipoInvestimento> tipoInvestimentoList = tipoInvestimentoRepository.findAll();
        assertThat(tipoInvestimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoInvestimentoRepository.findAll().size();
        // set the field null
        tipoInvestimento.setDescricao(null);

        // Create the TipoInvestimento, which fails.

        restTipoInvestimentoMockMvc.perform(post("/api/tipo-investimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoInvestimento)))
            .andExpect(status().isBadRequest());

        List<TipoInvestimento> tipoInvestimentoList = tipoInvestimentoRepository.findAll();
        assertThat(tipoInvestimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoInvestimentoRepository.findAll().size();
        // set the field null
        tipoInvestimento.setModalidade(null);

        // Create the TipoInvestimento, which fails.

        restTipoInvestimentoMockMvc.perform(post("/api/tipo-investimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoInvestimento)))
            .andExpect(status().isBadRequest());

        List<TipoInvestimento> tipoInvestimentoList = tipoInvestimentoRepository.findAll();
        assertThat(tipoInvestimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIndexadorIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoInvestimentoRepository.findAll().size();
        // set the field null
        tipoInvestimento.setTipoIndexador(null);

        // Create the TipoInvestimento, which fails.

        restTipoInvestimentoMockMvc.perform(post("/api/tipo-investimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoInvestimento)))
            .andExpect(status().isBadRequest());

        List<TipoInvestimento> tipoInvestimentoList = tipoInvestimentoRepository.findAll();
        assertThat(tipoInvestimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoInvestimentoRepository.findAll().size();
        // set the field null
        tipoInvestimento.setIndice(null);

        // Create the TipoInvestimento, which fails.

        restTipoInvestimentoMockMvc.perform(post("/api/tipo-investimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoInvestimento)))
            .andExpect(status().isBadRequest());

        List<TipoInvestimento> tipoInvestimentoList = tipoInvestimentoRepository.findAll();
        assertThat(tipoInvestimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoInvestimentos() throws Exception {
        // Initialize the database
        tipoInvestimentoRepository.saveAndFlush(tipoInvestimento);

        // Get all the tipoInvestimentoList
        restTipoInvestimentoMockMvc.perform(get("/api/tipo-investimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoInvestimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].modalidade").value(hasItem(DEFAULT_MODALIDADE.toString())))
            .andExpect(jsonPath("$.[*].tipoIndexador").value(hasItem(DEFAULT_TIPO_INDEXADOR.toString())))
            .andExpect(jsonPath("$.[*].indice").value(hasItem(DEFAULT_INDICE.toString())));
    }
    

    @Test
    @Transactional
    public void getTipoInvestimento() throws Exception {
        // Initialize the database
        tipoInvestimentoRepository.saveAndFlush(tipoInvestimento);

        // Get the tipoInvestimento
        restTipoInvestimentoMockMvc.perform(get("/api/tipo-investimentos/{id}", tipoInvestimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoInvestimento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.modalidade").value(DEFAULT_MODALIDADE.toString()))
            .andExpect(jsonPath("$.tipoIndexador").value(DEFAULT_TIPO_INDEXADOR.toString()))
            .andExpect(jsonPath("$.indice").value(DEFAULT_INDICE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTipoInvestimento() throws Exception {
        // Get the tipoInvestimento
        restTipoInvestimentoMockMvc.perform(get("/api/tipo-investimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoInvestimento() throws Exception {
        // Initialize the database
        tipoInvestimentoRepository.saveAndFlush(tipoInvestimento);

        int databaseSizeBeforeUpdate = tipoInvestimentoRepository.findAll().size();

        // Update the tipoInvestimento
        TipoInvestimento updatedTipoInvestimento = tipoInvestimentoRepository.findById(tipoInvestimento.getId()).get();
        // Disconnect from session so that the updates on updatedTipoInvestimento are not directly saved in db
        em.detach(updatedTipoInvestimento);
        updatedTipoInvestimento.setNome(UPDATED_NOME);
        updatedTipoInvestimento.setDescricao(UPDATED_DESCRICAO);
        updatedTipoInvestimento.setModalidade(UPDATED_MODALIDADE);
        updatedTipoInvestimento.setTipoIndexador(UPDATED_TIPO_INDEXADOR);
        updatedTipoInvestimento.setIndice(UPDATED_INDICE);

        restTipoInvestimentoMockMvc.perform(put("/api/tipo-investimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoInvestimento)))
            .andExpect(status().isOk());

        // Validate the TipoInvestimento in the database
        List<TipoInvestimento> tipoInvestimentoList = tipoInvestimentoRepository.findAll();
        assertThat(tipoInvestimentoList).hasSize(databaseSizeBeforeUpdate);
        TipoInvestimento testTipoInvestimento = tipoInvestimentoList.get(tipoInvestimentoList.size() - 1);
        assertThat(testTipoInvestimento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipoInvestimento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTipoInvestimento.getModalidade()).isEqualTo(UPDATED_MODALIDADE);
        assertThat(testTipoInvestimento.getTipoIndexador()).isEqualTo(UPDATED_TIPO_INDEXADOR);
        assertThat(testTipoInvestimento.getIndice()).isEqualTo(UPDATED_INDICE);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoInvestimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoInvestimentoRepository.findAll().size();

        // Create the TipoInvestimento

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoInvestimentoMockMvc.perform(put("/api/tipo-investimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoInvestimento)))
            .andExpect(status().isBadRequest());

        // Validate the TipoInvestimento in the database
        List<TipoInvestimento> tipoInvestimentoList = tipoInvestimentoRepository.findAll();
        assertThat(tipoInvestimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoInvestimento() throws Exception {
        // Initialize the database
        tipoInvestimentoRepository.saveAndFlush(tipoInvestimento);

        int databaseSizeBeforeDelete = tipoInvestimentoRepository.findAll().size();

        // Get the tipoInvestimento
        restTipoInvestimentoMockMvc.perform(delete("/api/tipo-investimentos/{id}", tipoInvestimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoInvestimento> tipoInvestimentoList = tipoInvestimentoRepository.findAll();
        assertThat(tipoInvestimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoInvestimento.class);
        TipoInvestimento tipoInvestimento1 = new TipoInvestimento();
        tipoInvestimento1.setId(1L);
        TipoInvestimento tipoInvestimento2 = new TipoInvestimento();
        tipoInvestimento2.setId(tipoInvestimento1.getId());
        assertThat(tipoInvestimento1).isEqualTo(tipoInvestimento2);
        tipoInvestimento2.setId(2L);
        assertThat(tipoInvestimento1).isNotEqualTo(tipoInvestimento2);
        tipoInvestimento1.setId(null);
        assertThat(tipoInvestimento1).isNotEqualTo(tipoInvestimento2);
    }
}
