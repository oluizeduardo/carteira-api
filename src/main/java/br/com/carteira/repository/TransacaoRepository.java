package br.com.carteira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.carteira.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

}
