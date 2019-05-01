package com.alexandrofs.gfp.web.rest;

import com.alexandrofs.gfp.GfpApp;
import com.alexandrofs.gfp.domain.IndiceSerieDi;
import com.alexandrofs.gfp.repository.IndiceSerieDiRepository;
import com.alexandrofs.gfp.service.IndiceSerieDiService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the IndiceSerieDiResource REST controller.
 *
 * @see IndiceSerieDiResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GfpApp.class)
@WebAppConfiguration
@IntegrationTest
public class IndiceSerieDiResourceIntTest {


    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_TAXA_MEDIA_ANUAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAXA_MEDIA_ANUAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAXA_SELIC = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAXA_SELIC = new BigDecimal(2);

    private static final BigDecimal DEFAULT_FATOR_DIARIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_FATOR_DIARIO = new BigDecimal(2);

    @Inject
    private IndiceSerieDiRepository indiceSerieDiRepository;

    @Inject
    private IndiceSerieDiService indiceSerieDiService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restIndiceSerieDiMockMvc;

    private IndiceSerieDi indiceSerieDi;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IndiceSerieDiResource indiceSerieDiResource = new IndiceSerieDiResource();
        ReflectionTestUtils.setField(indiceSerieDiResource, "indiceSerieDiService", indiceSerieDiService);
        this.restIndiceSerieDiMockMvc = MockMvcBuilders.standaloneSetup(indiceSerieDiResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        indiceSerieDi = new IndiceSerieDi();
        indiceSerieDi.setData(DEFAULT_DATA);
        indiceSerieDi.setTaxaMediaAnual(DEFAULT_TAXA_MEDIA_ANUAL);
        indiceSerieDi.setTaxaSelic(DEFAULT_TAXA_SELIC);
        indiceSerieDi.setFatorDiario(DEFAULT_FATOR_DIARIO);
    }

