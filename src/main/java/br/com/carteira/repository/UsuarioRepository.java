package br.com.carteira.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.carteira.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Optional<Usuario> findByLogin(String userName);

	@Query("SELECT u FROM Usuario u JOIN FETCH u.perfis WHERE u.id = :idUsuario")
	Optional<Usuario> carregarPorIdComPerfil(Integer idUsuario);

}
