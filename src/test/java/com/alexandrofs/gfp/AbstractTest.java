package com.alexandrofs.gfp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	protected Dsl dsl;
	
	@Autowired
    protected InvestimentoRepository investimentoRepository;
    
	@Autowired
    protected CarteiraRepository carteiraRepository;
    
	@Autowired
    protected InstituicaoRepository instituicaoRepository; 
}
