package br.edu.unoesc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.unoesc.contatoemail.EmailUtil;
import br.edu.unoesc.model.Usuario;
import br.edu.unoesc.repositories.UsuarioRepository;
import br.edu.unoesc.security.SecurityUtils;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public Usuario buscaPorEmail(String email) {
		return this.repository.findByEmail(email);
	}
	
	public void salvar(Usuario usuario) {
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		this.repository.save(usuario);
	}
	
	public void salvaAposEdicao(Usuario user) {
		SecurityUtils.getUsuarioLogado().getUsuario().setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));
		this.repository.saveAndFlush(SecurityUtils.getUsuarioLogado().getUsuario());
	}
	
	public void atualizar(Usuario usuario) {
		Usuario user = buscaPorEmail(usuario.getEmail());
		String senha = enviaEmail(usuario.getEmail());
		System.out.println(senha);
		user.setSenha(new BCryptPasswordEncoder().encode(senha));
		this.repository.saveAndFlush(user);	
	}
	
	
	private String enviaEmail(String email) {
		try {
		return EmailUtil.enviarEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
