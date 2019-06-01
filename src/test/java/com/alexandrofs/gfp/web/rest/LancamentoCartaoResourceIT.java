package com.alexandrofs.gfp.web.rest;

import com.alexandrofs.gfp.GfpApp;
import com.alexandrofs.gfp.domain.LancamentoCartao;
import com.alexandrofs.gfp.domain.ContaPagamento;
import com.alexandrofs.gfp.repository.LancamentoCartaoRepository;
import com.alexandrofs.gfp.web.rest.errors.ExceptionTranslator;

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
 * Integration tests for the {@Link LancamentoCartaoResource} REST controller.
 */
@SpringBootTest(classes = GfpApp.class)
public class LancamentoCartaoResourceIT {

    private static final LocalDate DEFAULT_DATA_COMPRA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_COMPRA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MES_FATURA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MES_FATURA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    @Autowired
    private LancamentoCartaoRepository lancamentoCartaoRepository;

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

    private MockMvc restLancamentoCartaoMockMvc;

    private LancamentoCartao lancamentoCartao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LancamentoCartaoResource lancamentoCartaoResource = new LancamentoCartaoResource(lancamentoCartaoRepository);
        this.restLancamentoCartaoMockMvc = MockMvcBuilders.standaloneSetup(lancamentoCartaoResource)
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
    public static LancamentoCartao createEntity(EntityManager em) {
        LancamentoCartao lancamentoCartao = new LancamentoCartao()
            .dataCompra(DEFAULT_DATA_COMPRA)
            .mesFatura(DEFAULT_MES_FATURA)
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
        lancamentoCartao.setContaPagamento(contaPagamento);
        return lancamentoCartao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LancamentoCartao createUpdatedEntity(EntityManager em) {
        LancamentoCartao lancamentoCartao = new LancamentoCartao()
            .dataCompra(UPDATED_DATA_COMPRA)
            .mesFatura(UPDATED_MES_FATURA)
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
        lancamentoCartao.setContaPagamento(contaPagamento);
        return lancamentoCartao;
    }

    @BeforeEach
    public void initTest() {
        lancamentoCartao = createEntity(em);
    }

    @Test
    @Transactional
    public void createLancamentoCartao() throws Exception {
        int databaseSizeBeforeCreate = lancamentoCartaoRepository.findAll().size();

        // Create the LancamentoCartao
        restLancamentoCartaoMockMvc.perform(post("/api/lancamento-cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoCartao)))
            .andExpect(status().isCreated());

        // Validate the LancamentoCartao in the database
        List<LancamentoCartao> lancamentoCartaoList = lancamentoCartaoRepository.findAll();
        assertThat(lancamentoCartaoList).hasSize(databaseSizeBeforeCreate + 1);
        LancamentoCartao testLancamentoCartao = lancamentoCartaoList.get(lancamentoCartaoList.size() - 1);
        assertThat(testLancamentoCartao.getDataCompra()).isEqualTo(DEFAULT_DATA_COMPRA);
        assertThat(testLancamentoCartao.getMesFatura()).isEqualTo(DEFAULT_MES_FATURA);
        assertThat(testLancamentoCartao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testLancamentoCartao.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testLancamentoCartao.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    public void createLancamentoCartaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lancamentoCartaoRepository.findAll().size();

        // Create the LancamentoCartao with an existing ID
        lancamentoCartao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLancamentoCartaoMockMvc.perform(post("/api/lancamento-cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoCartao)))
            .andExpect(status().isBadRequest());

        // Validate the LancamentoCartao in the database
        List<LancamentoCartao> lancamentoCartaoList = lancamentoCartaoRepository.findAll();
        assertThat(lancamentoCartaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataCompraIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoCartaoRepository.findAll().size();
        // set the field null
        lancamentoCartao.setDataCompra(null);

        // Create the LancamentoCartao, which fails.

        restLancamentoCartaoMockMvc.perform(post("/api/lancamento-cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoCartao)))
            .andExpect(status().isBadRequest());

        List<LancamentoCartao> lancamentoCartaoList = lancamentoCartaoRepository.findAll();
        assertThat(lancamentoCartaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMesFaturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoCartaoRepository.findAll().size();
        // set the field null
        lancamentoCartao.setMesFatura(null);

        // Create the LancamentoCartao, which fails.

        restLancamentoCartaoMockMvc.perform(post("/api/lancamento-cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoCartao)))
            .andExpect(status().isBadRequest());

        List<LancamentoCartao> lancamentoCartaoList = lancamentoCartaoRepository.findAll();
        assertThat(lancamentoCartaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoCartaoRepository.findAll().size();
        // set the field null
        lancamentoCartao.setDescricao(null);

        // Create the LancamentoCartao, which fails.

        restLancamentoCartaoMockMvc.perform(post("/api/lancamento-cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoCartao)))
            .andExpect(status().isBadRequest());

        List<LancamentoCartao> lancamentoCartaoList = lancamentoCartaoRepository.findAll();
        assertThat(lancamentoCartaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoCartaoRepository.findAll().size();
        // set the field null
        lancamentoCartao.setValor(null);

        // Create the LancamentoCartao, which fails.

        restLancamentoCartaoMockMvc.perform(post("/api/lancamento-cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoCartao)))
            .andExpect(status().isBadRequest());

        List<LancamentoCartao> lancamentoCartaoList = lancamentoCartaoRepository.findAll();
        assertThat(lancamentoCartaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoCartaoRepository.findAll().size();
        // set the field null
        lancamentoCartao.setUsuario(null);

        // Create the LancamentoCartao, which fails.

        restLancamentoCartaoMockMvc.perform(post("/api/lancamento-cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoCartao)))
            .andExpect(status().isBadRequest());

        List<LancamentoCartao> lancamentoCartaoList = lancamentoCartaoRepository.findAll();
        assertThat(lancamentoCartaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLancamentoCartaos() throws Exception {
        // Initialize the database
        lancamentoCartaoRepository.saveAndFlush(lancamentoCartao);

        // Get all the lancamentoCartaoList
        restLancamentoCartaoMockMvc.perform(get("/api/lancamento-cartaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lancamentoCartao.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataCompra").value(hasItem(DEFAULT_DATA_COMPRA.toString())))
            .andExpect(jsonPath("$.[*].mesFatura").value(hasItem(DEFAULT_MES_FATURA.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO.toString())));
    }
    
    @Test
    @Transactional
    public void getLancamentoCartao() throws Exception {
        // Initialize the database
        lancamentoCartaoRepository.saveAndFlush(lancamentoCartao);

        // Get the lancamentoCartao
        restLancamentoCartaoMockMvc.perform(get("/api/lancamento-cartaos/{id}", lancamentoCartao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lancamentoCartao.getId().intValue()))
            .andExpect(jsonPath("$.dataCompra").value(DEFAULT_DATA_COMPRA.toString()))
            .andExpect(jsonPath("$.mesFatura").value(DEFAULT_MES_FATURA.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLancamentoCartao() throws Exception {
        // Get the lancamentoCartao
        restLancamentoCartaoMockMvc.perform(get("/api/lancamento-cartaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLancamentoCartao() throws Exception {
        // Initialize the database
        lancamentoCartaoRepository.saveAndFlush(lancamentoCartao);

        int databaseSizeBeforeUpdate = lancamentoCartaoRepository.findAll().size();

        // Update the lancamentoCartao
        LancamentoCartao updatedLancamentoCartao = lancamentoCartaoRepository.findById(lancamentoCartao.getId()).get();
        // Disconnect from session so that the updates on updatedLancamentoCartao are not directly saved in db
        em.detach(updatedLancamentoCartao);
        updatedLancamentoCartao
            .dataCompra(UPDATED_DATA_COMPRA)
            .mesFatura(UPDATED_MES_FATURA)
            .descricao(UPDATED_DESCRICAO)
            .valor(UPDATED_VALOR)
            .usuario(UPDATED_USUARIO);

        restLancamentoCartaoMockMvc.perform(put("/api/lancamento-cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLancamentoCartao)))
            .andExpect(status().isOk());

        // Validate the LancamentoCartao in the database
        List<LancamentoCartao> lancamentoCartaoList = lancamentoCartaoRepository.findAll();
        assertThat(lancamentoCartaoList).hasSize(databaseSizeBeforeUpdate);
        LancamentoCartao testLancamentoCartao = lancamentoCartaoList.get(lancamentoCartaoList.size() - 1);
        assertThat(testLancamentoCartao.getDataCompra()).isEqualTo(UPDATED_DATA_COMPRA);
        assertThat(testLancamentoCartao.getMesFatura()).isEqualTo(UPDATED_MES_FATURA);
        assertThat(testLancamentoCartao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testLancamentoCartao.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testLancamentoCartao.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingLancamentoCartao() throws Exception {
        int databaseSizeBeforeUpdate = lancamentoCartaoRepository.findAll().size();

        // Create the LancamentoCartao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLancamentoCartaoMockMvc.perform(put("/api/lancamento-cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoCartao)))
            .andExpect(status().isBadRequest());

        // Validate the LancamentoCartao in the database
        List<LancamentoCartao> lancamentoCartaoList = lancamentoCartaoRepository.findAll();
        assertThat(lancamentoCartaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLancamentoCartao() throws Exception {
        // Initialize the database
        lancamentoCartaoRepository.saveAndFlush(lancamentoCartao);

        int databaseSizeBeforeDelete = lancamentoCartaoRepository.findAll().size();

        // Delete the lancamentoCartao
        restLancamentoCartaoMockMvc.perform(delete("/api/lancamento-cartaos/{id}", lancamentoCartao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<LancamentoCartao> lancamentoCartaoList = lancamentoCartaoRepository.findAll();
        assertThat(lancamentoCartaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LancamentoCartao.class);
        LancamentoCartao lancamentoCartao1 = new LancamentoCartao();
        lancamentoCartao1.setId(1L);
        LancamentoCartao lancamentoCartao2 = new LancamentoCartao();
        lancamentoCartao2.setId(lancamentoCartao1.getId());
        assertThat(lancamentoCartao1).isEqualTo(lancamentoCartao2);
        lancamentoCartao2.setId(2L);
        assertThat(lancamentoCartao1).isNotEqualTo(lancamentoCartao2);
        lancamentoCartao1.setId(null);
        assertThat(lancamentoCartao1).isNotEqualTo(lancamentoCartao2);
    }
}
