package br.com.carteira.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDetalhadaDTO extends TransacaoDTO {

	private LocalDate data;
	private UsuarioDTO usuario;
	
}
