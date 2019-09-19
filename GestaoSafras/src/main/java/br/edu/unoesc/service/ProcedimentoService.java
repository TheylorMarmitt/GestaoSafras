package br.edu.unoesc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.unoesc.model.Procedimento;
import br.edu.unoesc.model.Safra;
import br.edu.unoesc.repositories.ProcedimentoRepository;
import br.edu.unoesc.repositories.SafraRepository;

@Service
public class ProcedimentoService {

	@Autowired
	private ProcedimentoRepository procedRepository;
	
	@Autowired
	private SafraRepository safraRepository;
	
	public void salvar(Procedimento procedimento) {
		Safra safra = procedimento.getSafra();
		safra.adicionaProcedimento(procedimento);
		this.procedRepository.saveAndFlush(procedimento);
		this.safraRepository.saveAndFlush(safra);
	}
	
}
