package br.com.carteira.dto;

import java.math.BigDecimal;

import br.com.carteira.model.TipoTransacao;
import br.com.carteira.model.Transacao;

public class TransacaoDTO {

	private String ticker;
	private BigDecimal preco;
	private int quantidade;
	private TipoTransacao tipo;
	
	
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public TipoTransacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoTransacao tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Transacao [ticker=" + ticker + ", preco=" + preco + ", quantidade=" + quantidade + ", tipo=" + tipo
				+ "]";
	}

}
