package br.com.carteira.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.carteira.dto.AtualizacaoTransacaoFormDTO;
import br.com.carteira.dto.TransacaoDTO;
import br.com.carteira.dto.TransacaoDetalhadaDTO;
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
	
	@Autowired
	private ModelMapper modelMapper;

	
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

	
	@Transactional
	public TransacaoDTO atualizar(@Valid AtualizacaoTransacaoFormDTO dto) 
	{
		Transacao transacao = transacaoRepository.getById(dto.getId());		
		transacao.atualizarInformacoes(dto.getTicker(), dto.getData(), dto.getPreco(), dto.getQuantidade(), dto.getTipo());
		// Nesse momento a JPA percebe que uma entidade foi carregada do banco de dados
		// e teve seus dados modificados. A atualização é feita de forma automática pelo JPA.
		
		// Mapeia o retorno para um TransacaoDTO.
		return modelMapper.map(transacao, TransacaoDTO.class);
	}

	
	@Transactional
	public void remover(Integer id) 
	{
		transacaoRepository.deleteById(id);
	}

	public TransacaoDetalhadaDTO detalhar(Integer id) 
	{
		Transacao transacao = transacaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		
		// Mapeia o retorno para um TransacaoDTO.
		return modelMapper.map(transacao, TransacaoDetalhadaDTO.class);
	}

}
