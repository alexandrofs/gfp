package com.alexandrofs.gfp.dsl;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.alexandrofs.gfp.domain.Carteira;
import com.alexandrofs.gfp.domain.Instituicao;
import com.alexandrofs.gfp.domain.Investimento;
import com.alexandrofs.gfp.domain.TipoInvestimento;
import com.alexandrofs.gfp.repository.InvestimentoRepository;

public class InvestimentoDsl {
	
	private Investimento investimento;
	private InvestimentoRepository repository;
	private PreparacaoAmbiente dado;

	public InvestimentoDsl(InvestimentoRepository repository, PreparacaoAmbiente dado) {
		super();
		this.repository = repository;
		this.dado = dado;
		investimento = new Investimento();
		investimento.setDataAplicacao(LocalDate.now());
		investimento.setQtdeCota(BigDecimal.ONE);
		investimento.setVlrCota(BigDecimal.ONE);
	}

	public Investimento salva() {
		
		if (investimento.getTipoInvestimento() == null) {
			investimento.setTipoInvestimento(dado.tipoInvestimento().salva());
		}
		
		if (investimento.getCarteira() == null) {
			investimento.setCarteira(dado.carteira().salva());
		}
		
		if (investimento.getInstituicao() == null) {
			investimento.setInstituicao(dado.instituicao().salva());
		}
		
		return repository.saveAndFlush(investimento);
	}

	public InvestimentoDsl setDataAplicacao(LocalDate dataAplicacao) {
		investimento.setDataAplicacao(dataAplicacao);
		return this;
	}

	public InvestimentoDsl setQtdeCota(BigDecimal qtdeCota) {
		investimento.setQtdeCota(qtdeCota);
		return this;
	}

	public InvestimentoDsl setVlrCota(BigDecimal vlrCota) {
		investimento.setVlrCota(vlrCota);
		return this;
	}

	public InvestimentoDsl setCarteira(Carteira carteira) {
		investimento.setCarteira(carteira);
		return this;
	}

	public InvestimentoDsl setTipoInvestimento(TipoInvestimento tipoInvestimento) {
		investimento.setTipoInvestimento(tipoInvestimento);
		return this;
	}
	
}
