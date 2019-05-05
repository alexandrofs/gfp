package com.alexandrofs.gfp;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alexandrofs.gfp.domain.Carteira;
import com.alexandrofs.gfp.domain.Instituicao;
import com.alexandrofs.gfp.domain.Investimento;
import com.alexandrofs.gfp.domain.TipoImpostoRenda;
import com.alexandrofs.gfp.domain.TipoInvestimento;
import com.alexandrofs.gfp.dsl.TipoInvestimentoDsl;
import com.alexandrofs.gfp.service.CalculoCotasService;
import com.alexandrofs.gfp.service.CalculoRendimentosService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GfpApp.class)
public class CalculoRendimentosServiceTest extends AbstractTest {
	
	@Autowired
	private CalculoCotasService calculoCotasService;
	
	@Autowired
	private CalculoRendimentosService calculoRendimentosService;
	
	private Investimento investimento;
	
    @Before
    public void initTest() {
    	
    	TipoImpostoRenda tabelaIr = insereTabelaIr();
    	
    	TipoInvestimentoDsl tipoInvestimento = dsl.dado().tipoInvestimento();
		tipoInvestimento.setTipoImpostoRenda(tabelaIr);
		TipoInvestimento tipoInvestSalvo = tipoInvestimento.salva();
    	
        investimento = new Investimento();
        investimento.setDataAplicacao(DEFAULT_DATA_APLICACAO);
        investimento.setQtdeCota(UPDATED_QTDE_COTA);
        investimento.setVlrCota(UPDATED_VLR_COTA);
        investimento.setPctPrePosFixado(new BigDecimal(100));
        investimento.setTipoInvestimento(tipoInvestSalvo);
        Carteira cart = new Carteira();
        cart.setNome("AAA");
        cart.setDescricao("BBB");
        carteiraRepository.saveAndFlush(cart);
        investimento.setCarteira(cart);
        investimento.setTipoInvestimento(tipoInvestimento.salva());
        Instituicao instituicao = new Instituicao();
        instituicao.setNome("CCC");
        instituicaoRepository.saveAndFlush(instituicao);
        investimento.setInstituicao(instituicao);
        investimentoRepository.saveAndFlush(investimento);
        
        insereIndice(DEFAULT_DATA_APLICACAO.plusDays(1), new BigDecimal(1.5));
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
		
		Investimento investimentoCalculado = calculoRendimentosService.calcula(investimento);
		
		assertThat(investimentoCalculado.getVlrRendLiquido(), equalTo(new BigDecimal("0.4250")));
		assertThat(investimentoCalculado.getVlrSaldoBruto(), equalTo(new BigDecimal("1.50")));
		assertThat(investimentoCalculado.getVlrSaldoLiquido(), equalTo(new BigDecimal("1.4250")));
		assertThat(investimentoCalculado.getPctRendTotalLiquido(), equalTo(new BigDecimal("42.50000000")));
		
	}
    
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Test
	public void calcula1diaDuasVezes(){
		
		calculoCotasService.calcula(investimento.getId());
		
		calculoCotasService.calcula(investimento.getId());
		
		Investimento investimentoCalculado = calculoRendimentosService.calcula(investimento);
		
		assertThat(investimentoCalculado.getVlrRendLiquido(), equalTo(new BigDecimal("0.4250")));
		assertThat(investimentoCalculado.getVlrSaldoBruto(), equalTo(new BigDecimal("1.50")));
		assertThat(investimentoCalculado.getVlrSaldoLiquido(), equalTo(new BigDecimal("1.4250")));
		assertThat(investimentoCalculado.getPctRendTotalLiquido(), equalTo(new BigDecimal("42.50000000")));
	}
    
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Test
	public void calcula1diaDuasVezesNovoIndice(){
		
		calculoCotasService.calcula(investimento.getId());
		
		Investimento investimentoCalculado = calculoRendimentosService.calcula(investimento);
		
		assertThat(investimentoCalculado.getVlrRendLiquido(), equalTo(new BigDecimal("0.4250")));
		assertThat(investimentoCalculado.getVlrSaldoBruto(), equalTo(new BigDecimal("1.50")));
		assertThat(investimentoCalculado.getVlrSaldoLiquido(), equalTo(new BigDecimal("1.4250")));
		assertThat(investimentoCalculado.getPctRendTotalLiquido(), equalTo(new BigDecimal("42.50000000")));
		
	}
    
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Test
	public void calcula2dias(){
		
    	insereIndice(DEFAULT_DATA_APLICACAO.plusDays(2));
    	
		calculoCotasService.calcula(investimento.getId());
		
		Investimento investimentoCalculado = calculoRendimentosService.calcula(investimento);
		
		assertThat(investimentoCalculado.getVlrRendLiquido(), equalTo(new BigDecimal("0.4505")));
		assertThat(investimentoCalculado.getVlrSaldoBruto(), equalTo(new BigDecimal("1.53")));
		assertThat(investimentoCalculado.getVlrSaldoLiquido(), equalTo(new BigDecimal("1.4505")));
		assertThat(investimentoCalculado.getPctRendTotalLiquido(), equalTo(new BigDecimal("45.05000000")));
		
	}
    
}
