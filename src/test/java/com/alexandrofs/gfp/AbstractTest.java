package com.alexandrofs.gfp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.alexandrofs.gfp.domain.IndiceSerieDi;
import com.alexandrofs.gfp.domain.TabelaImpostoRenda;
import com.alexandrofs.gfp.domain.TipoImpostoRenda;
import com.alexandrofs.gfp.dsl.Dsl;
import com.alexandrofs.gfp.repository.CarteiraRepository;
import com.alexandrofs.gfp.repository.IndiceSerieDiRepository;
import com.alexandrofs.gfp.repository.InstituicaoRepository;
import com.alexandrofs.gfp.repository.InvestimentoRepository;
import com.alexandrofs.gfp.repository.TipoImpostoRendaRepository;

@ActiveProfiles("test")
public abstract class AbstractTest {
	
    protected static final LocalDate DEFAULT_DATA_APLICACAO = LocalDate.ofEpochDay(0L);
    protected static final LocalDate UPDATED_DATA_APLICACAO = LocalDate.now(ZoneId.systemDefault());

    protected static final BigDecimal DEFAULT_QTDE_COTA = new BigDecimal(0);
    protected static final BigDecimal UPDATED_QTDE_COTA = new BigDecimal(1);

    protected static final BigDecimal DEFAULT_VLR_COTA = new BigDecimal(0);
    protected static final BigDecimal UPDATED_VLR_COTA = new BigDecimal(1);

    protected static final BigDecimal DEFAULT_PCT_PRE_POS_FIXADO = new BigDecimal(1);
    protected static final BigDecimal UPDATED_PCT_PRE_POS_FIXADO = new BigDecimal(2);

	@Autowired
	protected Dsl dsl;
	
	@Autowired
    protected InvestimentoRepository investimentoRepository;
    
	@Autowired
    protected CarteiraRepository carteiraRepository;
    
	@Autowired
    protected InstituicaoRepository instituicaoRepository; 
	
	@Autowired
	protected TipoImpostoRendaRepository tipoImpostoRepo;
	
	@Autowired
	protected IndiceSerieDiRepository indiceRepo;
	
	protected TipoImpostoRenda insereTabelaIr() {
		TipoImpostoRenda tipoImpostoRenda = new TipoImpostoRenda();
		tipoImpostoRenda.setCodigo("RF");
		tipoImpostoRenda.setDescricao("RF");

		Set<TabelaImpostoRenda> tabela = new HashSet<>();
		
		TabelaImpostoRenda tabelaImpostoRenda1 = new TabelaImpostoRenda();
		tabelaImpostoRenda1.setNumDias(Long.valueOf(0));
		tabelaImpostoRenda1.setPctAliquota(BigDecimal.valueOf(22.5));
		tabelaImpostoRenda1.setTipoImpostoRenda(tipoImpostoRenda);
		tabela.add(tabelaImpostoRenda1);
		TabelaImpostoRenda tabelaImpostoRenda2 = new TabelaImpostoRenda();
		tabelaImpostoRenda2.setNumDias(Long.valueOf(180));
		tabelaImpostoRenda2.setPctAliquota(BigDecimal.valueOf(20));
		tabelaImpostoRenda2.setTipoImpostoRenda(tipoImpostoRenda);
		tabela.add(tabelaImpostoRenda2);
		TabelaImpostoRenda tabelaImpostoRenda3 = new TabelaImpostoRenda();
		tabelaImpostoRenda3.setNumDias(Long.valueOf(360));
		tabelaImpostoRenda3.setPctAliquota(BigDecimal.valueOf(17.5));
		tabelaImpostoRenda3.setTipoImpostoRenda(tipoImpostoRenda);
		tabela.add(tabelaImpostoRenda3);		
		TabelaImpostoRenda tabelaImpostoRenda4 = new TabelaImpostoRenda();
		tabelaImpostoRenda4.setNumDias(Long.valueOf(720));
		tabelaImpostoRenda4.setPctAliquota(BigDecimal.valueOf(15.0));
		tabelaImpostoRenda4.setTipoImpostoRenda(tipoImpostoRenda);
		tabela.add(tabelaImpostoRenda4);
		
		tipoImpostoRenda.setTabelaImpostoRendas(tabela);
		
		return tipoImpostoRepo.saveAndFlush(tipoImpostoRenda);
	}
	
    protected void insereIndice(LocalDate localDate) {
    	insereIndice(localDate, null);
    }
    
	protected void insereIndice(LocalDate localDate, BigDecimal fatorDiario) {
		IndiceSerieDi indiceSerieDi = new IndiceSerieDi();
        indiceSerieDi.setData(localDate);
        indiceSerieDi.setFatorDiario(fatorDiario == null ? new BigDecimal(1.02) : fatorDiario);
        indiceSerieDi.setTaxaMediaAnual(BigDecimal.ONE);
        indiceSerieDi.setTaxaSelic(BigDecimal.ONE);
        indiceRepo.saveAndFlush(indiceSerieDi);
	}
}
