package com.alexandrofs.gfp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexandrofs.gfp.model.HistoricoCotasEntity;
import com.alexandrofs.gfp.model.InvestimentoEntity;
import com.alexandrofs.gfp.model.TipoImpostoRenda;
import com.alexandrofs.gfp.persist.repository.HistoricoCotasRepository;

@Service
public class InvestimentoService {
	
	@Autowired
	private HistoricoCotasRepository historicoCotasRepo;

	public void calculaRendimento(InvestimentoEntity investimento) {
		HistoricoCotasEntity historico = historicoCotasRepo.findByInvestimentoOrderByDataCota(investimento).get(0);
		BigDecimal rendimentoBruto = historico.getVlrCota().subtract(investimento.getVlrCota()).multiply(investimento.getQtdeCota());
		BigDecimal rendimentoLiquido = calculaIr(investimento.getTipoImpostoRenda(), rendimentoBruto);
		BigDecimal saldo = investimento.getQtdeCota().multiply(investimento.getVlrCota()).add(rendimentoLiquido);
		investimento.setRendimentoLiquido(rendimentoLiquido.setScale(2, RoundingMode.HALF_DOWN));
		investimento.setSaldo(saldo.setScale(2, RoundingMode.HALF_DOWN));
		// TODO Terminar de calcular os outros atributos
	}

	private BigDecimal calculaIr(TipoImpostoRenda tipoImpostoRenda, BigDecimal rendimentoBruto) {
		// TODO Implementar o cálculo
		return rendimentoBruto;
	}
	
}
