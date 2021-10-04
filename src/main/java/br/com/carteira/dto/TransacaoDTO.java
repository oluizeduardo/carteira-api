package br.com.carteira.dto;

import java.math.BigDecimal;

import br.com.carteira.model.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoDTO {

	private String ticker;
	private BigDecimal preco;
	private int quantidade;
	private TipoTransacao tipo;

}
