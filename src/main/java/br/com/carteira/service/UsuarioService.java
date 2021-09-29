package br.com.carteira.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carteira.dto.UsuarioDTO;
import br.com.carteira.dto.UsuarioFormDTO;
import br.com.carteira.model.Usuario;
import br.com.carteira.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository; 
	private ModelMapper modelMapper = new ModelMapper();

	public List<UsuarioDTO> listar() 
	{
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios.stream().map(t -> modelMapper.map(t, UsuarioDTO.class)).collect(Collectors.toList());
	}

	public void cadastrar(UsuarioFormDTO dto) 
	{
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		usuarioRepository.save(usuario);
	}

}
