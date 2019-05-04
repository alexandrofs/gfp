package com.alexandrofs.gfp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alexandrofs.gfp.domain.TipoImpostoRenda;
import com.alexandrofs.gfp.service.CalculoImpostoService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GfpApp.class)
@Transactional
public class CalculoImpostoTest extends AbstractTest {
	
	@Autowired
	private CalculoImpostoService calculoService;
	
	@Before
	public void setup() {
		insereTabelaIr();
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
		Assert.assertEquals(new BigDecimal("100.0"), result);
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
