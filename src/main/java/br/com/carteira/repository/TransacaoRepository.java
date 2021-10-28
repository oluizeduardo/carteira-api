package br.com.carteira.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.carteira.dto.ItemCarteiraDTO;
import br.com.carteira.model.Transacao;
import br.com.carteira.model.Usuario;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

	@Query("SELECT new br.com.carteira.dto.ItemCarteiraDTO("
			// ticker
			+ "t.ticker, "
			// quantidade de cada item comprado.
			+ "SUM(CASE WHEN(t.tipo = 'COMPRA') THEN t.quantidade ELSE (t.quantidade * -1) END), "
			// quantidade total
			+ "(SELECT SUM(CASE WHEN(t2.tipo = 'COMPRA') THEN t2.quantidade ELSE (t2.quantidade * -1) END) FROM Transacao t2)) "
			+ "FROM Transacao t "
			+ "GROUP BY t.ticker")
	List<ItemCarteiraDTO> relatorioCarteiraDeInvestimentos();

	Page<Transacao> findAllByUsuario(Pageable paginacao, Usuario usuario);

	
}
