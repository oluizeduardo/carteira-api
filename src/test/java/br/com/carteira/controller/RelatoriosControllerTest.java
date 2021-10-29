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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import br.com.carteira.model.Perfil;
import br.com.carteira.model.TipoTransacao;
import br.com.carteira.model.Transacao;
import br.com.carteira.model.Usuario;
import br.com.carteira.repository.PerfilRepository;
import br.com.carteira.repository.TransacaoRepository;
import br.com.carteira.repository.UsuarioRepository;
import br.com.carteira.service.TokenService;


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
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private TokenService tokenService; 
	
	
	
	@Test
	void deveriaEmitirRelatorio() throws Exception {
		
		// Cria um novo usuário com perfil Admin.
		Perfil admin = perfilRepository.findById(1).get();
		Usuario usuario = new Usuario("Rafaela", "rafa@gmail.com");
		usuario.addPerfil(admin);
		usuarioRepository.save(usuario);
		
		// Cria uma nova transação.
		Transacao transacao = new Transacao("XPTO1", 
				new BigDecimal("10.20"), 
				10, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		transacaoRepository.save(transacao);

		// Cria um token para o usuário logado.
		Authentication authentication = 
				new UsernamePasswordAuthenticationToken(usuario, usuario.getLogin());
		
		String token = tokenService.gerarToken(authentication);	
		
		// Dispara uma requisição para /relatorios levando o token gerado.
		MvcResult mvcResult = mvc
				.perform(get("/relatorios/carteira")
						.header("Authorization", "Bearer "+token))
			    .andReturn();
		
		// JSON de retorno.
		String json = "[{\"ticker\":\"XPTO1\",\"quantidade\":10,\"percentual\":100.00}]";
		
		assertEquals(json, mvcResult.getResponse().getContentAsString());
		
	}

}
