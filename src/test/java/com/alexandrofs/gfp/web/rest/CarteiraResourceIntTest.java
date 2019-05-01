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
import com.alexandrofs.gfp.domain.Carteira;
import com.alexandrofs.gfp.repository.CarteiraRepository;
import static org.hamcrest.Matchers.hasItem;

/**
 * Test class for the CarteiraResource REST controller.
 *
 * @see CarteiraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GfpApp.class)
public class CarteiraResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCarteiraMockMvc;

    private Carteira carteira;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CarteiraResource carteiraResource = new CarteiraResource(carteiraRepository);
        this.restCarteiraMockMvc = MockMvcBuilders.standaloneSetup(carteiraResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carteira createEntity(EntityManager em) {
        Carteira carteira = new Carteira();
        carteira.setNome(DEFAULT_NOME);
        carteira.setDescricao(DEFAULT_DESCRICAO);
        return carteira;
    }

    @Before
    public void initTest() {
        carteira = createEntity(em);
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
        List<Carteira> carteiraList = carteiraRepository.findAll();
        assertThat(carteiraList).hasSize(databaseSizeBeforeCreate + 1);
        Carteira testCarteira = carteiraList.get(carteiraList.size() - 1);
        assertThat(testCarteira.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCarteira.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createCarteiraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carteiraRepository.findAll().size();

        // Create the Carteira with an existing ID
        Carteira existingCarteira = new Carteira();
        existingCarteira.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarteiraMockMvc.perform(post("/api/carteiras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCarteira)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Carteira> carteiraList = carteiraRepository.findAll();
        assertThat(carteiraList).hasSize(databaseSizeBeforeCreate);
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

        List<Carteira> carteiraList = carteiraRepository.findAll();
        assertThat(carteiraList).hasSize(databaseSizeBeforeTest);
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

        List<Carteira> carteiraList = carteiraRepository.findAll();
        assertThat(carteiraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarteiras() throws Exception {
        // Initialize the database
        carteiraRepository.saveAndFlush(carteira);

        // Get all the carteiraList
        restCarteiraMockMvc.perform(get("/api/carteiras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
        Carteira updatedCarteira = carteiraRepository.findOne(carteira.getId());
        updatedCarteira.setNome(UPDATED_NOME);
        updatedCarteira.setDescricao(UPDATED_DESCRICAO);

        restCarteiraMockMvc.perform(put("/api/carteiras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCarteira)))
            .andExpect(status().isOk());

        // Validate the Carteira in the database
        List<Carteira> carteiraList = carteiraRepository.findAll();
        assertThat(carteiraList).hasSize(databaseSizeBeforeUpdate);
        Carteira testCarteira = carteiraList.get(carteiraList.size() - 1);
        assertThat(testCarteira.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCarteira.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingCarteira() throws Exception {
        int databaseSizeBeforeUpdate = carteiraRepository.findAll().size();

        // Create the Carteira

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarteiraMockMvc.perform(put("/api/carteiras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carteira)))
            .andExpect(status().isCreated());

        // Validate the Carteira in the database
        List<Carteira> carteiraList = carteiraRepository.findAll();
        assertThat(carteiraList).hasSize(databaseSizeBeforeUpdate + 1);
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
        List<Carteira> carteiraList = carteiraRepository.findAll();
        assertThat(carteiraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carteira.class);
    }
}
