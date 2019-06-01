package com.alexandrofs.gfp.web.rest;

import com.alexandrofs.gfp.GfpApp;

import com.alexandrofs.gfp.domain.CategoriaDespesa;
import com.alexandrofs.gfp.repository.CategoriaDespesaRepository;
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
 * Test class for the CategoriaDespesaResource REST controller.
 *
 * @see CategoriaDespesaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GfpApp.class)
public class CategoriaDespesaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    @Autowired
    private CategoriaDespesaRepository categoriaDespesaRepository;

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

    private MockMvc restCategoriaDespesaMockMvc;

    private CategoriaDespesa categoriaDespesa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoriaDespesaResource categoriaDespesaResource = new CategoriaDespesaResource(categoriaDespesaRepository);
        this.restCategoriaDespesaMockMvc = MockMvcBuilders.standaloneSetup(categoriaDespesaResource)
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
    public static CategoriaDespesa createEntity(EntityManager em) {
        CategoriaDespesa categoriaDespesa = new CategoriaDespesa()
            .nome(DEFAULT_NOME)
            .usuario(DEFAULT_USUARIO);
        return categoriaDespesa;
    }

    @Before
    public void initTest() {
        categoriaDespesa = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoriaDespesa() throws Exception {
        int databaseSizeBeforeCreate = categoriaDespesaRepository.findAll().size();

        // Create the CategoriaDespesa
        restCategoriaDespesaMockMvc.perform(post("/api/categoria-despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaDespesa)))
            .andExpect(status().isCreated());

        // Validate the CategoriaDespesa in the database
        List<CategoriaDespesa> categoriaDespesaList = categoriaDespesaRepository.findAll();
        assertThat(categoriaDespesaList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaDespesa testCategoriaDespesa = categoriaDespesaList.get(categoriaDespesaList.size() - 1);
        assertThat(testCategoriaDespesa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCategoriaDespesa.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    public void createCategoriaDespesaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriaDespesaRepository.findAll().size();

        // Create the CategoriaDespesa with an existing ID
        categoriaDespesa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaDespesaMockMvc.perform(post("/api/categoria-despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaDespesa)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaDespesa in the database
        List<CategoriaDespesa> categoriaDespesaList = categoriaDespesaRepository.findAll();
        assertThat(categoriaDespesaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriaDespesaRepository.findAll().size();
        // set the field null
        categoriaDespesa.setNome(null);

        // Create the CategoriaDespesa, which fails.

        restCategoriaDespesaMockMvc.perform(post("/api/categoria-despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaDespesa)))
            .andExpect(status().isBadRequest());

        List<CategoriaDespesa> categoriaDespesaList = categoriaDespesaRepository.findAll();
        assertThat(categoriaDespesaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriaDespesaRepository.findAll().size();
        // set the field null
        categoriaDespesa.setUsuario(null);

        // Create the CategoriaDespesa, which fails.

        restCategoriaDespesaMockMvc.perform(post("/api/categoria-despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaDespesa)))
            .andExpect(status().isBadRequest());

        List<CategoriaDespesa> categoriaDespesaList = categoriaDespesaRepository.findAll();
        assertThat(categoriaDespesaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoriaDespesas() throws Exception {
        // Initialize the database
        categoriaDespesaRepository.saveAndFlush(categoriaDespesa);

        // Get all the categoriaDespesaList
        restCategoriaDespesaMockMvc.perform(get("/api/categoria-despesas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaDespesa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO.toString())));
    }
    
    @Test
    @Transactional
    public void getCategoriaDespesa() throws Exception {
        // Initialize the database
        categoriaDespesaRepository.saveAndFlush(categoriaDespesa);

        // Get the categoriaDespesa
        restCategoriaDespesaMockMvc.perform(get("/api/categoria-despesas/{id}", categoriaDespesa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaDespesa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategoriaDespesa() throws Exception {
        // Get the categoriaDespesa
        restCategoriaDespesaMockMvc.perform(get("/api/categoria-despesas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoriaDespesa() throws Exception {
        // Initialize the database
        categoriaDespesaRepository.saveAndFlush(categoriaDespesa);

        int databaseSizeBeforeUpdate = categoriaDespesaRepository.findAll().size();

        // Update the categoriaDespesa
        CategoriaDespesa updatedCategoriaDespesa = categoriaDespesaRepository.findById(categoriaDespesa.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriaDespesa are not directly saved in db
        em.detach(updatedCategoriaDespesa);
        updatedCategoriaDespesa
            .nome(UPDATED_NOME)
            .usuario(UPDATED_USUARIO);

        restCategoriaDespesaMockMvc.perform(put("/api/categoria-despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategoriaDespesa)))
            .andExpect(status().isOk());

        // Validate the CategoriaDespesa in the database
        List<CategoriaDespesa> categoriaDespesaList = categoriaDespesaRepository.findAll();
        assertThat(categoriaDespesaList).hasSize(databaseSizeBeforeUpdate);
        CategoriaDespesa testCategoriaDespesa = categoriaDespesaList.get(categoriaDespesaList.size() - 1);
        assertThat(testCategoriaDespesa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCategoriaDespesa.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoriaDespesa() throws Exception {
        int databaseSizeBeforeUpdate = categoriaDespesaRepository.findAll().size();

        // Create the CategoriaDespesa

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaDespesaMockMvc.perform(put("/api/categoria-despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaDespesa)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaDespesa in the database
        List<CategoriaDespesa> categoriaDespesaList = categoriaDespesaRepository.findAll();
        assertThat(categoriaDespesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoriaDespesa() throws Exception {
        // Initialize the database
        categoriaDespesaRepository.saveAndFlush(categoriaDespesa);

        int databaseSizeBeforeDelete = categoriaDespesaRepository.findAll().size();

        // Delete the categoriaDespesa
        restCategoriaDespesaMockMvc.perform(delete("/api/categoria-despesas/{id}", categoriaDespesa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CategoriaDespesa> categoriaDespesaList = categoriaDespesaRepository.findAll();
        assertThat(categoriaDespesaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaDespesa.class);
        CategoriaDespesa categoriaDespesa1 = new CategoriaDespesa();
        categoriaDespesa1.setId(1L);
        CategoriaDespesa categoriaDespesa2 = new CategoriaDespesa();
        categoriaDespesa2.setId(categoriaDespesa1.getId());
        assertThat(categoriaDespesa1).isEqualTo(categoriaDespesa2);
        categoriaDespesa2.setId(2L);
        assertThat(categoriaDespesa1).isNotEqualTo(categoriaDespesa2);
        categoriaDespesa1.setId(null);
        assertThat(categoriaDespesa1).isNotEqualTo(categoriaDespesa2);
    }
}
