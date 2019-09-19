package br.edu.unoesc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unoesc.model.TipoSafra;
import br.edu.unoesc.repositories.TipoSafraRepository;
import br.edu.unoesc.security.SecurityUtils;

@Service
public class TipoSafraService {

	@Autowired
	TipoSafraRepository tipoRepository;
	
	public List<TipoSafra> tiposAtivos(){
		return this.tipoRepository.findByAtivos(SecurityUtils.getUsuarioLogado().getCodigo());
	}
	
	public void atualizar(TipoSafra tipo) {
		this.tipoRepository.saveAndFlush(tipo);
	}
	
	public void salvar(String nome) {
		TipoSafra tipo = new TipoSafra();
		tipo.setNome(nome);
		tipo.setAtivo(true);
		tipo.setUsuario(SecurityUtils.getUsuarioLogado().getUsuario());
		this.tipoRepository.saveAndFlush(tipo);
	}
	
	public void desativando(TipoSafra tipo) {
		tipo.setAtivo(false);
		tipoRepository.saveAndFlush(tipo);
	}
	
}
