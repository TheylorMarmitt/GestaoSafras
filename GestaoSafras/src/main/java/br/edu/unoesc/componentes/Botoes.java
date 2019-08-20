package br.edu.unoesc.componentes;

import com.flowingcode.vaadin.addons.ironicons.IronIcons;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class Botoes {
	
	public Button salvar() {
		Button salvar = new Button(" Salvar ", IronIcons.SAVE.create());
		salvar.setThemeName("primary");
		salvar.setEnabled(false);
		return salvar;
	}
	
	public Button limparCampos() {
		Button limpar = new Button(" Limpar campos ", new Icon(VaadinIcon.CLOSE_CIRCLE_O));
		limpar.setThemeName("secondary");
		return limpar;
	}
	
	public Button voltar() {
		Button voltar = new Button(" Voltar ", new Icon(VaadinIcon.ARROW_BACKWARD));
		return voltar;
	}
	
	public Button novo() {
		Button novo = new Button(" Novo ", new Icon(VaadinIcon.PLUS_CIRCLE));
		return novo;
	}
	
	public Button filtrar() {
		Button filtrar = new Button(" Filtrar ", new Icon(VaadinIcon.SEARCH));
		filtrar.setThemeName("primary");
		return filtrar;
	}
	
	public Button recuperar() {
		Button recuperar = new Button(" Recuperar ", new Icon(VaadinIcon.ENVELOPE_O));
		recuperar.setThemeName("primary");
		return recuperar;
	}
	
	public Button editar() {
		Button botao = new Button(" Editar ", new Icon(VaadinIcon.PENCIL));
		return botao;
	}
	
	public Button excluir() {
		Button botao = new Button(" Excluir ", new Icon(VaadinIcon.TRASH));
		return botao;
	}
	
	public Button inicio() {
		Button botao = new Button(" Ir para o Início ", new Icon(VaadinIcon.HOME_O));
		botao.getStyle().set("background-color", "#fff");
		botao.getStyle().set("color", "#9e0000");
		return botao;
	}
	
	public Button voltaHistorico() {
		Button botao = new Button(" Voltar para o histórico ", new Icon(VaadinIcon.ARROW_BACKWARD));
		botao.getStyle().set("background-color", "#fff");
		botao.getStyle().set("color", "#eb7900");
		return botao;
	}
	
	

}
