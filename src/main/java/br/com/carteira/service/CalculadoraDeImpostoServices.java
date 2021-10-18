package br.com.carteira.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.carteira.model.TipoTransacao;
import br.com.carteira.model.Transacao;

public class CalculadoraDeImpostoServices {

	public BigDecimal calcular(Transacao transacao) {
		if(transacao.getTipo() == TipoTransacao.COMPRA) 
		{
			return BigDecimal.ZERO;
		}
		
		BigDecimal valorTransacao = transacao
				.getPreco()
				.multiply(new BigDecimal(transacao.getQuantidade()));
		
		if(valorTransacao.compareTo(new BigDecimal(20000)) < 0) 
		{
			return BigDecimal.ZERO;
		}
		
		return valorTransacao.multiply(new BigDecimal("0.15")).setScale(2, RoundingMode.HALF_UP);
	}
	
}
