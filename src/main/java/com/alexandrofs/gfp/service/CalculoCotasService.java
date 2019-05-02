package com.alexandrofs.gfp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexandrofs.gfp.domain.HistoricoCotas;
import com.alexandrofs.gfp.domain.IndiceSerieDi;
import com.alexandrofs.gfp.domain.Investimento;
import com.alexandrofs.gfp.repository.HistoricoCotasRepository;
import com.alexandrofs.gfp.repository.ImportaIndiceSerieDiRepository;
import com.alexandrofs.gfp.web.rest.errors.BadRequestAlertException;


@Service
@Transactional
public class CalculoCotasService {
	
	private static org.slf4j.Logger LOG = LoggerFactory.getLogger(CalculoCotasService.class);

	@Autowired
	private HistoricoCotasRepository cotasRepository;
	
	@Autowired
	private ImportaIndiceSerieDiRepository serieDiRepository;
	
	@Autowired
	private InvestimentoService investimentoService;
	
	public void calcula(Long idInvestimento) {
		
		Investimento investimento = investimentoService.findOne(idInvestimento)
				.orElseThrow(() -> new BadRequestAlertException("Id não encontrado", "Investimento", "idnotfound"));
		
		Optional<HistoricoCotas> ultimaCota = cotasRepository.findFirstByInvestimentoOrderByDataCotaDesc(investimento);
		
		LocalDate dataInicio = investimento.getDataAplicacao();
		BigDecimal valor = investimento.getVlrCota();
		
		if (ultimaCota.isPresent()) {
			
			HistoricoCotas cota = ultimaCota.get();
			dataInicio = cota.getDataCota().plusDays(1);
			valor = cota.getVlrCota();
		}
		
		List<IndiceSerieDi> listIndices = serieDiRepository.findByDataBetweenOrderByData(dataInicio, LocalDate.now());
		
		for (IndiceSerieDi indice : listIndices) {
			
			BigDecimal valorJuros = valor.multiply(indice.getFatorDiario().subtract(BigDecimal.ONE));
			valorJuros = valorJuros.multiply(investimento.getPctPrePosFixado()).divide(new BigDecimal(100));
			valor = valor.add(valorJuros);
			
			HistoricoCotas historicoCotas = new HistoricoCotas();
			historicoCotas.setDataCota(indice.getData());
			historicoCotas.setVlrCota(valor);
			historicoCotas.setInvestimento(investimento);
			
			investimento.getHistoricoCotas().add(historicoCotas);
			
			LOG.info("Calculando cota data [" + indice.getData() + "], fator [" + indice.getFatorDiario() + "], valor [" + valor + "]");
			
		}
		
		investimentoService.save(investimento);
			
	}
	
	public Investimento calculaSaldoBruto(final Investimento i) {
		Optional<HistoricoCotas> cotaDesc = cotasRepository.findFirstByInvestimentoOrderByDataCotaDesc(i);
		if (cotaDesc.isPresent()) {
			i.setVlrSaldoBruto(cotaDesc.get().getVlrCota().multiply(i.getQtdeCota()));
		} else {
			i.setVlrSaldoBruto(i.getVlrCota().multiply(i.getQtdeCota()));
		}
		return i;
	}
}
