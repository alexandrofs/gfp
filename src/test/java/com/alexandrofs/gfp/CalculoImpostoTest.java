package com.alexandrofs.gfp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alexandrofs.gfp.domain.TabelaImpostoRenda;
import com.alexandrofs.gfp.domain.TipoImpostoRenda;
import com.alexandrofs.gfp.repository.TipoImpostoRendaRepository;
import com.alexandrofs.gfp.service.CalculoImpostoService;

@Transactional
public class CalculoImpostoTest extends AbstractTest {
	
	@Autowired
	private CalculoImpostoService calculoService;
	
	@Autowired
	private TipoImpostoRendaRepository tipoImpostoRepo;
	
	@Before
	public void setup() {
		TipoImpostoRenda tipoImpostoRenda = new TipoImpostoRenda();
		tipoImpostoRenda.setCodigo("RF");
		tipoImpostoRenda.setDescricao("RF");

		Set<TabelaImpostoRenda> tabela = new HashSet<>();
		
		TabelaImpostoRenda tabelaImpostoRenda1 = new TabelaImpostoRenda();
		tabelaImpostoRenda1.setNumDias(Long.valueOf(180));
		tabelaImpostoRenda1.setPctAliquota(BigDecimal.valueOf(22.5));
		tabelaImpostoRenda1.setTipoImpostoRenda(tipoImpostoRenda);
		tabela.add(tabelaImpostoRenda1);
		TabelaImpostoRenda tabelaImpostoRenda2 = new TabelaImpostoRenda();
		tabelaImpostoRenda2.setNumDias(Long.valueOf(360));
		tabelaImpostoRenda2.setPctAliquota(BigDecimal.valueOf(20));
		tabelaImpostoRenda2.setTipoImpostoRenda(tipoImpostoRenda);
		tabela.add(tabelaImpostoRenda2);
		TabelaImpostoRenda tabelaImpostoRenda3 = new TabelaImpostoRenda();
		tabelaImpostoRenda3.setNumDias(Long.valueOf(720));
		tabelaImpostoRenda3.setPctAliquota(BigDecimal.valueOf(17.5));
		tabelaImpostoRenda3.setTipoImpostoRenda(tipoImpostoRenda);
		tabela.add(tabelaImpostoRenda3);		
		TabelaImpostoRenda tabelaImpostoRenda4 = new TabelaImpostoRenda();
		tabelaImpostoRenda4.setNumDias(Long.valueOf(721));
		tabelaImpostoRenda4.setPctAliquota(BigDecimal.valueOf(17.5));
		tabelaImpostoRenda4.setTipoImpostoRenda(tipoImpostoRenda);
		tabela.add(tabelaImpostoRenda4);
		
		tipoImpostoRenda.setTabelaImpostoRendas(tabela);
		
		tipoImpostoRepo.saveAndFlush(tipoImpostoRenda);
	}
	
	@Test
	public void testaCalculoImpostoRendaFixa100dias(){
		String codImposto = "RF";
		long diasCorridos = 100;
		double valor = 500;
		BigDecimal result = calculaImposto(codImposto, diasCorridos, valor);
		Assert.assertEquals(new BigDecimal("112.500"), result);
	}
	
	@Test
	public void testaCalculoImpostoRendaFixa180dias(){
		String codImposto = "RF";
		long diasCorridos = 180;
		double valor = 500;
		BigDecimal result = calculaImposto(codImposto, diasCorridos, valor);
		Assert.assertEquals(new BigDecimal("112.500"), result);
	}

	@Test
	public void testaCalculoImpostoRendaFixa181dias(){
		String codImposto = "RF";
		long diasCorridos = 181;
		double valor = 500;
		BigDecimal result = calculaImposto(codImposto, diasCorridos, valor);
		Assert.assertEquals(new BigDecimal("100.0"), result);
	}

	@Test
	public void testaCalculoImpostoRendaFixa360dias(){
		String codImposto = "RF";
		long diasCorridos = 360;
		double valor = 500;
		BigDecimal result = calculaImposto(codImposto, diasCorridos, valor);
		Assert.assertEquals(new BigDecimal("100.00"), result);
	}

	@Test
	public void testaCalculoImpostoRendaFixa361dias(){
		String codImposto = "RF";
		long diasCorridos = 361;
		double valor = 500;
		BigDecimal result = calculaImposto(codImposto, diasCorridos, valor);
		Assert.assertEquals(new BigDecimal("87.500"), result);
	}

	@Test
	public void testaCalculoImpostoRendaFixa720dias(){
		String codImposto = "RF";
		long diasCorridos = 720;
		double valor = 500;
		BigDecimal result = calculaImposto(codImposto, diasCorridos, valor);
		Assert.assertEquals(new BigDecimal("87.500"), result);
	}

	@Test
	public void testaCalculoImpostoRendaFixa721dias(){
		String codImposto = "RF";
		long diasCorridos = 721;
		double valor = 500;
		BigDecimal result = calculaImposto(codImposto, diasCorridos, valor);
		Assert.assertEquals(new BigDecimal("75.00"), result);
	}

	@Test
	public void testaCalculoImpostoRendaFixa1444dias(){
		String codImposto = "RF";
		long diasCorridos = 1444;
		double valor = 500;
		BigDecimal result = calculaImposto(codImposto, diasCorridos, valor);
		Assert.assertEquals(new BigDecimal("75.00"), result);
	}

	private BigDecimal calculaImposto(String codImposto, long diasCorridos, double valor) {
		TipoImpostoRenda tipoImp = tipoImpostoRepo.findByCodigo(codImposto);
		BigDecimal result = calculoService.calculaImposto(tipoImp, new BigDecimal(valor), LocalDate.now().minusDays(diasCorridos).atStartOfDay(ZoneId.systemDefault()).toLocalDate());
		return result;
	}
}
