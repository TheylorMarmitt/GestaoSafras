package br.edu.unoesc.teste.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.edu.unoesc.model.Procedimento;
import br.edu.unoesc.model.Safra;
import br.edu.unoesc.model.TipoSafra;
import br.edu.unoesc.model.Usuario;

class TesteProcedimento {

	@Test
	void test() {
		Usuario user = new Usuario(1L, "nome", "sobrenome", "email", "senha", LocalDate.of(1990, 01, 01));
		TipoSafra tp = new TipoSafra(1L, "safra1");
		tp.setUsuario(user);
		tp.setAtivo(true);
		Safra safra = new Safra();
		safra.setUsuario(user);
		safra.setTipo(tp);
		safra.setCodigo(1L);
		safra.setData(LocalDate.of(2018, 01, 01));
		safra.setDescricao("descricao");
		safra.setEmAtividade(true);
		safra.setObservacao("obs");
		safra.setTamanho(3.0);
		safra.setTipoSemente("tipo semente");
		
		Procedimento pr = new Procedimento();
		pr.setCodigo(1L);
		pr.setData(LocalDate.of(2019, 01, 01));
		pr.setDescricao("descricao");
		pr.setObservacao("obs");
		pr.setQuantidade(4.0);
		pr.setSafra(safra);
		pr.setTipo("tipo");
		pr.setValorGasto(30.0);
		
		assertEquals(user, pr.getSafra().getUsuario());
		assertEquals(new Long(1), pr.getCodigo());
		assertEquals(LocalDate.of(2019, 01, 01), pr.getData());
		assertEquals("descricao", pr.getDescricao());
		assertEquals("obs", pr.getObservacao());
		assertEquals(new Double(4.0), pr.getQuantidade());
		assertEquals(safra, pr.getSafra());
		assertEquals("tipo", pr.getTipo());
		assertEquals(new Double(30.0), pr.getValorGasto());
		assertEquals("01/01/2019", pr.dataFormatada());
		
	}

}
