package br.com.carteira.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString(exclude = {"senha"})
@EqualsAndHashCode(of = "id")
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
	
	@ManyToMany
	@JoinTable(name = "perfis_usuarios", 
			   joinColumns = @JoinColumn(name = "usuario_id"), 
			   inverseJoinColumns = @JoinColumn(name = "perfil_id"))
	private List<Perfil> perfis = new ArrayList<Perfil>();

	
	public Usuario(String nome, String login) {
		this.nome = nome;
		this.login = login;
		setSenha();
	}
	
	
	public void setSenha()
	{
		//String strSenha = new String("@"+nome.substring(0, 3).concat(login.substring(0, 3))); 
		// TODO Implementar lógica de geração de senha.
		String strSenha = "abc123";
		
		// Senha criptografada.
		this.senha = new BCryptPasswordEncoder().encode(strSenha);
	}

	public void atualizarInformacoes(String nome, String login)
	{
		this.nome = nome;
		this.login = login;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.perfis;
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


	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil);	
	}

}
