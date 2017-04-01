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
import com.alexandrofs.gfp.domain.TipoInvestimento;
import com.alexandrofs.gfp.repository.TipoInvestimentoRepository;


/**
 * Test class for the TipoInvestimentoResource REST controller.
 *
 * @see TipoInvestimentoResource
 */
public class TipoInvestimentoResourceIntTest extends AbstractTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private TipoInvestimentoRepository tipoInvestimentoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTipoInvestimentoMockMvc;

    private TipoInvestimento tipoInvestimento;
    
    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TipoInvestimentoResource tipoInvestimentoResource = new TipoInvestimentoResource();
        ReflectionTestUtils.setField(tipoInvestimentoResource, "tipoInvestimentoRepository", tipoInvestimentoRepository);
        this.restTipoInvestimentoMockMvc = MockMvcBuilders.standaloneSetup(tipoInvestimentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tipoInvestimento = new TipoInvestimento();
        tipoInvestimento.setNome(DEFAULT_NOME);
        tipoInvestimento.setDescricao(DEFAULT_DESCRICAO);
        tipoInvestimento.setTipoImpostoRenda(dsl.dado().tipoImpostoRenda().salva());
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
        List<TipoInvestimento> tipoInvestimentos = tipoInvestimentoRepository.findAll();
        assertThat(tipoInvestimentos).hasSize(databaseSizeBeforeCreate + 1);
        TipoInvestimento testTipoInvestimento = tipoInvestimentos.get(tipoInvestimentos.size() - 1);
        assertThat(testTipoInvestimento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTipoInvestimento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
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

        List<TipoInvestimento> tipoInvestimentos = tipoInvestimentoRepository.findAll();
        assertThat(tipoInvestimentos).hasSize(databaseSizeBeforeTest);
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

        List<TipoInvestimento> tipoInvestimentos = tipoInvestimentoRepository.findAll();
        assertThat(tipoInvestimentos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoInvestimentos() throws Exception {
        // Initialize the database
        tipoInvestimentoRepository.saveAndFlush(tipoInvestimento);

        // Get all the tipoInvestimentos
        restTipoInvestimentoMockMvc.perform(get("/api/tipo-investimentos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tipoInvestimento.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getTipoInvestimento() throws Exception {
        // Initialize the database
        tipoInvestimentoRepository.saveAndFlush(tipoInvestimento);

        // Get the tipoInvestimento
        restTipoInvestimentoMockMvc.perform(get("/api/tipo-investimentos/{id}", tipoInvestimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tipoInvestimento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
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
        TipoInvestimento updatedTipoInvestimento = new TipoInvestimento();
        updatedTipoInvestimento.setId(tipoInvestimento.getId());
        updatedTipoInvestimento.setNome(UPDATED_NOME);
        updatedTipoInvestimento.setDescricao(UPDATED_DESCRICAO);
        updatedTipoInvestimento.setTipoImpostoRenda(tipoInvestimento.getTipoImpostoRenda());

        restTipoInvestimentoMockMvc.perform(put("/api/tipo-investimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTipoInvestimento)))
                .andExpect(status().isOk());

        // Validate the TipoInvestimento in the database
        List<TipoInvestimento> tipoInvestimentos = tipoInvestimentoRepository.findAll();
        assertThat(tipoInvestimentos).hasSize(databaseSizeBeforeUpdate);
        TipoInvestimento testTipoInvestimento = tipoInvestimentos.get(tipoInvestimentos.size() - 1);
        assertThat(testTipoInvestimento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipoInvestimento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
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
        List<TipoInvestimento> tipoInvestimentos = tipoInvestimentoRepository.findAll();
        assertThat(tipoInvestimentos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
