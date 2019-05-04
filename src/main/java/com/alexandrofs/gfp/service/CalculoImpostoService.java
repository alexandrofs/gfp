package com.alexandrofs.gfp.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexandrofs.gfp.domain.TabelaImpostoRenda;
import com.alexandrofs.gfp.domain.TipoImpostoRenda;


@Service
@Transactional
public class CalculoImpostoService {

	private static org.slf4j.Logger LOG = LoggerFactory.getLogger(CalculoCotasService.class);
	
	public BigDecimal calculaImposto(TipoImpostoRenda impostoRenda, BigDecimal valor, LocalDate dtAplicacao) {
		BigDecimal resultado = null;
		if (impostoRenda.getTabelaImpostoRendas().size() > 0){
			long diasCorridos = calculaDiasCorridos(dtAplicacao);
			TabelaImpostoRenda tabela = impostoRenda.getTabelaImpostoRendas().stream()
					.filter(l -> diasCorridos > l.getNumDias().longValue())
					.sorted(new Comparator<TabelaImpostoRenda>() {

						@Override
						public int compare(TabelaImpostoRenda o1, TabelaImpostoRenda o2) {
							return o2.getNumDias().compareTo(o1.getNumDias());
						}
					})
					.findFirst().get();
			LOG.info("Calculando imposto de " + tabela.getPctAliquota() + "% para o valor " + valor);
			resultado = valor.multiply(tabela.getPctAliquota().divide(new BigDecimal(100)));
		} else {
			LOG.info("Tipo de imposto n�o tem tabela de aliquota, ent�o considera como ISENTO");
			resultado = new BigDecimal(0);
		}
		LOG.info("Resultado: " + resultado);
		return resultado;
	}

	public long calculaDiasCorridos(LocalDate dataInicio) {
		LOG.info("Data inicio entrada: " + dataInicio);
		LocalDateTime inicio = LocalDateTime.of(dataInicio, LocalTime.of(0, 0)); 
		LocalDateTime hoje = LocalDateTime.now();
		LOG.info("Data inicio: " + dataInicio + " Data fim: " + hoje);
		long diasCorridos = Duration.between(inicio, hoje).toDays();
		LOG.info("Dias corridos: " + diasCorridos);
		return diasCorridos;
	}
}
