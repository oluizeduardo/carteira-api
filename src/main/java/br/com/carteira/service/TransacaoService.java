package br.com.carteira.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.carteira.dto.TransacaoDTO;
import br.com.carteira.dto.TransacaoFormDTO;
import br.com.carteira.model.Transacao;
import br.com.carteira.model.Usuario;
import br.com.carteira.repository.TransacaoRepository;
import br.com.carteira.repository.UsuarioRepository;

@Service
public class TransacaoService {

	@Autowired
	private TransacaoRepository transacaoRepository; 
	
	@Autowired
	private UsuarioRepository usuarioRepository; 
	
	private ModelMapper modelMapper = new ModelMapper();

	public Page<TransacaoDTO> listar(Pageable paginacao) 
	{
		Page<Transacao> transacoes = transacaoRepository.findAll(paginacao);
		return transacoes.map(t -> modelMapper.map(t, TransacaoDTO.class));
	}

	@Transactional
	public TransacaoDTO cadastrar(TransacaoFormDTO dto) 
	{
		
		Integer iUsuario = dto.getUsuarioId();
		
		try {
			Usuario usuario = usuarioRepository.getById(iUsuario);
			
			Transacao transacao = modelMapper.map(dto, Transacao.class);
			transacao.setId(null);
			transacao.setUsuario(usuario);
			
			transacaoRepository.save(transacao);
			
			return modelMapper.map(transacao, TransacaoDTO.class);
		} catch (EntityNotFoundException ex) {
			throw new IllegalArgumentException("Usuário Inexistente!");
		}
	}

}
