package br.com.carteira.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.carteira.model.TipoTransacao;
import br.com.carteira.model.Transacao;
import br.com.carteira.model.Usuario;
import br.com.carteira.repository.TransacaoRepository;
import br.com.carteira.repository.UsuarioRepository;


//Carrega as funções do Spring antes de inicializar o JUnit.
@ExtendWith(SpringExtension.class)

//Carrega todas as configurações do projeto, não somente a camada de persistência.
@SpringBootTest

//Configura o MockMvc para injeção nos testes.
@AutoConfigureMockMvc

//Indica qual arquivo .propoerties deve ser carregado. Gerar banco de teste.
@ActiveProfiles("test")

//Executa em contexto transacional. Faz o rollback ao final de cada teste.
@Transactional
class RelatoriosControllerTest {

	/**
	 * Objeto que simula as requisições HTTP.
	 */
	@Autowired
	private MockMvc mvc; 
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	
	
	@Test
	void deveriaEmitirRelatorio() throws Exception {
		
		Usuario usuario = new Usuario("Rafaela", "rafa@gmail.com");
		usuarioRepository.save(usuario);
		
		Transacao transacao = new Transacao("XPTO1", 
				new BigDecimal("10.20"), 
				10, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		transacaoRepository.save(transacao);

		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get("/relatorios/carteira"))
			    .andReturn();
			 
		String json = "[{\"ticker\":\"XPTO1\",\"quantidade\":10,\"percentual\":100.00}]";
		
		assertEquals(json, mvcResult.getResponse().getContentAsString());
		
	}

}
