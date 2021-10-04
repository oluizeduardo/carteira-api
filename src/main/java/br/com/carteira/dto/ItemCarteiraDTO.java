package br.com.carteira.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemCarteiraDTO {

	private String ticker;
	private Long quantidade;
	private Double percentual;
}
