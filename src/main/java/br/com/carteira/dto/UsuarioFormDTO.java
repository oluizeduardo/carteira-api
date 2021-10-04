package br.com.carteira.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class UsuarioFormDTO {
	
	@NotBlank(message = "Name is required!")
	@Size(min=2, max = 40, message = "Name should be between 2 and 20 chars.")
	private String nome;
	
	@NotBlank(message = "Login is required!")
	@Size(min=2, max = 20, message = "Login should be between 2 and 20 chars.")
	private String login;
}
