package com.alexandrofs.gfp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexandrofs.gfp.domain.HistoricoCotas;
import com.alexandrofs.gfp.domain.Investimento;
import com.alexandrofs.gfp.repository.HistoricoCotasRepository;


@Service
@Transactional
public class CalculoRendimentosService {
	
	private static org.slf4j.Logger LOG = LoggerFactory.getLogger(CalculoRendimentosService.class);

	@Autowired
	private HistoricoCotasRepository cotasRepository;
	
	@Autowired
    private CalculoImpostoService impostoService;
	
	public Investimento calcula(final Investimento i) {
		
		LOG.info("Calculando saldos do investimento [" + i + "]");
		
		Optional<HistoricoCotas> cotaDesc = cotasRepository.findFirstByInvestimentoOrderByDataCotaDesc(i);
		
		if (cotaDesc.isPresent()) {
			
			LOG.info("Última cota [" + cotaDesc + "]");
			
			BigDecimal vlrValorAplicado = i.getVlrCota().multiply(i.getQtdeCota());
			BigDecimal vlrSaldoBruto = cotaDesc.get().getVlrCota().multiply(i.getQtdeCota());
			BigDecimal vlrRendimento = vlrSaldoBruto.subtract(vlrValorAplicado);
			BigDecimal vlrImposto = impostoService.calculaImposto(i.getTipoInvestimento().getTipoImpostoRenda(), vlrRendimento, i.getDataAplicacao());
			BigDecimal vlrSaldoLiquido = vlrSaldoBruto.subtract(vlrImposto);
			BigDecimal pctRendTotalLiquido = vlrRendimento.subtract(vlrImposto)
					.divide(vlrValorAplicado, 8, RoundingMode.HALF_UP)
					.multiply(new BigDecimal(100));	
			i.setVlrSaldoBruto(vlrSaldoBruto);
			i.setVlrSaldoLiquido(vlrSaldoLiquido);
			i.setVlrRendLiquido(vlrRendimento.subtract(vlrImposto));
			i.setPctRendTotalLiquido(pctRendTotalLiquido); 
			i.setPctRendTotalLiquido(pctRendTotalLiquido);
			
		} else {
			
			i.setVlrSaldoBruto(i.getVlrCota().multiply(i.getQtdeCota()));
			
		}
		
		return i;
	}
}
