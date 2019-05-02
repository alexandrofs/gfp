package com.alexandrofs.gfp.web.rest;

import com.alexandrofs.gfp.GfpApp;

import com.alexandrofs.gfp.domain.TipoImpostoRenda;
import com.alexandrofs.gfp.repository.TipoImpostoRendaRepository;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.alexandrofs.gfp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TipoImpostoRendaResource REST controller.
 *
 * @see TipoImpostoRendaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GfpApp.class)
public class TipoImpostoRendaResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TipoImpostoRendaRepository tipoImpostoRendaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTipoImpostoRendaMockMvc;

    private TipoImpostoRenda tipoImpostoRenda;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoImpostoRendaResource tipoImpostoRendaResource = new TipoImpostoRendaResource(tipoImpostoRendaRepository);
        this.restTipoImpostoRendaMockMvc = MockMvcBuilders.standaloneSetup(tipoImpostoRendaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoImpostoRenda createEntity(EntityManager em) {
        TipoImpostoRenda tipoImpostoRenda = new TipoImpostoRenda();
        tipoImpostoRenda.setCodigo(DEFAULT_CODIGO);
        tipoImpostoRenda.setDescricao(DEFAULT_DESCRICAO);
        return tipoImpostoRenda;
    }

    @Before
    public void initTest() {
        tipoImpostoRenda = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoImpostoRenda() throws Exception {
        int databaseSizeBeforeCreate = tipoImpostoRendaRepository.findAll().size();

        // Create the TipoImpostoRenda
        restTipoImpostoRendaMockMvc.perform(post("/api/tipo-imposto-rendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoImpostoRenda)))
            .andExpect(status().isCreated());

        // Validate the TipoImpostoRenda in the database
        List<TipoImpostoRenda> tipoImpostoRendaList = tipoImpostoRendaRepository.findAll();
        assertThat(tipoImpostoRendaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoImpostoRenda testTipoImpostoRenda = tipoImpostoRendaList.get(tipoImpostoRendaList.size() - 1);
        assertThat(testTipoImpostoRenda.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testTipoImpostoRenda.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createTipoImpostoRendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoImpostoRendaRepository.findAll().size();

        // Create the TipoImpostoRenda with an existing ID
        tipoImpostoRenda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoImpostoRendaMockMvc.perform(post("/api/tipo-imposto-rendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoImpostoRenda)))
            .andExpect(status().isBadRequest());

        // Validate the TipoImpostoRenda in the database
        List<TipoImpostoRenda> tipoImpostoRendaList = tipoImpostoRendaRepository.findAll();
        assertThat(tipoImpostoRendaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoImpostoRendaRepository.findAll().size();
        // set the field null
        tipoImpostoRenda.setCodigo(null);

        // Create the TipoImpostoRenda, which fails.

        restTipoImpostoRendaMockMvc.perform(post("/api/tipo-imposto-rendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoImpostoRenda)))
            .andExpect(status().isBadRequest());

        List<TipoImpostoRenda> tipoImpostoRendaList = tipoImpostoRendaRepository.findAll();
        assertThat(tipoImpostoRendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoImpostoRendaRepository.findAll().size();
        // set the field null
        tipoImpostoRenda.setDescricao(null);

        // Create the TipoImpostoRenda, which fails.

        restTipoImpostoRendaMockMvc.perform(post("/api/tipo-imposto-rendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoImpostoRenda)))
            .andExpect(status().isBadRequest());

        List<TipoImpostoRenda> tipoImpostoRendaList = tipoImpostoRendaRepository.findAll();
        assertThat(tipoImpostoRendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoImpostoRendas() throws Exception {
        // Initialize the database
        tipoImpostoRendaRepository.saveAndFlush(tipoImpostoRenda);

        // Get all the tipoImpostoRendaList
        restTipoImpostoRendaMockMvc.perform(get("/api/tipo-imposto-rendas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoImpostoRenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoImpostoRenda() throws Exception {
        // Initialize the database
        tipoImpostoRendaRepository.saveAndFlush(tipoImpostoRenda);

        // Get the tipoImpostoRenda
        restTipoImpostoRendaMockMvc.perform(get("/api/tipo-imposto-rendas/{id}", tipoImpostoRenda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoImpostoRenda.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoImpostoRenda() throws Exception {
        // Get the tipoImpostoRenda
        restTipoImpostoRendaMockMvc.perform(get("/api/tipo-imposto-rendas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoImpostoRenda() throws Exception {
        // Initialize the database
        tipoImpostoRendaRepository.saveAndFlush(tipoImpostoRenda);

        int databaseSizeBeforeUpdate = tipoImpostoRendaRepository.findAll().size();

        // Update the tipoImpostoRenda
        TipoImpostoRenda updatedTipoImpostoRenda = tipoImpostoRendaRepository.findById(tipoImpostoRenda.getId()).get();
        // Disconnect from session so that the updates on updatedTipoImpostoRenda are not directly saved in db
        em.detach(updatedTipoImpostoRenda);
        updatedTipoImpostoRenda.setCodigo(UPDATED_CODIGO);
        updatedTipoImpostoRenda.setDescricao(UPDATED_DESCRICAO);

        restTipoImpostoRendaMockMvc.perform(put("/api/tipo-imposto-rendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoImpostoRenda)))
            .andExpect(status().isOk());

        // Validate the TipoImpostoRenda in the database
        List<TipoImpostoRenda> tipoImpostoRendaList = tipoImpostoRendaRepository.findAll();
        assertThat(tipoImpostoRendaList).hasSize(databaseSizeBeforeUpdate);
        TipoImpostoRenda testTipoImpostoRenda = tipoImpostoRendaList.get(tipoImpostoRendaList.size() - 1);
        assertThat(testTipoImpostoRenda.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTipoImpostoRenda.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoImpostoRenda() throws Exception {
        int databaseSizeBeforeUpdate = tipoImpostoRendaRepository.findAll().size();

        // Create the TipoImpostoRenda

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoImpostoRendaMockMvc.perform(put("/api/tipo-imposto-rendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoImpostoRenda)))
            .andExpect(status().isBadRequest());

        // Validate the TipoImpostoRenda in the database
        List<TipoImpostoRenda> tipoImpostoRendaList = tipoImpostoRendaRepository.findAll();
        assertThat(tipoImpostoRendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoImpostoRenda() throws Exception {
        // Initialize the database
        tipoImpostoRendaRepository.saveAndFlush(tipoImpostoRenda);

        int databaseSizeBeforeDelete = tipoImpostoRendaRepository.findAll().size();

        // Delete the tipoImpostoRenda
        restTipoImpostoRendaMockMvc.perform(delete("/api/tipo-imposto-rendas/{id}", tipoImpostoRenda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoImpostoRenda> tipoImpostoRendaList = tipoImpostoRendaRepository.findAll();
        assertThat(tipoImpostoRendaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoImpostoRenda.class);
        TipoImpostoRenda tipoImpostoRenda1 = new TipoImpostoRenda();
        tipoImpostoRenda1.setId(1L);
        TipoImpostoRenda tipoImpostoRenda2 = new TipoImpostoRenda();
        tipoImpostoRenda2.setId(tipoImpostoRenda1.getId());
        assertThat(tipoImpostoRenda1).isEqualTo(tipoImpostoRenda2);
        tipoImpostoRenda2.setId(2L);
        assertThat(tipoImpostoRenda1).isNotEqualTo(tipoImpostoRenda2);
        tipoImpostoRenda1.setId(null);
        assertThat(tipoImpostoRenda1).isNotEqualTo(tipoImpostoRenda2);
    }
}
