package br.com.carteira.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carteira.dto.LoginFormDTO;
import br.com.carteira.dto.TokenDTO;
import br.com.carteira.infra.security.AutenticacaoService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Api(tags = "Autenticação")
@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired
	private AutenticacaoService service;
	
	
	@PostMapping
	public TokenDTO autenticar(@RequestBody LoginFormDTO dto) 
	{
		return new TokenDTO(service.autenticar(dto));
	}
	
	
}
