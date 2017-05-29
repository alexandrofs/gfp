package com.alexandrofs.gfp.dsl;

import com.alexandrofs.gfp.domain.TipoImpostoRenda;
import com.alexandrofs.gfp.domain.TipoInvestimento;
import com.alexandrofs.gfp.domain.fixed.ModalidadeEnum;
import com.alexandrofs.gfp.repository.TipoInvestimentoRepository;

public class TipoInvestimentoDsl {
	
	private TipoInvestimento tipoInvestimento;
	
	private TipoInvestimentoRepository repository;
	
	private PreparacaoAmbiente dado;

	public TipoInvestimentoDsl(TipoInvestimentoRepository repository, PreparacaoAmbiente dado) {
		super();
		this.repository = repository;
		this.dado = dado;
		tipoInvestimento = new TipoInvestimento();		
		tipoInvestimento.setNome("AAA");
		tipoInvestimento.setDescricao("BBB");
		tipoInvestimento.setModalidade(ModalidadeEnum.CDB);
	}
	
	public TipoInvestimento salva() {
		
		if (tipoInvestimento.getTipoImpostoRenda() == null) {
			tipoInvestimento.setTipoImpostoRenda(dado.tipoImpostoRenda().salva());			
		}
		
		return repository.saveAndFlush(tipoInvestimento);
	}

	public void setNome(String nome) {
		tipoInvestimento.setNome(nome);
	}

	public void setDescricao(String descricao) {
		tipoInvestimento.setDescricao(descricao);
	}

	public void setTipoImpostoRenda(TipoImpostoRenda tipoImpostoRenda) {
		tipoInvestimento.setTipoImpostoRenda(tipoImpostoRenda);
	}
	
	

}
