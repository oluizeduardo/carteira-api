package br.com.carteira.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

//Carrega as funções do Spring antes de inicializar o JUnit.
@ExtendWith(SpringExtension.class)

// Carrega todas as configurações do projeto, não somente a camada de persistência.
@SpringBootTest

// Configura o MockMvc para injeção nos testes.
@AutoConfigureMockMvc

//Indica qual arquivo .propoerties deve ser carregado. Gerar banco de teste.
@ActiveProfiles("test")

// Executa em contexto transacional. Faz o rollback ao final de cada teste.
@Transactional
class UsuarioControllerTest {

	/**
	 * Objeto que simula as requisições HTTP.
	 */
	@Autowired
	private MockMvc mvc;
	
	@Test
	void naoDeveriaCadastrarUsuarioComDadosIncompletos() throws Exception 
	{
		
		String json = "{}";
		
		mvc
			.perform(post("/usuarios")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
			.andExpect(status().isBadRequest());		
	}
	
	
	@Test
	void deveriaCadastrarUsuarioComDadosCompletos() throws Exception 
	{
		
		String json = "{\"nome\":\"pessoa\",\"login\":\"pessoa@email.com\"}";
		
		mvc
			.perform(post("/usuarios")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
			.andExpect(status().isCreated())
			.andExpect(header().exists("Location"))
			.andExpect(content().json(json));		
	}

}
