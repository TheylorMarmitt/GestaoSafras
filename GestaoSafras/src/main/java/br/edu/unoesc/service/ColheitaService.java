package br.edu.unoesc.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.unoesc.model.Colheita;
import br.edu.unoesc.model.Safra;
import br.edu.unoesc.repositories.ColheitaRepository;
import br.edu.unoesc.repositories.SafraRepository;
import br.edu.unoesc.security.SecurityUtils;

@Service
public class ColheitaService {
	
	@Autowired
	private ColheitaRepository colheitaRepository;
	
	@Autowired
	private SafraRepository safraRepository;

	public Colheita ultimaColheita() {
		Colheita ultima =  this.colheitaRepository.findLastColheita(SecurityUtils.getUsuarioLogado().getCodigo());
		return ultima;
	}
	
	public List<Colheita> colheitas(){
		List<Colheita> colheitas = this.colheitaRepository.findColheitas(SecurityUtils.getUsuarioLogado().getCodigo());
		return colheitas;
	}
	
	@Transactional
	public void salvar(Colheita colheita) {
		Safra safra = colheita.getSafra();
		safra.setEmAtividade(false);
		this.colheitaRepository.save(colheita);
		this.safraRepository.save(safra);
	}
	
	public List<Colheita> colheitasPorUsuario(){
		return this.colheitaRepository.findColheitas(SecurityUtils.getUsuarioLogado().getCodigo());
	}
	
	public List<Colheita> colheitasEntreDatas(LocalDate dataInicio, LocalDate dataFim){
		return this.colheitaRepository.findColheitasEntreDatas(SecurityUtils.getUsuarioLogado().getCodigo(), dataInicio, dataFim);
	}
	
	public List<Colheita> colheitasPorTipo(String tipo){
		return this.colheitaRepository.findColheitasPorTipo(SecurityUtils.getUsuarioLogado().getCodigo(), tipo);
	}
	
	public List<Colheita> colheitasEntreDatasComTipo(LocalDate dataInicio, LocalDate dataFim, String tipo){
		return this.colheitaRepository.findColheitasFiltradas(SecurityUtils.getUsuarioLogado().getCodigo(), 
			tipo, dataInicio, dataFim);
	}
	
	public Colheita buscarPorCodigo(Long codigo) {
		return this.colheitaRepository.findByCodigo(codigo);
	}
	
	
	
}
