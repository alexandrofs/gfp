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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import com.alexandrofs.gfp.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import com.alexandrofs.gfp.GfpApp;
import com.alexandrofs.gfp.domain.HistoricoCotas;
import java.math.BigDecimal;
import com.alexandrofs.gfp.domain.Investimento;
import com.alexandrofs.gfp.repository.HistoricoCotasRepository;

import static com.alexandrofs.gfp.web.rest.TestUtil.createFormattingConversionService;

/**
 * Test class for the HistoricoCotasResource REST controller.
 *
 * @see HistoricoCotasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GfpApp.class)
@Transactional
public class HistoricoCotasResourceIntTest {

    private static final LocalDate DEFAULT_DATA_COTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_COTA = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_VLR_COTA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VLR_COTA = new BigDecimal(2);

    @Autowired
    private HistoricoCotasRepository historicoCotasRepository;

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

    private MockMvc restHistoricoCotasMockMvc;

    private HistoricoCotas historicoCotas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HistoricoCotasResource historicoCotasResource = new HistoricoCotasResource(historicoCotasRepository);
        this.restHistoricoCotasMockMvc = MockMvcBuilders.standaloneSetup(historicoCotasResource)
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
    public static HistoricoCotas createEntity(EntityManager em) {
        HistoricoCotas historicoCotas = new HistoricoCotas();
        historicoCotas.setDataCota(DEFAULT_DATA_COTA);
        historicoCotas.setVlrCota(DEFAULT_VLR_COTA);
        // Add required entity
        Investimento investimento = InvestimentoResourceIntTest.createEntity(em);
        em.persist(investimento);
        em.flush();
        historicoCotas.setInvestimento(investimento);
        return historicoCotas;
    }

    @Before
    public void initTest() {
        historicoCotas = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistoricoCotas() throws Exception {
        int databaseSizeBeforeCreate = historicoCotasRepository.findAll().size();

        // Create the HistoricoCotas
        restHistoricoCotasMockMvc.perform(post("/api/historico-cotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historicoCotas)))
            .andExpect(status().isCreated());

        // Validate the HistoricoCotas in the database
        List<HistoricoCotas> historicoCotasList = historicoCotasRepository.findAll();
        assertThat(historicoCotasList).hasSize(databaseSizeBeforeCreate + 1);
        HistoricoCotas testHistoricoCotas = historicoCotasList.get(historicoCotasList.size() - 1);
        assertThat(testHistoricoCotas.getDataCota()).isEqualTo(DEFAULT_DATA_COTA);
        assertThat(testHistoricoCotas.getVlrCota()).isEqualTo(DEFAULT_VLR_COTA);
    }

    @Test
    @Transactional
    public void createHistoricoCotasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historicoCotasRepository.findAll().size();

        // Create the HistoricoCotas with an existing ID
        historicoCotas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoricoCotasMockMvc.perform(post("/api/historico-cotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historicoCotas)))
            .andExpect(status().isBadRequest());

        // Validate the HistoricoCotas in the database
        List<HistoricoCotas> historicoCotasList = historicoCotasRepository.findAll();
        assertThat(historicoCotasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDataCotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = historicoCotasRepository.findAll().size();
        // set the field null
        historicoCotas.setDataCota(null);

        // Create the HistoricoCotas, which fails.

        restHistoricoCotasMockMvc.perform(post("/api/historico-cotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historicoCotas)))
            .andExpect(status().isBadRequest());

        List<HistoricoCotas> historicoCotasList = historicoCotasRepository.findAll();
        assertThat(historicoCotasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVlrCotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = historicoCotasRepository.findAll().size();
        // set the field null
        historicoCotas.setVlrCota(null);

        // Create the HistoricoCotas, which fails.

        restHistoricoCotasMockMvc.perform(post("/api/historico-cotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historicoCotas)))
            .andExpect(status().isBadRequest());

        List<HistoricoCotas> historicoCotasList = historicoCotasRepository.findAll();
        assertThat(historicoCotasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHistoricoCotas() throws Exception {
        // Initialize the database
        historicoCotasRepository.saveAndFlush(historicoCotas);

        // Get all the historicoCotasList
        restHistoricoCotasMockMvc.perform(get("/api/historico-cotas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historicoCotas.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataCota").value(hasItem(DEFAULT_DATA_COTA.toString())))
            .andExpect(jsonPath("$.[*].vlrCota").value(hasItem(DEFAULT_VLR_COTA.intValue())));
    }
    
    @Test
    @Transactional
    public void getHistoricoCotas() throws Exception {
        // Initialize the database
        historicoCotasRepository.saveAndFlush(historicoCotas);

        // Get the historicoCotas
        restHistoricoCotasMockMvc.perform(get("/api/historico-cotas/{id}", historicoCotas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(historicoCotas.getId().intValue()))
            .andExpect(jsonPath("$.dataCota").value(DEFAULT_DATA_COTA.toString()))
            .andExpect(jsonPath("$.vlrCota").value(DEFAULT_VLR_COTA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHistoricoCotas() throws Exception {
        // Get the historicoCotas
        restHistoricoCotasMockMvc.perform(get("/api/historico-cotas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistoricoCotas() throws Exception {
        // Initialize the database
        historicoCotasRepository.saveAndFlush(historicoCotas);

        int databaseSizeBeforeUpdate = historicoCotasRepository.findAll().size();

        // Update the historicoCotas
        HistoricoCotas updatedHistoricoCotas = historicoCotasRepository.findById(historicoCotas.getId()).get();
        // Disconnect from session so that the updates on updatedHistoricoCotas are not directly saved in db
        em.detach(updatedHistoricoCotas);
        updatedHistoricoCotas.setDataCota(UPDATED_DATA_COTA);
        updatedHistoricoCotas.setVlrCota(UPDATED_VLR_COTA);

        restHistoricoCotasMockMvc.perform(put("/api/historico-cotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHistoricoCotas)))
            .andExpect(status().isOk());

        // Validate the HistoricoCotas in the database
        List<HistoricoCotas> historicoCotasList = historicoCotasRepository.findAll();
        assertThat(historicoCotasList).hasSize(databaseSizeBeforeUpdate);
        HistoricoCotas testHistoricoCotas = historicoCotasList.get(historicoCotasList.size() - 1);
        assertThat(testHistoricoCotas.getDataCota()).isEqualTo(UPDATED_DATA_COTA);
        assertThat(testHistoricoCotas.getVlrCota()).isEqualTo(UPDATED_VLR_COTA);
    }

    @Test
    @Transactional
    public void updateNonExistingHistoricoCotas() throws Exception {
        int databaseSizeBeforeUpdate = historicoCotasRepository.findAll().size();

        // Create the HistoricoCotas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoricoCotasMockMvc.perform(put("/api/historico-cotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historicoCotas)))
            .andExpect(status().isBadRequest());

        // Validate the HistoricoCotas in the database
        List<HistoricoCotas> historicoCotasList = historicoCotasRepository.findAll();
        assertThat(historicoCotasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHistoricoCotas() throws Exception {
        // Initialize the database
        historicoCotasRepository.saveAndFlush(historicoCotas);

        int databaseSizeBeforeDelete = historicoCotasRepository.findAll().size();

        // Delete the historicoCotas
        restHistoricoCotasMockMvc.perform(delete("/api/historico-cotas/{id}", historicoCotas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HistoricoCotas> historicoCotasList = historicoCotasRepository.findAll();
        assertThat(historicoCotasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoricoCotas.class);
        HistoricoCotas historicoCotas1 = new HistoricoCotas();
        historicoCotas1.setId(1L);
        HistoricoCotas historicoCotas2 = new HistoricoCotas();
        historicoCotas2.setId(historicoCotas1.getId());
        assertThat(historicoCotas1).isEqualTo(historicoCotas2);
        historicoCotas2.setId(2L);
        assertThat(historicoCotas1).isNotEqualTo(historicoCotas2);
        historicoCotas1.setId(null);
        assertThat(historicoCotas1).isNotEqualTo(historicoCotas2);
    }
}
