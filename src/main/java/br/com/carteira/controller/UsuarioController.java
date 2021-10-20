package br.com.carteira.controller;

import java.net.URI;

import javax.validation.Valid;

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

import br.com.carteira.dto.AtualizacaoUsuarioFormDTO;
import br.com.carteira.dto.UsuarioDTO;
import br.com.carteira.dto.UsuarioFormDTO;
import br.com.carteira.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/usuarios")

//Documentação Swagger.
@Api(tags = "Usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	
	@GetMapping
	@ApiOperation("Listar usuários")
	public Page<UsuarioDTO> listar(@PageableDefault(size = 5) Pageable paginacao) 
	{
		return service.listar(paginacao);
	}
	
	
	@PostMapping
	@ApiOperation("Cadastrar novo usuário")
	public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody @Valid UsuarioFormDTO dto,
			UriComponentsBuilder uriBuilder) 
	{
		UsuarioDTO usuarioDTO = service.cadastrar(dto);
		
		URI uri = uriBuilder.path("/usuarios/{id}")
				.buildAndExpand(usuarioDTO.getId())
				.toUri();
		
		return ResponseEntity.created(uri ).body(usuarioDTO);
	}
	
	@PutMapping
	@ApiOperation("Atualizar um usuário")
	public ResponseEntity<UsuarioDTO> atualizar(@RequestBody @Valid AtualizacaoUsuarioFormDTO dto) 
	{
		UsuarioDTO usuarioDTOAtualizado = service.atualizar(dto);
		return ResponseEntity.ok(usuarioDTOAtualizado);
	}
	
	@DeleteMapping("{id}")
	@ApiOperation("Deletar um usuário")
	public ResponseEntity<UsuarioDTO> remover(@PathVariable @NotNull Integer id) 
	{
		service.remover(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("{id}")
	@ApiOperation("Detalhar um usuário")
	public ResponseEntity<UsuarioDTO> detalhar(@PathVariable @NotNull Integer id) 
	{
		UsuarioDTO dto = service.detalhar(id);
		return ResponseEntity.ok(dto);
	}
	
}
