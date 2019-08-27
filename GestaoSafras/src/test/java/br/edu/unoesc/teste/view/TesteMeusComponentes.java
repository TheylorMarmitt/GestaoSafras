package br.edu.unoesc.teste.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import br.edu.unoesc.componentes.Botoes;
import br.edu.unoesc.componentes.Navegacao;

class TesteMeusComponentes {

	@Test
	void navegacao() {
		Navegacao n = new Navegacao();
		assertEquals(HorizontalLayout.class, n.menu(1).getClass());
	}
	
	@Test
	void botoes() {
		Botoes b = new Botoes();
		
		assertEquals(" Editar ", b.editar().getText());
		assertEquals(new Icon(VaadinIcon.PENCIL).getClass(), b.editar().getIcon().getClass());
		
		assertEquals(" Salvar ", b.salvar().getText());
		assertEquals("primary", b.salvar().getThemeName());
		assertEquals(false, b.salvar().isEnabled());
		
		assertEquals(" Limpar campos ", b.limparCampos().getText());
		assertEquals("secondary", b.limparCampos().getThemeName());
		
		assertEquals(" Voltar ", b.voltar().getText());
		
		assertEquals(" Ir para o Início ", b.inicio().getText());
		assertEquals(" Voltar para o histórico ", b.voltaHistorico().getText());
	}

}
