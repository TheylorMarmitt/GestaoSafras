package br.edu.unoesc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unoesc.model.Safra;
import br.edu.unoesc.repositories.SafraRepository;
import br.edu.unoesc.security.SecurityUtils;

@Service
public class SafraService {
	
	@Autowired
	SafraRepository safraRepository;
	
	public void salvar(Safra safra) {
		safra.setEmAtividade(true);
		safra.setUsuario(SecurityUtils.getUsuarioLogado().getUsuario());
		this.safraRepository.save(safra);
	}
	
	public List<Safra> todasEmAtividade(){
		return this.safraRepository.findByEmAtividade(SecurityUtils.getUsuarioLogado().getCodigo());
	}

}
