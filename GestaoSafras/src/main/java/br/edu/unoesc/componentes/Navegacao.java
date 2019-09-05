package br.edu.unoesc.componentes;

import org.springframework.security.core.context.SecurityContextHolder;

import com.flowingcode.vaadin.addons.ironicons.IronIcons;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

public class Navegacao{

	private Tabs tabs = new Tabs();
	private Tab principal = criar("Início", IronIcons.HOME.create());
	private Tab tipoSafra = criar("Tipo de safra", IronIcons.LABEL.create());
	private Tab addSafra = criar("Adicionar Safra", IronIcons.ADD.create());
	private Tab procedimento = criar("Realizar Procedimento", IronIcons.ASSIGNMENT.create());
	private Tab colher = criar("Finalizar Safra", IronIcons.CHECK.create());
	private Tab editarUser = criar("Editar Usuário", IronIcons.ACCOUNT_CIRCLE.create());
	private Tab historico = criar("Histórico", IronIcons.HISTORY.create());
	private Tab sair = criar("Sair", new Icon(VaadinIcon.SIGN_OUT));
			
	public Component menu(int posicao) {
		HorizontalLayout menu = new HorizontalLayout();
		tabs.add(principal, tipoSafra, addSafra, procedimento, colher, historico, editarUser, sair);
		tabs.setWidth("100%");
		tabs.setFlexGrowForEnclosedTabs(7);

		menu.setWidth("100%");
		menu.add(tabs);
		
		switch (posicao) {
			case 1:
				tabs.setSelectedTab(principal);
				break;
			case 2:
				tabs.setSelectedTab(tipoSafra);
				break;
			case 3:
				tabs.setSelectedTab(addSafra);
				break;
			case 4:
				tabs.setSelectedTab(procedimento);
				break;
			case 5:
				tabs.setSelectedTab(colher);
				break;
			case 6:
				tabs.setSelectedTab(historico);
				break;
			case 7:
				tabs.setSelectedTab(editarUser);
				break;
		}

		tabs.addSelectedChangeListener(event -> {
			if (tabs.getSelectedTab().equals(principal)) {
				tabs.getSelectedTab().getUI().ifPresent(ui -> ui.navigate("inicio"));
			}
			if (tabs.getSelectedTab().equals(tipoSafra)) {
				tabs.getSelectedTab().getUI().ifPresent(ui -> ui.navigate("tipo-safra"));
			}
			if (tabs.getSelectedTab().equals(addSafra)) {
				tabs.getSelectedTab().getUI().ifPresent(ui -> ui.navigate("adicionar-safra"));
			}
			if (tabs.getSelectedTab().equals(procedimento)) {
				tabs.getSelectedTab().getUI().ifPresent(ui -> ui.navigate("procedimento"));
			}
			if (tabs.getSelectedTab().equals(colher)) {
				tabs.getSelectedTab().getUI().ifPresent(ui -> ui.navigate("finalizar"));
			}
			if (tabs.getSelectedTab().equals(historico)) {
				tabs.getSelectedTab().getUI().ifPresent(ui -> ui.navigate("historico"));
			}
			if (tabs.getSelectedTab().equals(editarUser)) {
				tabs.getSelectedTab().getUI().ifPresent(ui -> ui.navigate("editar-usuario"));
			}
			if (tabs.getSelectedTab().equals(sair)) {
				tabs.getSelectedTab().getUI().ifPresent(ui -> ui.navigate("login"));
			    SecurityContextHolder.clearContext();
			}
		});
		return menu;		
	}
	
	private Tab criar(String titulo, Component icon) {
		Tab t = new Tab();
		VerticalLayout vl = new VerticalLayout();
		vl.add(icon);
		Span s = new Span(titulo);
		s.getStyle().set("margin-top", "5px");
		s.getStyle().set("font-size", "14px");
		vl.add(s);
		vl.getStyle().set("height", "60px");
		vl.getStyle().set("padding-top", "0px");
		vl.getStyle().set("padding-bottom", "0px");
		vl.getStyle().set("align-items", "center");
		t.add(vl);
		t.getStyle().set("padding", "0px");
		return t;
	}
}
