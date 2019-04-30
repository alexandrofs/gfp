package com.alexandrofs.gfp;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alexandrofs.gfp.domain.Carteira;
import com.alexandrofs.gfp.domain.HistoricoCotas;
import com.alexandrofs.gfp.domain.IndiceSerieDi;
import com.alexandrofs.gfp.domain.Instituicao;
import com.alexandrofs.gfp.domain.Investimento;
import com.alexandrofs.gfp.repository.HistoricoCotasRepository;
import com.alexandrofs.gfp.repository.IndiceSerieDiRepository;
import com.alexandrofs.gfp.service.CalculoCotasService;

public class CalculoCotasTest extends AbstractTest {
	
	@Autowired
	private CalculoCotasService calculoCotasService;
	
	@Autowired
	private IndiceSerieDiRepository indiceRepo;
	
	@Autowired
	private HistoricoCotasRepository cotasRepository;
	
	private Investimento investimento;
	
    @Before
    public void initTest() {
        investimento = new Investimento();
        investimento.setDataAplicacao(DEFAULT_DATA_APLICACAO);
        investimento.setQtdeCota(UPDATED_QTDE_COTA);
        investimento.setVlrCota(UPDATED_VLR_COTA);
        investimento.setPctPrePosFixado(new BigDecimal(100));
        Carteira cart = new Carteira();
        cart.setNome("AAA");
        cart.setDescricao("BBB");
        carteiraRepository.saveAndFlush(cart);
        investimento.setCarteira(cart);
        investimento.setTipoInvestimento(dsl.dado().tipoInvestimento().salva());
        Instituicao instituicao = new Instituicao();
        instituicao.setNome("CCC");
        instituicaoRepository.saveAndFlush(instituicao);
        investimento.setInstituicao(instituicao);
        investimentoRepository.saveAndFlush(investimento);
        
        insereIndice(DEFAULT_DATA_APLICACAO.plusDays(1));
    }

    @After
    public void clearData() {
    	
    	investimentoRepository.deleteAll();
    	indiceRepo.deleteAll();
    	
    }
    
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Test
	public void calcula1dia(){
		
		calculoCotasService.calcula(investimento.getId());
		
		List<HistoricoCotas> cotaDesc = cotasRepository.findByInvestimentoOrderByDataCotaDesc(investimento);
		
		assertThat(cotaDesc.size(), equalTo(1));
		assertThat(cotaDesc.get(0).getDataCota(), equalTo(DEFAULT_DATA_APLICACAO.plusDays(1)));
		assertThat(cotaDesc.get(0).getVlrCota(), equalTo(new BigDecimal("1.02")));
		
	}
    
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Test
	public void calcula1diaDuasVezes(){
		
		calculoCotasService.calcula(investimento.getId());
		
		List<HistoricoCotas> cotaDesc = cotasRepository.findByInvestimentoOrderByDataCotaDesc(investimento);
		
		assertThat(cotaDesc.size(), equalTo(1));
		assertThat(cotaDesc.get(0).getDataCota(), equalTo(DEFAULT_DATA_APLICACAO.plusDays(1)));
		assertThat(cotaDesc.get(0).getVlrCota(), equalTo(new BigDecimal("1.02")));
		
		insereIndice(DEFAULT_DATA_APLICACAO.plusDays(2));
		
		calculoCotasService.calcula(investimento.getId());
		
		cotaDesc = cotasRepository.findByInvestimentoOrderByDataCotaDesc(investimento);
		
		assertThat(cotaDesc.size(), equalTo(2));
		assertThat(cotaDesc.get(0).getDataCota(), equalTo(DEFAULT_DATA_APLICACAO.plusDays(2)));
		assertThat(cotaDesc.get(0).getVlrCota(), equalTo(new BigDecimal("1.04")));
		
	}
    
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Test
	public void calcula1diaDuasVezesNovoIndice(){
		
		calculoCotasService.calcula(investimento.getId());
		
		List<HistoricoCotas> cotaDesc = cotasRepository.findByInvestimentoOrderByDataCotaDesc(investimento);
		
		assertThat(cotaDesc.size(), equalTo(1));
		assertThat(cotaDesc.get(0).getDataCota(), equalTo(DEFAULT_DATA_APLICACAO.plusDays(1)));
		assertThat(cotaDesc.get(0).getVlrCota(), equalTo(new BigDecimal("1.02")));
		
		calculoCotasService.calcula(investimento.getId());
		
		cotaDesc = cotasRepository.findByInvestimentoOrderByDataCotaDesc(investimento);
		
		assertThat(cotaDesc.size(), equalTo(1));
		assertThat(cotaDesc.get(0).getDataCota(), equalTo(DEFAULT_DATA_APLICACAO.plusDays(1)));
		assertThat(cotaDesc.get(0).getVlrCota(), equalTo(new BigDecimal("1.02")));
		
	}
    
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Test
	public void calcula2dias(){
		
    	insereIndice(DEFAULT_DATA_APLICACAO.plusDays(2));
    	
		calculoCotasService.calcula(investimento.getId());
		
		List<HistoricoCotas> cotaDesc = cotasRepository.findByInvestimentoOrderByDataCotaDesc(investimento);
		
		assertThat(cotaDesc.size(), equalTo(2));
		assertThat(cotaDesc.get(0).getDataCota(), equalTo(DEFAULT_DATA_APLICACAO.plusDays(2)));
		assertThat(cotaDesc.get(0).getVlrCota(), equalTo(new BigDecimal("1.04")));
		
	}
    
	private void insereIndice(LocalDate localDate) {
		IndiceSerieDi indiceSerieDi = new IndiceSerieDi();
        indiceSerieDi.setData(localDate);
        indiceSerieDi.setFatorDiario(new BigDecimal(1.02));
        indiceSerieDi.setTaxaMediaAnual(BigDecimal.ONE);
        indiceSerieDi.setTaxaSelic(BigDecimal.ONE);
        indiceRepo.saveAndFlush(indiceSerieDi);
	}
	
}
