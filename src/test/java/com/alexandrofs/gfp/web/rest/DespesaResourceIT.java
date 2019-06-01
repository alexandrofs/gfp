package com.alexandrofs.gfp.web.rest;

import com.alexandrofs.gfp.GfpApp;
import com.alexandrofs.gfp.domain.Despesa;
import com.alexandrofs.gfp.domain.ContaPagamento;
import com.alexandrofs.gfp.domain.CategoriaDespesa;
import com.alexandrofs.gfp.repository.DespesaRepository;
import com.alexandrofs.gfp.service.DespesaService;
import com.alexandrofs.gfp.web.rest.errors.ExceptionTranslator;
import com.alexandrofs.gfp.service.dto.DespesaCriteria;
import com.alexandrofs.gfp.service.DespesaQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
 * Integration tests for the {@Link DespesaResource} REST controller.
 */
@SpringBootTest(classes = GfpApp.class)
public class DespesaResourceIT {

    private static final LocalDate DEFAULT_DATA_DESPESA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DESPESA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MES_REFERENCIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MES_REFERENCIA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private DespesaQueryService despesaQueryService;

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

    private MockMvc restDespesaMockMvc;

    private Despesa despesa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DespesaResource despesaResource = new DespesaResource(despesaService, despesaQueryService);
        this.restDespesaMockMvc = MockMvcBuilders.standaloneSetup(despesaResource)
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
    public static Despesa createEntity(EntityManager em) {
        Despesa despesa = new Despesa()
            .dataDespesa(DEFAULT_DATA_DESPESA)
            .mesReferencia(DEFAULT_MES_REFERENCIA)
            .descricao(DEFAULT_DESCRICAO)
            .valor(DEFAULT_VALOR)
            .usuario(DEFAULT_USUARIO);
        // Add required entity
        ContaPagamento contaPagamento;
        if (TestUtil.findAll(em, ContaPagamento.class).isEmpty()) {
            contaPagamento = ContaPagamentoResourceIT.createEntity(em);
            em.persist(contaPagamento);
            em.flush();
        } else {
            contaPagamento = TestUtil.findAll(em, ContaPagamento.class).get(0);
        }
        despesa.setContaPagamento(contaPagamento);
        // Add required entity
        CategoriaDespesa categoriaDespesa;
        if (TestUtil.findAll(em, CategoriaDespesa.class).isEmpty()) {
            categoriaDespesa = CategoriaDespesaResourceIT.createEntity(em);
            em.persist(categoriaDespesa);
            em.flush();
        } else {
            categoriaDespesa = TestUtil.findAll(em, CategoriaDespesa.class).get(0);
        }
        despesa.setCategoriaDespesa(categoriaDespesa);
        return despesa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Despesa createUpdatedEntity(EntityManager em) {
        Despesa despesa = new Despesa()
            .dataDespesa(UPDATED_DATA_DESPESA)
            .mesReferencia(UPDATED_MES_REFERENCIA)
            .descricao(UPDATED_DESCRICAO)
            .valor(UPDATED_VALOR)
            .usuario(UPDATED_USUARIO);
        // Add required entity
        ContaPagamento contaPagamento;
        if (TestUtil.findAll(em, ContaPagamento.class).isEmpty()) {
            contaPagamento = ContaPagamentoResourceIT.createUpdatedEntity(em);
            em.persist(contaPagamento);
            em.flush();
        } else {
            contaPagamento = TestUtil.findAll(em, ContaPagamento.class).get(0);
        }
        despesa.setContaPagamento(contaPagamento);
        // Add required entity
        CategoriaDespesa categoriaDespesa;
        if (TestUtil.findAll(em, CategoriaDespesa.class).isEmpty()) {
            categoriaDespesa = CategoriaDespesaResourceIT.createUpdatedEntity(em);
            em.persist(categoriaDespesa);
            em.flush();
        } else {
            categoriaDespesa = TestUtil.findAll(em, CategoriaDespesa.class).get(0);
        }
        despesa.setCategoriaDespesa(categoriaDespesa);
        return despesa;
    }

    @BeforeEach
    public void initTest() {
        despesa = createEntity(em);
    }

    @Test
    @Transactional
    public void createDespesa() throws Exception {
        int databaseSizeBeforeCreate = despesaRepository.findAll().size();

        // Create the Despesa
        restDespesaMockMvc.perform(post("/api/despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(despesa)))
            .andExpect(status().isCreated());

        // Validate the Despesa in the database
        List<Despesa> despesaList = despesaRepository.findAll();
        assertThat(despesaList).hasSize(databaseSizeBeforeCreate + 1);
        Despesa testDespesa = despesaList.get(despesaList.size() - 1);
        assertThat(testDespesa.getDataDespesa()).isEqualTo(DEFAULT_DATA_DESPESA);
        assertThat(testDespesa.getMesReferencia()).isEqualTo(DEFAULT_MES_REFERENCIA);
        assertThat(testDespesa.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDespesa.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testDespesa.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    public void createDespesaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = despesaRepository.findAll().size();

        // Create the Despesa with an existing ID
        despesa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDespesaMockMvc.perform(post("/api/despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(despesa)))
            .andExpect(status().isBadRequest());

        // Validate the Despesa in the database
        List<Despesa> despesaList = despesaRepository.findAll();
        assertThat(despesaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataDespesaIsRequired() throws Exception {
        int databaseSizeBeforeTest = despesaRepository.findAll().size();
        // set the field null
        despesa.setDataDespesa(null);

        // Create the Despesa, which fails.

        restDespesaMockMvc.perform(post("/api/despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(despesa)))
            .andExpect(status().isBadRequest());

        List<Despesa> despesaList = despesaRepository.findAll();
        assertThat(despesaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMesReferenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = despesaRepository.findAll().size();
        // set the field null
        despesa.setMesReferencia(null);

        // Create the Despesa, which fails.

        restDespesaMockMvc.perform(post("/api/despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(despesa)))
            .andExpect(status().isBadRequest());

        List<Despesa> despesaList = despesaRepository.findAll();
        assertThat(despesaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = despesaRepository.findAll().size();
        // set the field null
        despesa.setDescricao(null);

        // Create the Despesa, which fails.

        restDespesaMockMvc.perform(post("/api/despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(despesa)))
            .andExpect(status().isBadRequest());

        List<Despesa> despesaList = despesaRepository.findAll();
        assertThat(despesaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = despesaRepository.findAll().size();
        // set the field null
        despesa.setValor(null);

        // Create the Despesa, which fails.

        restDespesaMockMvc.perform(post("/api/despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(despesa)))
            .andExpect(status().isBadRequest());

        List<Despesa> despesaList = despesaRepository.findAll();
        assertThat(despesaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = despesaRepository.findAll().size();
        // set the field null
        despesa.setUsuario(null);

        // Create the Despesa, which fails.

        restDespesaMockMvc.perform(post("/api/despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(despesa)))
            .andExpect(status().isBadRequest());

        List<Despesa> despesaList = despesaRepository.findAll();
        assertThat(despesaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDespesas() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList
        restDespesaMockMvc.perform(get("/api/despesas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(despesa.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataDespesa").value(hasItem(DEFAULT_DATA_DESPESA.toString())))
            .andExpect(jsonPath("$.[*].mesReferencia").value(hasItem(DEFAULT_MES_REFERENCIA.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO.toString())));
    }
    
    @Test
    @Transactional
    public void getDespesa() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get the despesa
        restDespesaMockMvc.perform(get("/api/despesas/{id}", despesa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(despesa.getId().intValue()))
            .andExpect(jsonPath("$.dataDespesa").value(DEFAULT_DATA_DESPESA.toString()))
            .andExpect(jsonPath("$.mesReferencia").value(DEFAULT_MES_REFERENCIA.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO.toString()));
    }

    @Test
    @Transactional
    public void getAllDespesasByDataDespesaIsEqualToSomething() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where dataDespesa equals to DEFAULT_DATA_DESPESA
        defaultDespesaShouldBeFound("dataDespesa.equals=" + DEFAULT_DATA_DESPESA);

        // Get all the despesaList where dataDespesa equals to UPDATED_DATA_DESPESA
        defaultDespesaShouldNotBeFound("dataDespesa.equals=" + UPDATED_DATA_DESPESA);
    }

    @Test
    @Transactional
    public void getAllDespesasByDataDespesaIsInShouldWork() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where dataDespesa in DEFAULT_DATA_DESPESA or UPDATED_DATA_DESPESA
        defaultDespesaShouldBeFound("dataDespesa.in=" + DEFAULT_DATA_DESPESA + "," + UPDATED_DATA_DESPESA);

        // Get all the despesaList where dataDespesa equals to UPDATED_DATA_DESPESA
        defaultDespesaShouldNotBeFound("dataDespesa.in=" + UPDATED_DATA_DESPESA);
    }

    @Test
    @Transactional
    public void getAllDespesasByDataDespesaIsNullOrNotNull() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where dataDespesa is not null
        defaultDespesaShouldBeFound("dataDespesa.specified=true");

        // Get all the despesaList where dataDespesa is null
        defaultDespesaShouldNotBeFound("dataDespesa.specified=false");
    }

    @Test
    @Transactional
    public void getAllDespesasByDataDespesaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where dataDespesa greater than or equals to DEFAULT_DATA_DESPESA
        defaultDespesaShouldBeFound("dataDespesa.greaterOrEqualThan=" + DEFAULT_DATA_DESPESA);

        // Get all the despesaList where dataDespesa greater than or equals to UPDATED_DATA_DESPESA
        defaultDespesaShouldNotBeFound("dataDespesa.greaterOrEqualThan=" + UPDATED_DATA_DESPESA);
    }

    @Test
    @Transactional
    public void getAllDespesasByDataDespesaIsLessThanSomething() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where dataDespesa less than or equals to DEFAULT_DATA_DESPESA
        defaultDespesaShouldNotBeFound("dataDespesa.lessThan=" + DEFAULT_DATA_DESPESA);

        // Get all the despesaList where dataDespesa less than or equals to UPDATED_DATA_DESPESA
        defaultDespesaShouldBeFound("dataDespesa.lessThan=" + UPDATED_DATA_DESPESA);
    }


    @Test
    @Transactional
    public void getAllDespesasByMesReferenciaIsEqualToSomething() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where mesReferencia equals to DEFAULT_MES_REFERENCIA
        defaultDespesaShouldBeFound("mesReferencia.equals=" + DEFAULT_MES_REFERENCIA);

        // Get all the despesaList where mesReferencia equals to UPDATED_MES_REFERENCIA
        defaultDespesaShouldNotBeFound("mesReferencia.equals=" + UPDATED_MES_REFERENCIA);
    }

    @Test
    @Transactional
    public void getAllDespesasByMesReferenciaIsInShouldWork() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where mesReferencia in DEFAULT_MES_REFERENCIA or UPDATED_MES_REFERENCIA
        defaultDespesaShouldBeFound("mesReferencia.in=" + DEFAULT_MES_REFERENCIA + "," + UPDATED_MES_REFERENCIA);

        // Get all the despesaList where mesReferencia equals to UPDATED_MES_REFERENCIA
        defaultDespesaShouldNotBeFound("mesReferencia.in=" + UPDATED_MES_REFERENCIA);
    }

    @Test
    @Transactional
    public void getAllDespesasByMesReferenciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where mesReferencia is not null
        defaultDespesaShouldBeFound("mesReferencia.specified=true");

        // Get all the despesaList where mesReferencia is null
        defaultDespesaShouldNotBeFound("mesReferencia.specified=false");
    }

    @Test
    @Transactional
    public void getAllDespesasByMesReferenciaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where mesReferencia greater than or equals to DEFAULT_MES_REFERENCIA
        defaultDespesaShouldBeFound("mesReferencia.greaterOrEqualThan=" + DEFAULT_MES_REFERENCIA);

        // Get all the despesaList where mesReferencia greater than or equals to UPDATED_MES_REFERENCIA
        defaultDespesaShouldNotBeFound("mesReferencia.greaterOrEqualThan=" + UPDATED_MES_REFERENCIA);
    }

    @Test
    @Transactional
    public void getAllDespesasByMesReferenciaIsLessThanSomething() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where mesReferencia less than or equals to DEFAULT_MES_REFERENCIA
        defaultDespesaShouldNotBeFound("mesReferencia.lessThan=" + DEFAULT_MES_REFERENCIA);

        // Get all the despesaList where mesReferencia less than or equals to UPDATED_MES_REFERENCIA
        defaultDespesaShouldBeFound("mesReferencia.lessThan=" + UPDATED_MES_REFERENCIA);
    }


    @Test
    @Transactional
    public void getAllDespesasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where descricao equals to DEFAULT_DESCRICAO
        defaultDespesaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the despesaList where descricao equals to UPDATED_DESCRICAO
        defaultDespesaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllDespesasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultDespesaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the despesaList where descricao equals to UPDATED_DESCRICAO
        defaultDespesaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllDespesasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where descricao is not null
        defaultDespesaShouldBeFound("descricao.specified=true");

        // Get all the despesaList where descricao is null
        defaultDespesaShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    public void getAllDespesasByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where valor equals to DEFAULT_VALOR
        defaultDespesaShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the despesaList where valor equals to UPDATED_VALOR
        defaultDespesaShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDespesasByValorIsInShouldWork() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultDespesaShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the despesaList where valor equals to UPDATED_VALOR
        defaultDespesaShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDespesasByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where valor is not null
        defaultDespesaShouldBeFound("valor.specified=true");

        // Get all the despesaList where valor is null
        defaultDespesaShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllDespesasByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where usuario equals to DEFAULT_USUARIO
        defaultDespesaShouldBeFound("usuario.equals=" + DEFAULT_USUARIO);

        // Get all the despesaList where usuario equals to UPDATED_USUARIO
        defaultDespesaShouldNotBeFound("usuario.equals=" + UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void getAllDespesasByUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where usuario in DEFAULT_USUARIO or UPDATED_USUARIO
        defaultDespesaShouldBeFound("usuario.in=" + DEFAULT_USUARIO + "," + UPDATED_USUARIO);

        // Get all the despesaList where usuario equals to UPDATED_USUARIO
        defaultDespesaShouldNotBeFound("usuario.in=" + UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void getAllDespesasByUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        despesaRepository.saveAndFlush(despesa);

        // Get all the despesaList where usuario is not null
        defaultDespesaShouldBeFound("usuario.specified=true");

        // Get all the despesaList where usuario is null
        defaultDespesaShouldNotBeFound("usuario.specified=false");
    }

    @Test
    @Transactional
    public void getAllDespesasByContaPagamentoIsEqualToSomething() throws Exception {
        // Get already existing entity
        ContaPagamento contaPagamento = despesa.getContaPagamento();
        despesaRepository.saveAndFlush(despesa);
        Long contaPagamentoId = contaPagamento.getId();

        // Get all the despesaList where contaPagamento equals to contaPagamentoId
        defaultDespesaShouldBeFound("contaPagamentoId.equals=" + contaPagamentoId);

        // Get all the despesaList where contaPagamento equals to contaPagamentoId + 1
        defaultDespesaShouldNotBeFound("contaPagamentoId.equals=" + (contaPagamentoId + 1));
    }


    @Test
    @Transactional
    public void getAllDespesasByCategoriaDespesaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CategoriaDespesa categoriaDespesa = despesa.getCategoriaDespesa();
        despesaRepository.saveAndFlush(despesa);
        Long categoriaDespesaId = categoriaDespesa.getId();

        // Get all the despesaList where categoriaDespesa equals to categoriaDespesaId
        defaultDespesaShouldBeFound("categoriaDespesaId.equals=" + categoriaDespesaId);

        // Get all the despesaList where categoriaDespesa equals to categoriaDespesaId + 1
        defaultDespesaShouldNotBeFound("categoriaDespesaId.equals=" + (categoriaDespesaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDespesaShouldBeFound(String filter) throws Exception {
        restDespesaMockMvc.perform(get("/api/despesas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(despesa.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataDespesa").value(hasItem(DEFAULT_DATA_DESPESA.toString())))
            .andExpect(jsonPath("$.[*].mesReferencia").value(hasItem(DEFAULT_MES_REFERENCIA.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)));

        // Check, that the count call also returns 1
        restDespesaMockMvc.perform(get("/api/despesas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDespesaShouldNotBeFound(String filter) throws Exception {
        restDespesaMockMvc.perform(get("/api/despesas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDespesaMockMvc.perform(get("/api/despesas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDespesa() throws Exception {
        // Get the despesa
        restDespesaMockMvc.perform(get("/api/despesas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDespesa() throws Exception {
        // Initialize the database
        despesaService.save(despesa);

        int databaseSizeBeforeUpdate = despesaRepository.findAll().size();

        // Update the despesa
        Despesa updatedDespesa = despesaRepository.findById(despesa.getId()).get();
        // Disconnect from session so that the updates on updatedDespesa are not directly saved in db
        em.detach(updatedDespesa);
        updatedDespesa
            .dataDespesa(UPDATED_DATA_DESPESA)
            .mesReferencia(UPDATED_MES_REFERENCIA)
            .descricao(UPDATED_DESCRICAO)
            .valor(UPDATED_VALOR)
            .usuario(UPDATED_USUARIO);

        restDespesaMockMvc.perform(put("/api/despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDespesa)))
            .andExpect(status().isOk());

        // Validate the Despesa in the database
        List<Despesa> despesaList = despesaRepository.findAll();
        assertThat(despesaList).hasSize(databaseSizeBeforeUpdate);
        Despesa testDespesa = despesaList.get(despesaList.size() - 1);
        assertThat(testDespesa.getDataDespesa()).isEqualTo(UPDATED_DATA_DESPESA);
        assertThat(testDespesa.getMesReferencia()).isEqualTo(UPDATED_MES_REFERENCIA);
        assertThat(testDespesa.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDespesa.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testDespesa.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingDespesa() throws Exception {
        int databaseSizeBeforeUpdate = despesaRepository.findAll().size();

        // Create the Despesa

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDespesaMockMvc.perform(put("/api/despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(despesa)))
            .andExpect(status().isBadRequest());

        // Validate the Despesa in the database
        List<Despesa> despesaList = despesaRepository.findAll();
        assertThat(despesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDespesa() throws Exception {
        // Initialize the database
        despesaService.save(despesa);

        int databaseSizeBeforeDelete = despesaRepository.findAll().size();

        // Delete the despesa
        restDespesaMockMvc.perform(delete("/api/despesas/{id}", despesa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Despesa> despesaList = despesaRepository.findAll();
        assertThat(despesaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Despesa.class);
        Despesa despesa1 = new Despesa();
        despesa1.setId(1L);
        Despesa despesa2 = new Despesa();
        despesa2.setId(despesa1.getId());
        assertThat(despesa1).isEqualTo(despesa2);
        despesa2.setId(2L);
        assertThat(despesa1).isNotEqualTo(despesa2);
        despesa1.setId(null);
        assertThat(despesa1).isNotEqualTo(despesa2);
    }
}
