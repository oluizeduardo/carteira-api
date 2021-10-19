package br.com.carteira.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.carteira.model.TipoTransacao;
import br.com.carteira.model.Transacao;
import br.com.carteira.model.Usuario;

class CalculadoraDeImpostoServicesTest {

	
	private CalculadoraDeImpostoServices calculadora;


	private Transacao criaTransacao(BigDecimal preco, Integer quantidade, TipoTransacao tipo) {
		return new Transacao(
				120, 
				"BBSE3", 
				preco, 
				quantidade, 
				LocalDate.now(), 
				tipo, 
				new Usuario(1, "Juliana", "juliana@gmail.com", "123789"));
	}
	
	
	
	@BeforeEach
	public void inicializarCalculadoraDeImpostoServices() 
	{
		calculadora = new CalculadoraDeImpostoServices();
	}
	
	
	
	@Test
	void transacaoDoTipoCompraNaoDeveriaTerImposto() 
	{
		
		Transacao transacao = criaTransacao(new BigDecimal("30.00"), 10, TipoTransacao.COMPRA);
		BigDecimal imposto = calculadora.calcular(transacao);
		assertEquals(BigDecimal.ZERO, imposto);
		
	}

	
	@Test
	void transacaoDoTipoVendaComValorMenorQueVinteMilNaoDeveriaTerImposto() 
	{
		
		Transacao transacao = criaTransacao(new BigDecimal("30.00"), 10, TipoTransacao.VENDA);
		BigDecimal imposto = calculadora.calcular(transacao);	
		assertEquals(BigDecimal.ZERO, imposto);
		
	}
	
	
	@Test
	void deveriaCalcularImpostoDeTransacaoDoTipoVendaComValorMaiorQueVinteMil() 
	{
		
		Transacao transacao = criaTransacao(new BigDecimal("1000.00"), 30, TipoTransacao.VENDA);
		BigDecimal imposto = calculadora.calcular(transacao);
		assertEquals(new BigDecimal("4500.00"), imposto);
		
	}

}
