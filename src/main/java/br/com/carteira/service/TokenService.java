package br.com.carteira.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.carteira.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class TokenService {

	@Value("${jjwt.secret}")
	private String secret;
	
	
	public String gerarToken(Authentication authentication) 
	{	
		Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
		
		return Jwts
				.builder()
				.setSubject(usuarioLogado.getId().toString())
				.signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public boolean isValid(String token) 
	{
		try {
			
			Jwts
			.parser()
			.setSigningKey(secret)
			.parseClaimsJws(token);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Integer extrairIdUsuario(String token) 
	{
		Claims claims = Jwts
		.parser()
		.setSigningKey(secret)
		.parseClaimsJws(token).getBody();
		
		return Integer.parseInt(claims.getSubject());		
	}

}