    @Test
    @Transactional
    public void createIndiceSerieDi() throws Exception {
        int databaseSizeBeforeCreate = indiceSerieDiRepository.findAll().size();

        // Create the IndiceSerieDi

        restIndiceSerieDiMockMvc.perform(post("/api/indice-serie-dis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(indiceSerieDi)))
                .andExpect(status().isCreated());

        // Validate the IndiceSerieDi in the database
        List<IndiceSerieDi> indiceSerieDis = indiceSerieDiRepository.findAll();
        assertThat(indiceSerieDis).hasSize(databaseSizeBeforeCreate + 1);
        IndiceSerieDi testIndiceSerieDi = indiceSerieDis.get(indiceSerieDis.size() - 1);
        assertThat(testIndiceSerieDi.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testIndiceSerieDi.getTaxaMediaAnual()).isEqualTo(DEFAULT_TAXA_MEDIA_ANUAL);
        assertThat(testIndiceSerieDi.getTaxaSelic()).isEqualTo(DEFAULT_TAXA_SELIC);
        assertThat(testIndiceSerieDi.getFatorDiario()).isEqualTo(DEFAULT_FATOR_DIARIO);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = indiceSerieDiRepository.findAll().size();
        // set the field null
        indiceSerieDi.setData(null);

        // Create the IndiceSerieDi, which fails.

        restIndiceSerieDiMockMvc.perform(post("/api/indice-serie-dis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(indiceSerieDi)))
                .andExpect(status().isBadRequest());

        List<IndiceSerieDi> indiceSerieDis = indiceSerieDiRepository.findAll();
        assertThat(indiceSerieDis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxaMediaAnualIsRequired() throws Exception {
        int databaseSizeBeforeTest = indiceSerieDiRepository.findAll().size();
        // set the field null
        indiceSerieDi.setTaxaMediaAnual(null);

        // Create the IndiceSerieDi, which fails.

        restIndiceSerieDiMockMvc.perform(post("/api/indice-serie-dis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(indiceSerieDi)))
                .andExpect(status().isBadRequest());

        List<IndiceSerieDi> indiceSerieDis = indiceSerieDiRepository.findAll();
        assertThat(indiceSerieDis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxaSelicIsRequired() throws Exception {
        int databaseSizeBeforeTest = indiceSerieDiRepository.findAll().size();
        // set the field null
        indiceSerieDi.setTaxaSelic(null);

        // Create the IndiceSerieDi, which fails.

        restIndiceSerieDiMockMvc.perform(post("/api/indice-serie-dis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(indiceSerieDi)))
                .andExpect(status().isBadRequest());

        List<IndiceSerieDi> indiceSerieDis = indiceSerieDiRepository.findAll();
        assertThat(indiceSerieDis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFatorDiarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = indiceSerieDiRepository.findAll().size();
        // set the field null
        indiceSerieDi.setFatorDiario(null);

        // Create the IndiceSerieDi, which fails.

        restIndiceSerieDiMockMvc.perform(post("/api/indice-serie-dis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(indiceSerieDi)))
                .andExpect(status().isBadRequest());

        List<IndiceSerieDi> indiceSerieDis = indiceSerieDiRepository.findAll();
        assertThat(indiceSerieDis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIndiceSerieDis() throws Exception {
        // Initialize the database
        indiceSerieDiRepository.saveAndFlush(indiceSerieDi);

        // Get all the indiceSerieDis
        restIndiceSerieDiMockMvc.perform(get("/api/indice-serie-dis?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(indiceSerieDi.getId().intValue())))
                .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
                .andExpect(jsonPath("$.[*].taxaMediaAnual").value(hasItem(DEFAULT_TAXA_MEDIA_ANUAL.intValue())))
                .andExpect(jsonPath("$.[*].taxaSelic").value(hasItem(DEFAULT_TAXA_SELIC.intValue())))
                .andExpect(jsonPath("$.[*].fatorDiario").value(hasItem(DEFAULT_FATOR_DIARIO.intValue())));
    }

    @Test
    @Transactional
    public void getIndiceSerieDi() throws Exception {
        // Initialize the database
        indiceSerieDiRepository.saveAndFlush(indiceSerieDi);

        // Get the indiceSerieDi
        restIndiceSerieDiMockMvc.perform(get("/api/indice-serie-dis/{id}", indiceSerieDi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(indiceSerieDi.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.taxaMediaAnual").value(DEFAULT_TAXA_MEDIA_ANUAL.intValue()))
            .andExpect(jsonPath("$.taxaSelic").value(DEFAULT_TAXA_SELIC.intValue()))
            .andExpect(jsonPath("$.fatorDiario").value(DEFAULT_FATOR_DIARIO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIndiceSerieDi() throws Exception {
        // Get the indiceSerieDi
        restIndiceSerieDiMockMvc.perform(get("/api/indice-serie-dis/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndiceSerieDi() throws Exception {
        // Initialize the database
        indiceSerieDiService.save(indiceSerieDi);

        int databaseSizeBeforeUpdate = indiceSerieDiRepository.findAll().size();

        // Update the indiceSerieDi
        IndiceSerieDi updatedIndiceSerieDi = new IndiceSerieDi();
        updatedIndiceSerieDi.setId(indiceSerieDi.getId());
        updatedIndiceSerieDi.setData(UPDATED_DATA);
        updatedIndiceSerieDi.setTaxaMediaAnual(UPDATED_TAXA_MEDIA_ANUAL);
        updatedIndiceSerieDi.setTaxaSelic(UPDATED_TAXA_SELIC);
        updatedIndiceSerieDi.setFatorDiario(UPDATED_FATOR_DIARIO);

        restIndiceSerieDiMockMvc.perform(put("/api/indice-serie-dis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedIndiceSerieDi)))
                .andExpect(status().isOk());

        // Validate the IndiceSerieDi in the database
        List<IndiceSerieDi> indiceSerieDis = indiceSerieDiRepository.findAll();
        assertThat(indiceSerieDis).hasSize(databaseSizeBeforeUpdate);
        IndiceSerieDi testIndiceSerieDi = indiceSerieDis.get(indiceSerieDis.size() - 1);
        assertThat(testIndiceSerieDi.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testIndiceSerieDi.getTaxaMediaAnual()).isEqualTo(UPDATED_TAXA_MEDIA_ANUAL);
        assertThat(testIndiceSerieDi.getTaxaSelic()).isEqualTo(UPDATED_TAXA_SELIC);
        assertThat(testIndiceSerieDi.getFatorDiario()).isEqualTo(UPDATED_FATOR_DIARIO);
    }

    @Test
    @Transactional
    public void deleteIndiceSerieDi() throws Exception {
        // Initialize the database
        indiceSerieDiService.save(indiceSerieDi);

        int databaseSizeBeforeDelete = indiceSerieDiRepository.findAll().size();

        // Get the indiceSerieDi
        restIndiceSerieDiMockMvc.perform(delete("/api/indice-serie-dis/{id}", indiceSerieDi.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<IndiceSerieDi> indiceSerieDis = indiceSerieDiRepository.findAll();
        assertThat(indiceSerieDis).hasSize(databaseSizeBeforeDelete - 1);
    }
}
