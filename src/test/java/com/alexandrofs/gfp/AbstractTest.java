package com.alexandrofs.gfp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.inject.Inject;

import org.springframework.test.context.ActiveProfiles;

import com.alexandrofs.gfp.dsl.Dsl;
import com.alexandrofs.gfp.repository.CarteiraRepository;
import com.alexandrofs.gfp.repository.InstituicaoRepository;
import com.alexandrofs.gfp.repository.InvestimentoRepository;

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

	@Inject
	protected Dsl dsl;
	
    @Inject
    protected InvestimentoRepository investimentoRepository;
    
    @Inject
    protected CarteiraRepository carteiraRepository;
    
    @Inject
    protected InstituicaoRepository instituicaoRepository; 
}
