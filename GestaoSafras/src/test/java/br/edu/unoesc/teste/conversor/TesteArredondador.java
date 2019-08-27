package br.edu.unoesc.teste.conversor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.edu.unoesc.conversor.Arredondador;

class TesteArredondador {

	@Test
	void test() {
		Arredondador a = new Arredondador();
		
		assertEquals("1,000,123.00", a.arredondar(1000123.00));
		assertEquals("34.21", a.arredondar(34.21));
		assertEquals("100.00", a.arredondar(new Double(100)));
	}
	
}
