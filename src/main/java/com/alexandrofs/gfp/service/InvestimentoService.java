package com.alexandrofs.gfp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexandrofs.gfp.model.HistoricoCotas;
import com.alexandrofs.gfp.model.Investimento;
import com.alexandrofs.gfp.repository.HistoricoCotasRepository;

@Service
public class InvestimentoService {
	
	@Autowired
	private HistoricoCotasRepository historicoCotasRepo;
	
	@Autowired
	private CalculoImpostoService calculoImpostoService;

	public void calculaRendimento(Investimento investimento) {
		HistoricoCotas historico = historicoCotasRepo.findByInvestimentoOrderByDataCotaDesc(investimento).stream().findFirst().get();
		BigDecimal rendimentoBruto = historico.getVlrCota().subtract(investimento.getVlrCota()).multiply(investimento.getQtdeCota());
		BigDecimal rendimentoLiquido = calculaIr(investimento, rendimentoBruto);
		BigDecimal saldo = investimento.getQtdeCota().multiply(investimento.getVlrCota()).add(rendimentoLiquido);
		investimento.setRendimentoLiquido(rendimentoLiquido.setScale(2, RoundingMode.HALF_DOWN));
		investimento.setSaldo(saldo.setScale(2, RoundingMode.HALF_DOWN));
		// TODO Terminar de calcular os outros atributos
	}

	private BigDecimal calculaIr(Investimento investimento, BigDecimal rendimentoBruto) {
		BigDecimal vlrImposto = calculoImpostoService.calculaImposto(investimento.getTipoInvestimento().getTipoImpostoRenda(), rendimentoBruto, investimento.getDataAplicacao());
		return rendimentoBruto.subtract(vlrImposto);
	}
	
}
