package br.com.carteira.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.carteira.dto.AtualizacaoUsuarioFormDTO;
import br.com.carteira.dto.UsuarioDTO;
import br.com.carteira.dto.UsuarioFormDTO;
import br.com.carteira.model.Usuario;
import br.com.carteira.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository; 
	
	@Autowired
	private ModelMapper modelMapper;

	
	
	public Page<UsuarioDTO> listar(Pageable paginacao) 
	{
		Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
		return usuarios.map(t -> modelMapper.map(t, UsuarioDTO.class));
	}

	public UsuarioDTO cadastrar(UsuarioFormDTO dto) 
	{
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		usuario.setSenha();
		usuarioRepository.save(usuario);
		
		return modelMapper.map(usuario, UsuarioDTO.class);
	}

	@Transactional
	public UsuarioDTO atualizar(@Valid AtualizacaoUsuarioFormDTO dto) {
		Usuario usuario = usuarioRepository.getById(dto.getId());		
		usuario.atualizarInformacoes(dto.getNome(), dto.getLogin());
		// Nesse momento a JPA percebe que uma entidade foi carregada do banco de dados
		// e teve seus dados modificados. A atualização é feita de forma automática pelo JPA.
		
		// Mapeia o retorno para um TransacaoDTO.
		return modelMapper.map(usuario, UsuarioDTO.class);
	}

	@Transactional
	public void remover(Integer id) 
	{
		usuarioRepository.deleteById(id);
	}

	public UsuarioDTO detalhar(Integer id) 
	{
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		
		// Mapeia o retorno para um TransacaoDTO.
		return modelMapper.map(usuario, UsuarioDTO.class);
	}

}
