package br.com.carteira.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Erro400DTO {

	private String campo;
	private String mensagem;
	
}
