package br.edu.unoesc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import br.edu.unoesc.model.Usuario;
import br.edu.unoesc.service.UsuarioService;

@Component
public class ImplementsUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioService service;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		Usuario usuario  = service.buscaPorEmail(email);
		if(usuario == null) {
			return null;			
		} 
		return new UsuarioLogado(usuario);
	}

}
