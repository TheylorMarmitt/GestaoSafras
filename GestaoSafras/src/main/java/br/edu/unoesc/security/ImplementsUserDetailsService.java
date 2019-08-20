package br.edu.unoesc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import br.edu.unoesc.model.Usuario;
import br.edu.unoesc.repositories.UsuarioRepository;

@Component
public class ImplementsUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		Usuario usuario  = repository.findByEmail(email);
		if(usuario == null) {
			return null;			
		} 
		return new UsuarioLogado(usuario);
	}

}
 