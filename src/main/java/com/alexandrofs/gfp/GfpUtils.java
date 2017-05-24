package com.alexandrofs.gfp;

import java.math.BigDecimal;

public class GfpUtils {
	
	public static BigDecimal converteStringToBigDecimal(String value) {
		return new BigDecimal(value.replaceAll(",", "."));
	}

}
