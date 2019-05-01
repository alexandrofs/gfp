package com.alexandrofs.gfp.web.rest;

import com.alexandrofs.gfp.GfpApp;
import com.alexandrofs.gfp.domain.Carteira;
import com.alexandrofs.gfp.repository.CarteiraRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CarteiraResource REST controller.
 *
 * @see CarteiraResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GfpApp.class)
@WebAppConfiguration
@IntegrationTest
public class CarteiraResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private CarteiraRepository carteiraRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCarteiraMockMvc;

    private Carteira carteira;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarteiraResource carteiraResource = new CarteiraResource();
        ReflectionTestUtils.setField(carteiraResource, "carteiraRepository", carteiraRepository);
        this.restCarteiraMockMvc = MockMvcBuilders.standaloneSetup(carteiraResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        carteira = new Carteira();
        carteira.setNome(DEFAULT_NOME);
        carteira.setDescricao(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createCarteira() throws Exception {
        int databaseSizeBeforeCreate = carteiraRepository.findAll().size();

        // Create the Carteira

        restCarteiraMockMvc.perform(post("/api/carteiras")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carteira)))
                .andExpect(status().isCreated());

        // Validate the Carteira in the database
        List<Carteira> carteiras = carteiraRepository.findAll();
        assertThat(carteiras).hasSize(databaseSizeBeforeCreate + 1);
        Carteira testCarteira = carteiras.get(carteiras.size() - 1);
        assertThat(testCarteira.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCarteira.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = carteiraRepository.findAll().size();
        // set the field null
        carteira.setNome(null);

        // Create the Carteira, which fails.

        restCarteiraMockMvc.perform(post("/api/carteiras")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carteira)))
                .andExpect(status().isBadRequest());

        List<Carteira> carteiras = carteiraRepository.findAll();
        assertThat(carteiras).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = carteiraRepository.findAll().size();
        // set the field null
        carteira.setDescricao(null);

        // Create the Carteira, which fails.

        restCarteiraMockMvc.perform(post("/api/carteiras")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carteira)))
                .andExpect(status().isBadRequest());

        List<Carteira> carteiras = carteiraRepository.findAll();
        assertThat(carteiras).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarteiras() throws Exception {
        // Initialize the database
        carteiraRepository.saveAndFlush(carteira);

        // Get all the carteiras
        restCarteiraMockMvc.perform(get("/api/carteiras?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(carteira.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getCarteira() throws Exception {
        // Initialize the database
        carteiraRepository.saveAndFlush(carteira);

        // Get the carteira
        restCarteiraMockMvc.perform(get("/api/carteiras/{id}", carteira.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(carteira.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCarteira() throws Exception {
        // Get the carteira
        restCarteiraMockMvc.perform(get("/api/carteiras/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarteira() throws Exception {
        // Initialize the database
        carteiraRepository.saveAndFlush(carteira);
        int databaseSizeBeforeUpdate = carteiraRepository.findAll().size();

        // Update the carteira
        Carteira updatedCarteira = new Carteira();
        updatedCarteira.setId(carteira.getId());
        updatedCarteira.setNome(UPDATED_NOME);
        updatedCarteira.setDescricao(UPDATED_DESCRICAO);

        restCarteiraMockMvc.perform(put("/api/carteiras")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCarteira)))
                .andExpect(status().isOk());

        // Validate the Carteira in the database
        List<Carteira> carteiras = carteiraRepository.findAll();
        assertThat(carteiras).hasSize(databaseSizeBeforeUpdate);
        Carteira testCarteira = carteiras.get(carteiras.size() - 1);
        assertThat(testCarteira.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCarteira.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void deleteCarteira() throws Exception {
        // Initialize the database
        carteiraRepository.saveAndFlush(carteira);
        int databaseSizeBeforeDelete = carteiraRepository.findAll().size();

        // Get the carteira
        restCarteiraMockMvc.perform(delete("/api/carteiras/{id}", carteira.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Carteira> carteiras = carteiraRepository.findAll();
        assertThat(carteiras).hasSize(databaseSizeBeforeDelete - 1);
    }
}
