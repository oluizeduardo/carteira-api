package br.com.carteira.controller;

import java.net.URI;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import br.com.carteira.model.Usuario;
import br.com.carteira.service.TransacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/transacoes")
@Api(tags = "Transação")
public class TransacaoController {

	@Autowired
	private TransacaoService service;
	ModelMapper modelMapper = new ModelMapper();
	
	
	@GetMapping
	@ApiOperation("Listar transações")
	public Page<TransacaoDTO> listar(@ApiIgnore @PageableDefault(size = 5) Pageable paginacao,
			@ApiIgnore @AuthenticationPrincipal Usuario usuarioLogado) 
	{
		return service.listar(paginacao, usuarioLogado);
	}
	
	@PostMapping
	@ApiOperation("Cadastrar uma nova transação")
	public ResponseEntity<TransacaoDTO> cadastrar(
			@RequestBody 
			@Valid 
			TransacaoFormDTO dto,
			
			UriComponentsBuilder uriBuilder, 
			
			@AuthenticationPrincipal 
			@ApiIgnore
			Usuario usuarioLogado) 
	{
		TransacaoDTO transacaoDTO = service.cadastrar(dto, usuarioLogado);
		
		URI uri = uriBuilder.path("/transacoes/{id}")
				.buildAndExpand(transacaoDTO.getId())
				.toUri();
		return ResponseEntity.created(uri ).body(transacaoDTO);
	}
	
	@PutMapping
	@ApiOperation("Atualizar uma transação")
	public ResponseEntity<TransacaoDTO> atualizar(
			@RequestBody @Valid AtualizacaoTransacaoFormDTO dto,
			@ApiIgnore @AuthenticationPrincipal Usuario usuarioLogado) 
	{
		TransacaoDTO transacaoDTOAtualizada = service.atualizar(dto, usuarioLogado);
		return ResponseEntity.ok(transacaoDTOAtualizada);
	}
	
	@DeleteMapping("{id}")
	@ApiOperation("Deletar uma transação")
	public ResponseEntity<TransacaoDTO> remover(@PathVariable @NotNull Integer id,
						@ApiIgnore @AuthenticationPrincipal Usuario usuarioLogado) 
	{
		service.remover(id, usuarioLogado);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("{id}")
	@ApiOperation("Detalhar uma transação")
	public ResponseEntity<TransacaoDetalhadaDTO> detalhar(@PathVariable @NotNull Integer id,
			@ApiIgnore @AuthenticationPrincipal Usuario usuarioLogado) 
	{
		TransacaoDetalhadaDTO dto = service.detalhar(id, usuarioLogado);
		return ResponseEntity.ok(dto);
	}
	
}
