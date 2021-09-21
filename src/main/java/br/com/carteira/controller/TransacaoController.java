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
	
	@GetMapping
	public List<TransacaoDTO> listar() {
		
//		return transacoes.stream().map(TransacaoDTO::new).collect(Collectors.toList());
		
		ModelMapper modelMapper = new ModelMapper();
		return transacoes
				.stream()
				.map(t -> modelMapper.map(t, TransacaoDTO.class))
				.collect(Collectors.toList());
	}
	
	@PostMapping
	public void cadastrar(@RequestBody TransacaoFormDTO dto) {
		Transacao transacao = new Transacao(
				dto.getTicker(),
				dto.getPreco(),
				dto.getQuantidade(),
				dto.getData(),
				dto.getTipo());
		
		transacoes.add(transacao);
	}
	
}
