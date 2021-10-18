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

import br.com.carteira.dto.TransacaoDTO;
import br.com.carteira.dto.TransacaoFormDTO;
import br.com.carteira.model.TipoTransacao;
import br.com.carteira.repository.TransacaoRepository;
import br.com.carteira.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

	@Mock
	private TransacaoRepository transacaoRepository; 
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@InjectMocks
	private TransacaoService service;
	
	
	@Test
	void deveriaCadastrarUmaTransacao() {
		
		TransacaoFormDTO formDTO = new TransacaoFormDTO(
				"ITSA4", 
				new BigDecimal("10.45"), 
				10, 
				TipoTransacao.COMPRA, 
				LocalDate.now(),
				1);
		
		
		TransacaoDTO dto = service.cadastrar(formDTO);
		
//		Mockito.verify(transacaoRepository.save(Mockito.any()));
		
		assertEquals(formDTO.getTicker(), dto.getTicker());
		assertEquals(formDTO.getPreco(), dto.getPreco());
		assertEquals(formDTO.getQuantidade(), dto.getQuantidade());
		assertEquals(formDTO.getTipo(), dto.getTipo());		
	}
	
	
	@Test
	void naoDeveriaCadastrarUmaTransacaoComUsuarioInexistente() {
		
		TransacaoFormDTO formDTO = new TransacaoFormDTO(
				"ITSA4", 
				new BigDecimal("10.45"), 
				10, 
				TipoTransacao.COMPRA, 
				LocalDate.now(),
				1);
		
		
		Mockito.when(usuarioRepository
				.getById(formDTO.getUsuarioId()))
				.thenThrow(EntityNotFoundException.class);
		
		assertThrows(IllegalArgumentException.class, () -> service.cadastrar(formDTO));	
	}

}
