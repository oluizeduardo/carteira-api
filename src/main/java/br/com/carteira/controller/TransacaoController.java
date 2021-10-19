package br.com.carteira.controller;

import java.net.URI;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sun.istack.NotNull;

import br.com.carteira.dto.AtualizacaoTransacaoFormDTO;
import br.com.carteira.dto.TransacaoDTO;
import br.com.carteira.dto.TransacaoDetalhadaDTO;
import br.com.carteira.dto.TransacaoFormDTO;
import br.com.carteira.service.TransacaoService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/transacoes")
//Documentação Swagger.
@Api(tags = "Transação")
public class TransacaoController {

	@Autowired
	private TransacaoService service;
	ModelMapper modelMapper = new ModelMapper();
	
	
	@GetMapping
	public Page<TransacaoDTO> listar(@PageableDefault(size = 5) Pageable paginacao) 
	{
		return service.listar(paginacao);
	}
	
	@PostMapping
	public ResponseEntity<TransacaoDTO> cadastrar(@RequestBody @Valid TransacaoFormDTO dto,
			UriComponentsBuilder uriBuilder) 
	{
		TransacaoDTO transacaoDTO = service.cadastrar(dto);
		
		URI uri = uriBuilder.path("/transacoes/{id}")
				.buildAndExpand(transacaoDTO.getId())
				.toUri();
		return ResponseEntity.created(uri ).body(transacaoDTO);
	}
	
	@PutMapping
	public ResponseEntity<TransacaoDTO> atualizar(@RequestBody @Valid AtualizacaoTransacaoFormDTO dto) 
	{
		TransacaoDTO transacaoDTOAtualizada = service.atualizar(dto);
		return ResponseEntity.ok(transacaoDTOAtualizada);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<TransacaoDTO> remover(@PathVariable @NotNull Integer id) 
	{
		service.remover(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<TransacaoDetalhadaDTO> detalhar(@PathVariable @NotNull Integer id) 
	{
		TransacaoDetalhadaDTO dto = service.detalhar(id);
		return ResponseEntity.ok(dto);
	}
	
}
