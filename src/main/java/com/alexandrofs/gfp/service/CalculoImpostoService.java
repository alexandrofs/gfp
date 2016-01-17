package com.alexandrofs.gfp.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexandrofs.gfp.model.TabelaImpostoRenda;
import com.alexandrofs.gfp.model.TipoImpostoRenda;

@Service
@Transactional
public class CalculoImpostoService {

	private static Logger LOG = Logger.getLogger(CalculoImpostoService.class);
	
	public BigDecimal calculaImposto(TipoImpostoRenda impostoRenda, BigDecimal valor, Date dtAplicacao) {
		BigDecimal resultado = null;
		if (impostoRenda.getTabelaImposto().size() > 0){
			long diasCorridos = calculaDiasCorridos(dtAplicacao);
			TabelaImpostoRenda tabela = impostoRenda.getTabelaImposto().stream()
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
			LOG.info("Tipo de imposto não tem tabela de aliquota, então considera como ISENTO");
			resultado = new BigDecimal(0);
		}
		LOG.info("Resultado: " + resultado);
		return resultado;
	}

	public long calculaDiasCorridos(Date dataInicio) {
		LOG.info("Data inicio entrada: " + dataInicio);
		Instant instant = Instant.ofEpochMilli(dataInicio.getTime());
		LocalDateTime inicio = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		LocalDateTime hoje = LocalDateTime.now();
		LOG.info("Data inicio: " + inicio + " Data fim: " + hoje);
		long diasCorridos = Duration.between(inicio, hoje).toDays();
		LOG.info("Dias corridos: " + diasCorridos);
		return diasCorridos;
	}
}
