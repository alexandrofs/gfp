package com.alexandrofs.gfp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.alexandrofs.gfp.AbstractTest;
import com.alexandrofs.gfp.GfpApp;
import com.alexandrofs.gfp.GfpUtils;
import com.alexandrofs.gfp.domain.IndiceSerieDi;
import com.alexandrofs.gfp.repository.IndiceSerieDiRepository;
import com.alexandrofs.gfp.service.ImportaIndiceSerieDiService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GfpApp.class)
public class ImportaIndiceSerieDiResourceIntTest extends AbstractTest {


    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final BigDecimal DEFAULT_TAXA_MEDIA_ANUAL = new BigDecimal(1);
    private static final BigDecimal DEFAULT_TAXA_SELIC = new BigDecimal(1);
    private static final BigDecimal DEFAULT_FATOR_DIARIO = new BigDecimal(1);

    @Autowired
    private IndiceSerieDiRepository indiceSerieDiRepository;

    @Autowired
    private ImportaIndiceSerieDiService indiceSerieDiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restIndiceSerieDiMockMvc;

    private IndiceSerieDi indiceSerieDi;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ImportaIndiceSerieDiResource indiceSerieDiResource = new ImportaIndiceSerieDiResource();
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
    public void importIndiceSerieDi() throws Exception {
    	
        int databaseSizeBeforeCreate = indiceSerieDiRepository.findAll().size();

        InputStream inputStream = new ClassPathResource("file_import_indice_di.xls").getInputStream();
        
        MockMultipartFile file = new MockMultipartFile("file", inputStream);

        restIndiceSerieDiMockMvc.perform(fileUpload("/api/indice-serie-dis/import")
        		.file(file))
                .andExpect(status().isOk());

        // Validate the IndiceSerieDi in the database
        List<IndiceSerieDi> indiceSerieDis = indiceSerieDiRepository.findAll();
        assertThat(indiceSerieDis).hasSize(databaseSizeBeforeCreate + 2);
        IndiceSerieDi testIndiceSerieDi = indiceSerieDis.get(indiceSerieDis.size() - 2);
        assertThat(testIndiceSerieDi.getData()).isEqualTo(LocalDate.parse("2017-04-03", DateTimeFormatter.ISO_LOCAL_DATE));
        assertThat(testIndiceSerieDi.getTaxaMediaAnual()).isEqualTo(GfpUtils.converteStringToBigDecimal("12,13"));
        assertThat(testIndiceSerieDi.getTaxaSelic()).isEqualTo(GfpUtils.converteStringToBigDecimal("12,15"));
        assertThat(testIndiceSerieDi.getFatorDiario()).isEqualTo(GfpUtils.converteStringToBigDecimal("1,00045442"));
        
        IndiceSerieDi testIndiceSerieDi2 = indiceSerieDis.get(indiceSerieDis.size() - 1);
        assertThat(testIndiceSerieDi2.getData()).isEqualTo(LocalDate.parse("2017-04-04", DateTimeFormatter.ISO_LOCAL_DATE));
        assertThat(testIndiceSerieDi2.getTaxaMediaAnual()).isEqualTo(GfpUtils.converteStringToBigDecimal("12,13"));
        assertThat(testIndiceSerieDi2.getTaxaSelic()).isEqualTo(GfpUtils.converteStringToBigDecimal("12,15"));
        assertThat(testIndiceSerieDi2.getFatorDiario()).isEqualTo(GfpUtils.converteStringToBigDecimal("1,00045442"));
    }    
    
    @Test
    @Transactional
    public void importIndiceSerieDiRepetido() throws Exception {
    	
        int databaseSizeBeforeCreate = indiceSerieDiRepository.findAll().size();

        InputStream inputStream = new ClassPathResource("file_import_indice_di.xls").getInputStream();
        
        MockMultipartFile file = new MockMultipartFile("file", inputStream);

        restIndiceSerieDiMockMvc.perform(fileUpload("/api/indice-serie-dis/import")
        		.file(file))
                .andExpect(status().isOk());
        
        restIndiceSerieDiMockMvc.perform(fileUpload("/api/indice-serie-dis/import")
        		.file(file))
                .andExpect(status().isOk());        

        // Validate the IndiceSerieDi in the database
        List<IndiceSerieDi> indiceSerieDis = indiceSerieDiRepository.findAll();
        assertThat(indiceSerieDis).hasSize(databaseSizeBeforeCreate + 2);
        IndiceSerieDi testIndiceSerieDi = indiceSerieDis.get(indiceSerieDis.size() - 2);
        assertThat(testIndiceSerieDi.getData()).isEqualTo(LocalDate.parse("2017-04-03", DateTimeFormatter.ISO_LOCAL_DATE));
        assertThat(testIndiceSerieDi.getTaxaMediaAnual()).isEqualTo(GfpUtils.converteStringToBigDecimal("12,13"));
        assertThat(testIndiceSerieDi.getTaxaSelic()).isEqualTo(GfpUtils.converteStringToBigDecimal("12,15"));
        assertThat(testIndiceSerieDi.getFatorDiario()).isEqualTo(GfpUtils.converteStringToBigDecimal("1,00045442"));
        
        IndiceSerieDi testIndiceSerieDi2 = indiceSerieDis.get(indiceSerieDis.size() - 1);
        assertThat(testIndiceSerieDi2.getData()).isEqualTo(LocalDate.parse("2017-04-04", DateTimeFormatter.ISO_LOCAL_DATE));
        assertThat(testIndiceSerieDi2.getTaxaMediaAnual()).isEqualTo(GfpUtils.converteStringToBigDecimal("12,13"));
        assertThat(testIndiceSerieDi2.getTaxaSelic()).isEqualTo(GfpUtils.converteStringToBigDecimal("12,15"));
        assertThat(testIndiceSerieDi2.getFatorDiario()).isEqualTo(GfpUtils.converteStringToBigDecimal("1,00045442"));
    }    
    
}
