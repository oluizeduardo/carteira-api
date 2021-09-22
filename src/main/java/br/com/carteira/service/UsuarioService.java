package br.com.carteira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.carteira.dto.UsuarioDTO;
import br.com.carteira.dto.UsuarioFormDTO;
import br.com.carteira.model.Usuario;

@Service
public class UsuarioService {

	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private ModelMapper modelMapper = new ModelMapper();

	public List<UsuarioDTO> listar() 
	{
		return usuarios.stream().map(t -> modelMapper.map(t, UsuarioDTO.class)).collect(Collectors.toList());
	}

	public void cadastrar(UsuarioFormDTO dto) 
	{
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		
		String senha = (usuario.getNome().hashCode() + usuario.getLogin().hashCode())+"";
		usuario.setSenha(senha);
		
		usuarios.add(usuario);
	}

}
