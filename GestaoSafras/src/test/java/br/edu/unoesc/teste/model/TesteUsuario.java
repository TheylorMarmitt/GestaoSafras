package br.edu.unoesc.teste.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import br.edu.unoesc.model.Safra;
import br.edu.unoesc.model.Usuario;

class TesteUsuario {

	@Test
	void test() {
		Usuario user = new Usuario();
		Safra safra = new Safra();
		Set<Safra> safras = new HashSet<Safra>();
		safras.add(safra);
		
		user.setCodigo(1L);
		user.setDataNascimento(LocalDate.of(1990, 01, 01));
		user.setEmail("email@email");
		user.setNome("usuario");
		user.setSenha("123");
		user.setSobrenome("sobrenome");
		
		assertEquals("usuario", user.getNome(), "nome igual");
		assertEquals("123", user.getSenha(), "senha igual");
		assertEquals("sobrenome", user.getSobrenome(), "sobrenome igual");
		assertEquals("email@email", user.getEmail(), "email igual");
		assertEquals(new Long(1), user.getCodigo());

	}

}
