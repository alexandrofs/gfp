package com.alexandrofs.gfp.web.rest;

import com.alexandrofs.gfp.GfpApp;

import com.alexandrofs.gfp.domain.Lancamento;
import com.alexandrofs.gfp.domain.ContaPagamento;
import com.alexandrofs.gfp.repository.LancamentoRepository;
import com.alexandrofs.gfp.service.LancamentoService;
import com.alexandrofs.gfp.web.rest.errors.ExceptionTranslator;
import com.alexandrofs.gfp.service.dto.LancamentoCriteria;
import com.alexandrofs.gfp.service.LancamentoQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.alexandrofs.gfp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LancamentoResource REST controller.
 *
 * @see LancamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GfpApp.class)
public class LancamentoResourceIntTest {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private LancamentoQueryService lancamentoQueryService;

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

    private MockMvc restLancamentoMockMvc;

    private Lancamento lancamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LancamentoResource lancamentoResource = new LancamentoResource(lancamentoService, lancamentoQueryService);
        this.restLancamentoMockMvc = MockMvcBuilders.standaloneSetup(lancamentoResource)
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
    public static Lancamento createEntity(EntityManager em) {
        Lancamento lancamento = new Lancamento()
            .data(DEFAULT_DATA)
            .descricao(DEFAULT_DESCRICAO)
            .valor(DEFAULT_VALOR)
            .usuario(DEFAULT_USUARIO);
        // Add required entity
        ContaPagamento contaPagamento = ContaPagamentoResourceIntTest.createEntity(em);
        em.persist(contaPagamento);
        em.flush();
        lancamento.setContaPagamento(contaPagamento);
        return lancamento;
    }

    @Before
    public void initTest() {
        lancamento = createEntity(em);
    }

