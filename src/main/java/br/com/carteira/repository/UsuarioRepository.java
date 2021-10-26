package br.com.carteira.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.carteira.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Optional<Usuario> findByLogin(String userName);

}
