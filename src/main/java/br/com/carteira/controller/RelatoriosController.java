package br.com.carteira.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carteira.dto.ItemCarteiraDTO;
import br.com.carteira.service.RelatorioService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/relatorios")
//Documentação Swagger.
@Api(tags = "Relatórios")
public class RelatoriosController {

	@Autowired
	private RelatorioService service;
	
	@GetMapping("/carteira")
	public List<ItemCarteiraDTO> relatorioCarteiraDeInvestimentos()
	{
		return service.relatorioCarteiraDeInvestimentos();
	}
}
