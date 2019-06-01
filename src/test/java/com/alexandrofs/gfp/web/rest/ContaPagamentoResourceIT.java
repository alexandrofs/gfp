package com.alexandrofs.gfp.web.rest;

import com.alexandrofs.gfp.GfpApp;
import com.alexandrofs.gfp.domain.ContaPagamento;
import com.alexandrofs.gfp.repository.ContaPagamentoRepository;
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
import java.util.List;

import static com.alexandrofs.gfp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.alexandrofs.gfp.domain.enumeration.TipoConta;
/**
 * Integration tests for the {@Link ContaPagamentoResource} REST controller.
 */
@SpringBootTest(classes = GfpApp.class)
public class ContaPagamentoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final TipoConta DEFAULT_TIPO_CONTA = TipoConta.CARTAO_CREDITO;
    private static final TipoConta UPDATED_TIPO_CONTA = TipoConta.BANCO;

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    @Autowired
    private ContaPagamentoRepository contaPagamentoRepository;

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

    private MockMvc restContaPagamentoMockMvc;

    private ContaPagamento contaPagamento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContaPagamentoResource contaPagamentoResource = new ContaPagamentoResource(contaPagamentoRepository);
        this.restContaPagamentoMockMvc = MockMvcBuilders.standaloneSetup(contaPagamentoResource)
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
    public static ContaPagamento createEntity(EntityManager em) {
        ContaPagamento contaPagamento = new ContaPagamento()
            .nome(DEFAULT_NOME)
            .tipoConta(DEFAULT_TIPO_CONTA)
            .usuario(DEFAULT_USUARIO);
        return contaPagamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContaPagamento createUpdatedEntity(EntityManager em) {
        ContaPagamento contaPagamento = new ContaPagamento()
            .nome(UPDATED_NOME)
            .tipoConta(UPDATED_TIPO_CONTA)
            .usuario(UPDATED_USUARIO);
        return contaPagamento;
    }

    @BeforeEach
    public void initTest() {
        contaPagamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createContaPagamento() throws Exception {
        int databaseSizeBeforeCreate = contaPagamentoRepository.findAll().size();

        // Create the ContaPagamento
        restContaPagamentoMockMvc.perform(post("/api/conta-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaPagamento)))
            .andExpect(status().isCreated());

        // Validate the ContaPagamento in the database
        List<ContaPagamento> contaPagamentoList = contaPagamentoRepository.findAll();
        assertThat(contaPagamentoList).hasSize(databaseSizeBeforeCreate + 1);
        ContaPagamento testContaPagamento = contaPagamentoList.get(contaPagamentoList.size() - 1);
        assertThat(testContaPagamento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testContaPagamento.getTipoConta()).isEqualTo(DEFAULT_TIPO_CONTA);
        assertThat(testContaPagamento.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    public void createContaPagamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contaPagamentoRepository.findAll().size();

        // Create the ContaPagamento with an existing ID
        contaPagamento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContaPagamentoMockMvc.perform(post("/api/conta-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaPagamento)))
            .andExpect(status().isBadRequest());

        // Validate the ContaPagamento in the database
        List<ContaPagamento> contaPagamentoList = contaPagamentoRepository.findAll();
        assertThat(contaPagamentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contaPagamentoRepository.findAll().size();
        // set the field null
        contaPagamento.setNome(null);

        // Create the ContaPagamento, which fails.

        restContaPagamentoMockMvc.perform(post("/api/conta-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaPagamento)))
            .andExpect(status().isBadRequest());

        List<ContaPagamento> contaPagamentoList = contaPagamentoRepository.findAll();
        assertThat(contaPagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoContaIsRequired() throws Exception {
        int databaseSizeBeforeTest = contaPagamentoRepository.findAll().size();
        // set the field null
        contaPagamento.setTipoConta(null);

        // Create the ContaPagamento, which fails.

        restContaPagamentoMockMvc.perform(post("/api/conta-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaPagamento)))
            .andExpect(status().isBadRequest());

        List<ContaPagamento> contaPagamentoList = contaPagamentoRepository.findAll();
        assertThat(contaPagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = contaPagamentoRepository.findAll().size();
        // set the field null
        contaPagamento.setUsuario(null);

        // Create the ContaPagamento, which fails.

        restContaPagamentoMockMvc.perform(post("/api/conta-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaPagamento)))
            .andExpect(status().isBadRequest());

        List<ContaPagamento> contaPagamentoList = contaPagamentoRepository.findAll();
        assertThat(contaPagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContaPagamentos() throws Exception {
        // Initialize the database
        contaPagamentoRepository.saveAndFlush(contaPagamento);

        // Get all the contaPagamentoList
        restContaPagamentoMockMvc.perform(get("/api/conta-pagamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contaPagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].tipoConta").value(hasItem(DEFAULT_TIPO_CONTA.toString())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO.toString())));
    }
    
    @Test
    @Transactional
    public void getContaPagamento() throws Exception {
        // Initialize the database
        contaPagamentoRepository.saveAndFlush(contaPagamento);

        // Get the contaPagamento
        restContaPagamentoMockMvc.perform(get("/api/conta-pagamentos/{id}", contaPagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contaPagamento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.tipoConta").value(DEFAULT_TIPO_CONTA.toString()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContaPagamento() throws Exception {
        // Get the contaPagamento
        restContaPagamentoMockMvc.perform(get("/api/conta-pagamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContaPagamento() throws Exception {
        // Initialize the database
        contaPagamentoRepository.saveAndFlush(contaPagamento);

        int databaseSizeBeforeUpdate = contaPagamentoRepository.findAll().size();

        // Update the contaPagamento
        ContaPagamento updatedContaPagamento = contaPagamentoRepository.findById(contaPagamento.getId()).get();
        // Disconnect from session so that the updates on updatedContaPagamento are not directly saved in db
        em.detach(updatedContaPagamento);
        updatedContaPagamento
            .nome(UPDATED_NOME)
            .tipoConta(UPDATED_TIPO_CONTA)
            .usuario(UPDATED_USUARIO);

        restContaPagamentoMockMvc.perform(put("/api/conta-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContaPagamento)))
            .andExpect(status().isOk());

        // Validate the ContaPagamento in the database
        List<ContaPagamento> contaPagamentoList = contaPagamentoRepository.findAll();
        assertThat(contaPagamentoList).hasSize(databaseSizeBeforeUpdate);
        ContaPagamento testContaPagamento = contaPagamentoList.get(contaPagamentoList.size() - 1);
        assertThat(testContaPagamento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testContaPagamento.getTipoConta()).isEqualTo(UPDATED_TIPO_CONTA);
        assertThat(testContaPagamento.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingContaPagamento() throws Exception {
        int databaseSizeBeforeUpdate = contaPagamentoRepository.findAll().size();

        // Create the ContaPagamento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContaPagamentoMockMvc.perform(put("/api/conta-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaPagamento)))
            .andExpect(status().isBadRequest());

        // Validate the ContaPagamento in the database
        List<ContaPagamento> contaPagamentoList = contaPagamentoRepository.findAll();
        assertThat(contaPagamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContaPagamento() throws Exception {
        // Initialize the database
        contaPagamentoRepository.saveAndFlush(contaPagamento);

        int databaseSizeBeforeDelete = contaPagamentoRepository.findAll().size();

        // Delete the contaPagamento
        restContaPagamentoMockMvc.perform(delete("/api/conta-pagamentos/{id}", contaPagamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ContaPagamento> contaPagamentoList = contaPagamentoRepository.findAll();
        assertThat(contaPagamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContaPagamento.class);
        ContaPagamento contaPagamento1 = new ContaPagamento();
        contaPagamento1.setId(1L);
        ContaPagamento contaPagamento2 = new ContaPagamento();
        contaPagamento2.setId(contaPagamento1.getId());
        assertThat(contaPagamento1).isEqualTo(contaPagamento2);
        contaPagamento2.setId(2L);
        assertThat(contaPagamento1).isNotEqualTo(contaPagamento2);
        contaPagamento1.setId(null);
        assertThat(contaPagamento1).isNotEqualTo(contaPagamento2);
    }
}
