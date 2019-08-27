package br.edu.unoesc.teste.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import br.edu.unoesc.model.Procedimento;
import br.edu.unoesc.model.Safra;
import br.edu.unoesc.model.TipoSafra;
import br.edu.unoesc.model.Usuario;

class TesteSafra {

	@Test
	void test() {
		Usuario user = new Usuario();
		user.setCodigo(1L);
		user.setDataNascimento(LocalDate.of(1990, 01, 01));
		user.setEmail("email@email");
		user.setNome("usuario");
		user.setSenha("123");
		user.setSobrenome("sobrenome");
		TipoSafra tp = new TipoSafra();
		tp.setNome("Safra1");
		tp.setUsuario(user);
		tp.setAtivo(true);
		tp.setCodigo(1L);
		
		Procedimento pr = new Procedimento();
		pr.setCodigo(1L);
		pr.setData(LocalDate.of(2019, 01, 01));
		pr.setDescricao("descricao");
		pr.setObservacao("obs");
		pr.setQuantidade(4.0);
		pr.setTipo("tipo");
		pr.setValorGasto(30.0);
		Set<Procedimento> procedimentos = new HashSet<Procedimento>();
		procedimentos.add(pr);
		
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
		safra.setProcedimentos(procedimentos);
		
		assertEquals(user, safra.getUsuario());
		assertEquals(tp, safra.getTipo());
		assertEquals(new Long(1), safra.getCodigo());
		assertEquals(LocalDate.of(2018, 01, 01), safra.getData());
		assertEquals("descricao", safra.getDescricao());
		assertEquals(true, safra.isEmAtividade());
		assertEquals("obs", safra.getObservacao());
		assertEquals(new Double(3.0), safra.getTamanho());
		assertEquals("tipo semente", safra.getTipoSemente());
		assertEquals(procedimentos, safra.getProcedimentos());
		assertEquals("01/01/2018", safra.dataFormatada());
		
		
	}

}
