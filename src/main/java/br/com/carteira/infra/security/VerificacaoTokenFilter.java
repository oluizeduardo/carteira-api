package br.com.carteira.infra.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.carteira.model.Usuario;
import br.com.carteira.repository.UsuarioRepository;
import br.com.carteira.service.TokenService;

public class VerificacaoTokenFilter extends OncePerRequestFilter {

	
	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;

	
	public VerificacaoTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = request.getHeader("Authorization");
		
		if(token == null || token.isBlank()) 
		{
			filterChain.doFilter(request, response);
			return;
		}
		
		token = token.replace("Bearer ", "");
		
		boolean tokenValido = tokenService.isValid(token);
		
		if(tokenValido)
		{
			Integer idUsuario = tokenService.extrairIdUsuario(token);
			
			Usuario usuario = usuarioRepository.carregarPorIdComPerfil(idUsuario).get();
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
		
	}

}
