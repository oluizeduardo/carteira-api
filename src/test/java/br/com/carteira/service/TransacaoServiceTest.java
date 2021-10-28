package br.com.carteira.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.carteira.dto.TransacaoDTO;
import br.com.carteira.dto.TransacaoFormDTO;
import br.com.carteira.model.TipoTransacao;
import br.com.carteira.repository.TransacaoRepository;
import br.com.carteira.repository.UsuarioRepository;

//Carrega as funções do mockito antes de inicializar o JUnit.
@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

	@Mock
	private TransacaoRepository transacaoRepository; 
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks
	private TransacaoService service;
	
	
	private TransacaoFormDTO criaTransacaoFormDTO() 
	{
		return new TransacaoFormDTO(
				"ITSA4", 
				new BigDecimal("10.45"), 
				10, 
				TipoTransacao.COMPRA, 
				LocalDate.now(),
				1);
	}
	
	
	@Test
	void deveriaCadastrarUmaTransacao() 
	{
		
		TransacaoFormDTO formDTO = criaTransacaoFormDTO();
		TransacaoDTO dto = service.cadastrar(formDTO, null);
		
		// Checa se o método 'save' foi chamado.
		Mockito.verify(transacaoRepository).save(Mockito.any());
		
		assertEquals(formDTO.getTicker(), dto.getTicker());
		assertEquals(formDTO.getPreco(), dto.getPreco());
		assertEquals(formDTO.getQuantidade(), dto.getQuantidade());
		assertEquals(formDTO.getTipo(), dto.getTipo());		
	}

	
	@Test
	void naoDeveriaCadastrarUmaTransacaoComUsuarioInexistente() 
	{
		
		TransacaoFormDTO formDTO = criaTransacaoFormDTO();	
		
		Mockito.when(usuarioRepository
				.getById(formDTO.getUsuarioId()))
				.thenThrow(EntityNotFoundException.class);
		
		assertThrows(IllegalArgumentException.class, () -> service.cadastrar(formDTO, null));	
	}

}
