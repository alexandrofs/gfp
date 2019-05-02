package com.alexandrofs.gfp.dsl;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private TipoImpostoRendaRepository tipoImpostoRendaRepository;
	
	@Autowired
	private TipoInvestimentoRepository tipoInvestimentoRepository;
	
	@Autowired
	private CarteiraRepository carteiraRepository;
	
	@Autowired
	private InstituicaoRepository instituicaoRepository;
	
	@Autowired
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