    @WithMockUser(DEFAULT_USUARIO)
    @Test
    @Transactional
    public void createLancamento() throws Exception {
        int databaseSizeBeforeCreate = lancamentoRepository.findAll().size();

        // Create the Lancamento
        restLancamentoMockMvc.perform(post("/api/lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamento)))
            .andExpect(status().isCreated());

        // Validate the Lancamento in the database
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Lancamento testLancamento = lancamentoList.get(lancamentoList.size() - 1);
        assertThat(testLancamento.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testLancamento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testLancamento.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testLancamento.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    public void createLancamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lancamentoRepository.findAll().size();

        // Create the Lancamento with an existing ID
        lancamento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLancamentoMockMvc.perform(post("/api/lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamento)))
            .andExpect(status().isBadRequest());

        // Validate the Lancamento in the database
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoRepository.findAll().size();
        // set the field null
        lancamento.setData(null);

        // Create the Lancamento, which fails.

        restLancamentoMockMvc.perform(post("/api/lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamento)))
            .andExpect(status().isBadRequest());

        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoRepository.findAll().size();
        // set the field null
        lancamento.setDescricao(null);

        // Create the Lancamento, which fails.

        restLancamentoMockMvc.perform(post("/api/lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamento)))
            .andExpect(status().isBadRequest());

        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoRepository.findAll().size();
        // set the field null
        lancamento.setValor(null);

        // Create the Lancamento, which fails.

        restLancamentoMockMvc.perform(post("/api/lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamento)))
            .andExpect(status().isBadRequest());

        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLancamentos() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList
        restLancamentoMockMvc.perform(get("/api/lancamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lancamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO.toString())));
    }
    
    @Test
    @Transactional
    public void getLancamento() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get the lancamento
        restLancamentoMockMvc.perform(get("/api/lancamentos/{id}", lancamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lancamento.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO.toString()));
    }

    @Test
    @Transactional
    public void getAllLancamentosByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where data equals to DEFAULT_DATA
        defaultLancamentoShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the lancamentoList where data equals to UPDATED_DATA
        defaultLancamentoShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllLancamentosByDataIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where data in DEFAULT_DATA or UPDATED_DATA
        defaultLancamentoShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the lancamentoList where data equals to UPDATED_DATA
        defaultLancamentoShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllLancamentosByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where data is not null
        defaultLancamentoShouldBeFound("data.specified=true");

        // Get all the lancamentoList where data is null
        defaultLancamentoShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllLancamentosByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where data greater than or equals to DEFAULT_DATA
        defaultLancamentoShouldBeFound("data.greaterOrEqualThan=" + DEFAULT_DATA);

        // Get all the lancamentoList where data greater than or equals to UPDATED_DATA
        defaultLancamentoShouldNotBeFound("data.greaterOrEqualThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllLancamentosByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where data less than or equals to DEFAULT_DATA
        defaultLancamentoShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the lancamentoList where data less than or equals to UPDATED_DATA
        defaultLancamentoShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }


    @Test
    @Transactional
    public void getAllLancamentosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where descricao equals to DEFAULT_DESCRICAO
        defaultLancamentoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the lancamentoList where descricao equals to UPDATED_DESCRICAO
        defaultLancamentoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultLancamentoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the lancamentoList where descricao equals to UPDATED_DESCRICAO
        defaultLancamentoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where descricao is not null
        defaultLancamentoShouldBeFound("descricao.specified=true");

        // Get all the lancamentoList where descricao is null
        defaultLancamentoShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    public void getAllLancamentosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where valor equals to DEFAULT_VALOR
        defaultLancamentoShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the lancamentoList where valor equals to UPDATED_VALOR
        defaultLancamentoShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllLancamentosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultLancamentoShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the lancamentoList where valor equals to UPDATED_VALOR
        defaultLancamentoShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllLancamentosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where valor is not null
        defaultLancamentoShouldBeFound("valor.specified=true");

        // Get all the lancamentoList where valor is null
        defaultLancamentoShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllLancamentosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where usuario equals to DEFAULT_USUARIO
        defaultLancamentoShouldBeFound("usuario.equals=" + DEFAULT_USUARIO);

        // Get all the lancamentoList where usuario equals to UPDATED_USUARIO
        defaultLancamentoShouldNotBeFound("usuario.equals=" + UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where usuario in DEFAULT_USUARIO or UPDATED_USUARIO
        defaultLancamentoShouldBeFound("usuario.in=" + DEFAULT_USUARIO + "," + UPDATED_USUARIO);

        // Get all the lancamentoList where usuario equals to UPDATED_USUARIO
        defaultLancamentoShouldNotBeFound("usuario.in=" + UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void getAllLancamentosByUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList where usuario is not null
        defaultLancamentoShouldBeFound("usuario.specified=true");

        // Get all the lancamentoList where usuario is null
        defaultLancamentoShouldNotBeFound("usuario.specified=false");
    }

    @Test
    @Transactional
    public void getAllLancamentosByContaPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        ContaPagamento contaPagamento = ContaPagamentoResourceIntTest.createEntity(em);
        em.persist(contaPagamento);
        em.flush();
        lancamento.setContaPagamento(contaPagamento);
        lancamentoRepository.saveAndFlush(lancamento);
        Long contaPagamentoId = contaPagamento.getId();

        // Get all the lancamentoList where contaPagamento equals to contaPagamentoId
        defaultLancamentoShouldBeFound("contaPagamentoId.equals=" + contaPagamentoId);

        // Get all the lancamentoList where contaPagamento equals to contaPagamentoId + 1
        defaultLancamentoShouldNotBeFound("contaPagamentoId.equals=" + (contaPagamentoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLancamentoShouldBeFound(String filter) throws Exception {
        restLancamentoMockMvc.perform(get("/api/lancamentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lancamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO.toString())));

        // Check, that the count call also returns 1
        restLancamentoMockMvc.perform(get("/api/lancamentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLancamentoShouldNotBeFound(String filter) throws Exception {
        restLancamentoMockMvc.perform(get("/api/lancamentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLancamentoMockMvc.perform(get("/api/lancamentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLancamento() throws Exception {
        // Get the lancamento
        restLancamentoMockMvc.perform(get("/api/lancamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLancamento() throws Exception {
        // Initialize the database
        lancamentoService.save(lancamento);

        int databaseSizeBeforeUpdate = lancamentoRepository.findAll().size();

        // Update the lancamento
        Lancamento updatedLancamento = lancamentoRepository.findById(lancamento.getId()).get();
        // Disconnect from session so that the updates on updatedLancamento are not directly saved in db
        em.detach(updatedLancamento);
        updatedLancamento
            .data(UPDATED_DATA)
            .descricao(UPDATED_DESCRICAO)
            .valor(UPDATED_VALOR)
            .usuario(UPDATED_USUARIO);

        restLancamentoMockMvc.perform(put("/api/lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLancamento)))
            .andExpect(status().isOk());

        // Validate the Lancamento in the database
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeUpdate);
        Lancamento testLancamento = lancamentoList.get(lancamentoList.size() - 1);
        assertThat(testLancamento.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testLancamento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testLancamento.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testLancamento.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingLancamento() throws Exception {
        int databaseSizeBeforeUpdate = lancamentoRepository.findAll().size();

        // Create the Lancamento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLancamentoMockMvc.perform(put("/api/lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamento)))
            .andExpect(status().isBadRequest());

        // Validate the Lancamento in the database
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLancamento() throws Exception {
        // Initialize the database
        lancamentoService.save(lancamento);

        int databaseSizeBeforeDelete = lancamentoRepository.findAll().size();

        // Delete the lancamento
        restLancamentoMockMvc.perform(delete("/api/lancamentos/{id}", lancamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lancamento.class);
        Lancamento lancamento1 = new Lancamento();
        lancamento1.setId(1L);
        Lancamento lancamento2 = new Lancamento();
        lancamento2.setId(lancamento1.getId());
        assertThat(lancamento1).isEqualTo(lancamento2);
        lancamento2.setId(2L);
        assertThat(lancamento1).isNotEqualTo(lancamento2);
        lancamento1.setId(null);
        assertThat(lancamento1).isNotEqualTo(lancamento2);
    }
}
