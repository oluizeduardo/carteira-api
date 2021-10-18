package br.com.carteira.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.com.carteira.model.TipoTransacao;
import br.com.carteira.model.Transacao;
import br.com.carteira.model.Usuario;

class CalculadoraDeImpostoServicesTest {

	@Test
	void transacaoDoTipoCompraNaoDeveriaTerImposto() {
		
		Transacao transacao = new Transacao(
				120, 
				"BBSE3", 
				new BigDecimal("30.00"), 
				10, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				new Usuario(1, "Juliana", "juliana@gmail.com", "123789"));
		
		CalculadoraDeImpostoServices calculadora = new CalculadoraDeImpostoServices();
		BigDecimal imposto = calculadora.calcular(transacao);
		
		assertEquals(BigDecimal.ZERO, imposto);
		
	}
	
	
	@Test
	void transacaoDoTipoVendaComValorMenorQueVinteMilNaoDeveriaTerImposto() {
		
		Transacao transacao = new Transacao(
				120, 
				"BBSE3", 
				new BigDecimal("30.00"), 
				10, 
				LocalDate.now(), 
				TipoTransacao.VENDA, 
				new Usuario(1, "Juliana", "juliana@gmail.com", "123789"));
		
		CalculadoraDeImpostoServices calculadora = new CalculadoraDeImpostoServices();
		BigDecimal imposto = calculadora.calcular(transacao);
		
		assertEquals(BigDecimal.ZERO, imposto);
		
	}
	
	
	@Test
	void deveriaCalcularImpostoDeTransacaoDoTipoVendaComValorMaiorQueVinteMil() {
		
		Transacao transacao = new Transacao(
				120, 
				"BBSE3", 
				new BigDecimal("1000.00"), 
				30, 
				LocalDate.now(), 
				TipoTransacao.VENDA, 
				new Usuario(1, "Juliana", "juliana@gmail.com", "123789"));
		
		CalculadoraDeImpostoServices calculadora = new CalculadoraDeImpostoServices();
		BigDecimal imposto = calculadora.calcular(transacao);
		
		assertEquals(new BigDecimal("4500.00"), imposto);
		
	}

}
