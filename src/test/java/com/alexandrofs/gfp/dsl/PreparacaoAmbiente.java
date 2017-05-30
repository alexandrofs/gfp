package com.alexandrofs.gfp.dsl;

import javax.inject.Inject;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.alexandrofs.gfp.repository.CarteiraRepository;
import com.alexandrofs.gfp.repository.InstituicaoRepository;
import com.alexandrofs.gfp.repository.InvestimentoRepository;
import com.alexandrofs.gfp.repository.TipoImpostoRendaRepository;
import com.alexandrofs.gfp.repository.TipoInvestimentoRepository;

@Profile("test")
@Component
public class PreparacaoAmbiente {

	@Inject
	private TipoImpostoRendaRepository tipoImpostoRendaRepository;
	
	@Inject
	private TipoInvestimentoRepository tipoInvestimentoRepository;
	
	@Inject
	private CarteiraRepository carteiraRepository;
	
	@Inject
	private InstituicaoRepository instituicaoRepository;
	
	@Inject
	private InvestimentoRepository investimentoRepository;
	
	public TipoImpostoRendaDsl tipoImpostoRenda() {
		return new TipoImpostoRendaDsl(tipoImpostoRendaRepository);
	}
	
	public TipoInvestimentoDsl tipoInvestimento() {
		return new TipoInvestimentoDsl(tipoInvestimentoRepository, this);
	}

	public CarteiraDsl carteira() {
		return new CarteiraDsl(carteiraRepository);
	}
	
	public InstituicaoDsl instituicao() {
		return new InstituicaoDsl(instituicaoRepository);
	}

	public InvestimentoDsl investimento() {
		return new InvestimentoDsl(investimentoRepository, this);
	}
}
