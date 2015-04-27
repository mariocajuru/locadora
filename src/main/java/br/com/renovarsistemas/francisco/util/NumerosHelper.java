package br.com.renovarsistemas.francisco.util;

import java.math.BigDecimal;

public class NumerosHelper {
	
	public static int apenasNumeros(String texto) {
		StringBuilder stringBuilder = new StringBuilder();
		int retorno = 0;
	
		for (char posicao : texto.toCharArray())
			if (Character.isDigit(posicao))
				stringBuilder.append(posicao);

		try {
			retorno = Integer.parseInt( stringBuilder.toString() ); 
		} catch (NumberFormatException e) { }
		
		return retorno;
	}

	public static double apenasNumerosDouble(String texto) {
		StringBuilder stringBuilder = new StringBuilder();
		double retorno = 0;
	
		for (char posicao : texto.toCharArray())
			if (Character.isDigit(posicao))
				stringBuilder.append(posicao);

		try {
			retorno = Double.parseDouble( stringBuilder.toString() ); 
		} catch (NumberFormatException e) { }
		
		return retorno;
	}

	public static BigDecimal apenasNumerosBigDecimal(String texto) {
		StringBuilder stringBuilder = new StringBuilder();
		BigDecimal retorno = new BigDecimal(0);
	
		for (char posicao : texto.toCharArray())
			if (Character.isDigit(posicao))
				stringBuilder.append(posicao);

		try {
			retorno = new BigDecimal( stringBuilder.toString() ); 
		} catch (NumberFormatException e) { }
		
		return retorno;
	}
	
}