package com.alexandrofs.gfp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alexandrofs.gfp.domain.Carteira;
import com.alexandrofs.gfp.domain.HistoricoCotas;
import com.alexandrofs.gfp.domain.Investimento;
import com.alexandrofs.gfp.domain.TipoImpostoRenda;
import com.alexandrofs.gfp.domain.TipoInvestimento;
import com.alexandrofs.gfp.repository.CarteiraRepository;
import com.alexandrofs.gfp.repository.HistoricoCotasRepository;
import com.alexandrofs.gfp.repository.InvestimentoRepository;
import com.alexandrofs.gfp.repository.TipoImpostoRendaRepository;
import com.alexandrofs.gfp.repository.TipoInvestimentoRepository;
import com.alexandrofs.gfp.service.InvestimentoService;

public class InvestimentoTest extends AbstractTest {

	@Autowired
	private InvestimentoRepository investimentoRepo;

	@Autowired
	private HistoricoCotasRepository historicoRepo;

	@Autowired
	private InvestimentoService investimetoService;
	
	@Autowired
	private TipoInvestimentoRepository tipoInvestimentoRepo;
	
	@Autowired
	private TipoImpostoRendaRepository tipoIr;

	@Autowired
	private CarteiraRepository carteiraRepo;
	
	@Test
	public void testaInsert() throws ParseException {
		Investimento entity = new Investimento();
		entity.setNome("PETR4");
		SimpleDateFormat f = new SimpleDateFormat("dd/mm/yyyy");
		entity.setDataAplicacao(LocalDate.of(2011, 12, 19));
		entity.setQtdeCota(new BigDecimal(2.85));
		entity.setVlrCota(new BigDecimal(21.30));
		TipoInvestimento tipoInvestimento = new TipoInvestimento();
		tipoInvestimento.setNome("A��es");
		tipoInvestimento.setDescricao("A��es");
		TipoImpostoRenda ir = tipoIr.findByCodigo("RF");
		entity.setTipoInvestimento(tipoInvestimento);
		tipoInvestimento.setTipoImpostoRenda(ir);
		tipoInvestimentoRepo.save(tipoInvestimento);
		Carteira c = new Carteira();
		c.setNome("PREV");
		c.setDescricao("Previdencia");
		carteiraRepo.save(c);
		entity.setCarteira(c);
		investimentoRepo.saveAndFlush(entity);
		HistoricoCotas historicoCotasEntity = new HistoricoCotas();
		historicoCotasEntity.setDataCota(LocalDate.of(2011, 12, 19));
		historicoCotasEntity.setVlrCota(new BigDecimal(22));
		historicoCotasEntity.setInvestimento(entity);
		historicoRepo.saveAndFlush(historicoCotasEntity);
/*		investimetoService.calculaRendimento(entity);
		System.out.printf("Valor aplicado %s, rendimento %s, saldo %s",
				entity.getQtdeCota().multiply(entity.getVlrCota()).setScale(2, RoundingMode.HALF_DOWN),
				entity.getRendimentoLiquido(), entity.getSaldo());*/
	}
}
