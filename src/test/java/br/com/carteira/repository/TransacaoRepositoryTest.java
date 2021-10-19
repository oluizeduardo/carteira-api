package br.com.carteira.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.carteira.dto.ItemCarteiraDTO;
import br.com.carteira.model.TipoTransacao;
import br.com.carteira.model.Transacao;
import br.com.carteira.model.Usuario;

// Carrega as configurações da JPA na classe de teste.
@DataJpaTest

// Carrega as funções do Spring antes de inicializar o JUnit.
@ExtendWith(SpringExtension.class)

// Não troca pelo banco em memória, usa o banco original da aplicação (Mysql).
@AutoConfigureTestDatabase(replace = Replace.NONE)

// Indica qual arquivo .propoerties deve ser carregado. Gerar banco de teste.
@ActiveProfiles("test")
class TransacaoRepositoryTest {

	
	@Autowired
	private TransacaoRepository repository;
	
	@Autowired
	private TestEntityManager em;
	
	
	@Test
	void deveriaRetornarRelatorioCarteiraDeInvestimentos() {
		
		Usuario usuario = new Usuario("Rafaela", "rafa@gmail.com");
		em.persist(usuario);
		
		Transacao t1 = new Transacao("XPTO1", 
				new BigDecimal("10.20"), 
				10, 
				LocalDate.now(), 
				TipoTransacao.VENDA, 
				usuario);
		em.persist(t1);
		
		Transacao t2 = new Transacao("XPTO1", 
				new BigDecimal("10.20"), 
				10, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		em.persist(t2);
		
		Transacao t3 = new Transacao("XPTO5", 
				new BigDecimal("10.20"), 
				10, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		em.persist(t3);
		
		List<ItemCarteiraDTO> relatorio = repository.relatorioCarteiraDeInvestimentos();
		
		Assertions
		.assertThat(relatorio)
		.hasSize(2).extracting(ItemCarteiraDTO::getTicker, 
				ItemCarteiraDTO::getQuantidade, 
				ItemCarteiraDTO::getPercentual)
		.containsExactlyInAnyOrder(
				Assertions.tuple("XPTO1", 20l, 0.666667),
				Assertions.tuple("XPTO5", 10l, 0.333333)
				);

	}

}
