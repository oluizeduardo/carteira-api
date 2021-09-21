package br.com.carteira.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carteira.dto.TransacaoDTO;
import br.com.carteira.dto.TransacaoFormDTO;
import br.com.carteira.model.Transacao;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

	private List<Transacao> transacoes = new ArrayList<Transacao>();
	
	ModelMapper modelMapper = new ModelMapper();
	
	
	@GetMapping
	public List<TransacaoDTO> listar() {
		
//		return transacoes.stream().map(TransacaoDTO::new).collect(Collectors.toList());

		return transacoes
				.stream()
				.map(t -> modelMapper.map(t, TransacaoDTO.class))
				.collect(Collectors.toList());
	}
	
	@PostMapping
	public void cadastrar(@RequestBody TransacaoFormDTO dto) {
		
		Transacao transacao = modelMapper.map(dto, Transacao.class);
		
		transacoes.add(transacao);
	}
	
}
