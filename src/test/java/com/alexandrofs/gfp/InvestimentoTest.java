package com.alexandrofs.gfp;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alexandrofs.gfp.model.InvestimentoEntity;
import com.alexandrofs.gfp.persist.repository.InvestimentoRepository;

public class InvestimentoTest extends AbstractTest{

	@Autowired
	private InvestimentoRepository investimentoRepo;
	
	@Test
	public void testaInsert() throws ParseException{
		InvestimentoEntity entity = new InvestimentoEntity();
		entity.setNome("PETR4");
		SimpleDateFormat f = new SimpleDateFormat("dd/mm/yyyy");
		entity.setDataAplicacao(f.parse("19/12/2011"));
		entity.setQtdeCota(new BigDecimal(2.85));
		entity.setVlrCota(new BigDecimal(21.30));
		investimentoRepo.saveAndFlush(entity);
	}
}
