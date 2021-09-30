package br.com.carteira.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carteira.dto.TransacaoDTO;
import br.com.carteira.dto.TransacaoFormDTO;
import br.com.carteira.model.Transacao;
import br.com.carteira.repository.TransacaoRepository;

@Service
public class TransacaoService {

	@Autowired
	private TransacaoRepository transacaoRepository; 
	private ModelMapper modelMapper = new ModelMapper();

	public List<TransacaoDTO> listar() 
	{
		List<Transacao> transacoes = transacaoRepository.findAll();
		return transacoes.stream().map(t -> modelMapper.map(t, TransacaoDTO.class)).collect(Collectors.toList());
	}

	@Transactional
	public void cadastrar(TransacaoFormDTO dto) 
	{
		Transacao transacao = modelMapper.map(dto, Transacao.class);
		transacao.setId(null);
		transacaoRepository.save(transacao);
	}

}
