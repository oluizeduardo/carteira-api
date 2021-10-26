package br.com.carteira.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString(exclude = {"senha"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {
	
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NonNull
	private String nome;
	
	@NonNull
	private String login;
	
	@NonNull
	private String senha;

	
	public Usuario(String nome, String login) {
		this.nome = nome;
		this.login = login;
		setSenha();
	}
	
	
	public void setSenha()
	{
		String strSenha = new String("@"+nome.substring(0, 3).concat(login.substring(0, 3))); 
		//this.senha = bCryptPasswordEncoder.encode(strSenha);
		this.senha = new BCryptPasswordEncoder().encode(strSenha);
	}

	public void atualizarInformacoes(String nome, String login)
	{
		this.nome = nome;
		this.login = login;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {	
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	
	
}
