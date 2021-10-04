package br.com.carteira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.carteira.dto.ItemCarteiraDTO;
import br.com.carteira.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

	@Query("SELECT new br.com.carteira.dto.ItemCarteiraDTO("
			+ "t.ticker, "
			+ "sum(t.quantidade), "
			+ "sum(t.quantidade) * 1.0 / (SELECT sum(t2.quantidade) FROM Transacao t2) *1.0) "
			+ "from Transacao t "
			+ "GROUP BY t.ticker")
	List<ItemCarteiraDTO> relatorioCarteiraDeInvestimentos();
	
}
