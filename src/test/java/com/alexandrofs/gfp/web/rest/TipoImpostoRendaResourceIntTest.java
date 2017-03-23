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

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.alexandrofs.gfp.AbstractTest;
import com.alexandrofs.gfp.domain.TipoImpostoRenda;
import com.alexandrofs.gfp.repository.TipoImpostoRendaRepository;


/**
 * Test class for the TipoImpostoRendaResource REST controller.
 *
 * @see TipoImpostoRendaResource
 */
public class TipoImpostoRendaResourceIntTest extends AbstractTest {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private TipoImpostoRendaRepository tipoImpostoRendaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTipoImpostoRendaMockMvc;

    private TipoImpostoRenda tipoImpostoRenda;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TipoImpostoRendaResource tipoImpostoRendaResource = new TipoImpostoRendaResource();
        ReflectionTestUtils.setField(tipoImpostoRendaResource, "tipoImpostoRendaRepository", tipoImpostoRendaRepository);
        this.restTipoImpostoRendaMockMvc = MockMvcBuilders.standaloneSetup(tipoImpostoRendaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tipoImpostoRenda = new TipoImpostoRenda();
        tipoImpostoRenda.setCodigo(DEFAULT_CODIGO);
        tipoImpostoRenda.setDescricao(DEFAULT_DESCRICAO);
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
        List<TipoImpostoRenda> tipoImpostoRendas = tipoImpostoRendaRepository.findAll();
        assertThat(tipoImpostoRendas).hasSize(databaseSizeBeforeCreate + 1);
        TipoImpostoRenda testTipoImpostoRenda = tipoImpostoRendas.get(tipoImpostoRendas.size() - 1);
        assertThat(testTipoImpostoRenda.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testTipoImpostoRenda.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
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

        List<TipoImpostoRenda> tipoImpostoRendas = tipoImpostoRendaRepository.findAll();
        assertThat(tipoImpostoRendas).hasSize(databaseSizeBeforeTest);
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

        List<TipoImpostoRenda> tipoImpostoRendas = tipoImpostoRendaRepository.findAll();
        assertThat(tipoImpostoRendas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoImpostoRendas() throws Exception {
        // Initialize the database
        tipoImpostoRendaRepository.saveAndFlush(tipoImpostoRenda);

        // Get all the tipoImpostoRendas
        restTipoImpostoRendaMockMvc.perform(get("/api/tipo-imposto-rendas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
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
        TipoImpostoRenda updatedTipoImpostoRenda = new TipoImpostoRenda();
        updatedTipoImpostoRenda.setId(tipoImpostoRenda.getId());
        updatedTipoImpostoRenda.setCodigo(UPDATED_CODIGO);
        updatedTipoImpostoRenda.setDescricao(UPDATED_DESCRICAO);

        restTipoImpostoRendaMockMvc.perform(put("/api/tipo-imposto-rendas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTipoImpostoRenda)))
                .andExpect(status().isOk());

        // Validate the TipoImpostoRenda in the database
        List<TipoImpostoRenda> tipoImpostoRendas = tipoImpostoRendaRepository.findAll();
        assertThat(tipoImpostoRendas).hasSize(databaseSizeBeforeUpdate);
        TipoImpostoRenda testTipoImpostoRenda = tipoImpostoRendas.get(tipoImpostoRendas.size() - 1);
        assertThat(testTipoImpostoRenda.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTipoImpostoRenda.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void deleteTipoImpostoRenda() throws Exception {
        // Initialize the database
        tipoImpostoRendaRepository.saveAndFlush(tipoImpostoRenda);
        int databaseSizeBeforeDelete = tipoImpostoRendaRepository.findAll().size();

        // Get the tipoImpostoRenda
        restTipoImpostoRendaMockMvc.perform(delete("/api/tipo-imposto-rendas/{id}", tipoImpostoRenda.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoImpostoRenda> tipoImpostoRendas = tipoImpostoRendaRepository.findAll();
        assertThat(tipoImpostoRendas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
