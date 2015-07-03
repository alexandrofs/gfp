package com.alexandrofs.gfp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alexandrofs.gfp.model.HistoricoCotasEntity;
import com.alexandrofs.gfp.model.InvestimentoEntity;
import com.alexandrofs.gfp.persist.repository.HistoricoCotasRepository;
import com.alexandrofs.gfp.persist.repository.InvestimentoRepository;
import com.alexandrofs.gfp.service.InvestimentoService;

public class InvestimentoTest extends AbstractTest {

	@Autowired
	private InvestimentoRepository investimentoRepo;

	@Autowired
	private HistoricoCotasRepository historicoRepo;

	@Autowired
	private InvestimentoService investimetoService;

	@Test
	public void testaInsert() throws ParseException {
		InvestimentoEntity entity = new InvestimentoEntity();
		entity.setNome("PETR4");
		SimpleDateFormat f = new SimpleDateFormat("dd/mm/yyyy");
		entity.setDataAplicacao(f.parse("19/12/2011"));
		entity.setQtdeCota(new BigDecimal(2.85));
		entity.setVlrCota(new BigDecimal(21.30));
		investimentoRepo.saveAndFlush(entity);
		HistoricoCotasEntity historicoCotasEntity = new HistoricoCotasEntity();
		historicoCotasEntity.setDataCota(f.parse("19/12/2011"));
		historicoCotasEntity.setVlrCota(new BigDecimal(22));
		historicoCotasEntity.setInvestimento(entity);
		historicoRepo.saveAndFlush(historicoCotasEntity);
		investimetoService.calculaRendimento(entity);
		System.out.printf("Valor aplicado %s, rendimento %s, saldo %s",
				entity.getQtdeCota().multiply(entity.getVlrCota()).setScale(2, RoundingMode.HALF_DOWN),
				entity.getRendimentoLiquido(), entity.getSaldo());
	}
}
