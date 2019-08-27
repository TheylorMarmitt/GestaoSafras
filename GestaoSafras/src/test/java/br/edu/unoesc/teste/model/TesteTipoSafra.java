package br.edu.unoesc.teste.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.edu.unoesc.model.TipoSafra;
import br.edu.unoesc.model.Usuario;

class TesteTipoSafra {

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
		
		assertEquals(user, tp.getUsuario());
		assertEquals("Safra1", tp.getNome());
		assertEquals(new Long(1), tp.getCodigo());
		assertEquals(true, tp.isAtivo());
		
	}

}
