package br.edu.unoesc.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.unoesc.model.Usuario;

public class UsuarioLogado implements UserDetails{


	private static final long serialVersionUID = -9113820510638475011L;

	private final Usuario usuarioLogado;
	
	public UsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public Usuario getUsuario() {
		return this.usuarioLogado;
	}
	
	public Long getCodigo() {
		return this.usuarioLogado.getCodigo();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.usuarioLogado.getSenha();
	}

	@Override
	public String getUsername() {
		return this.usuarioLogado.getEmail();
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
