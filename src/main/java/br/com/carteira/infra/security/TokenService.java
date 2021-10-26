package br.com.carteira.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.carteira.model.Usuario;
import io.jsonwebtoken.Jwts;

@Service
public class TokenService {

	@Value("${jjwt.secret}")
	private String secret;
	
	
	public String gerarToken(Authentication authentication) {
		
		Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
		
		return Jwts
				.builder()
				.setId(usuarioLogado.getId().toString())
				.signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secret)
				.compact();
	}

}
