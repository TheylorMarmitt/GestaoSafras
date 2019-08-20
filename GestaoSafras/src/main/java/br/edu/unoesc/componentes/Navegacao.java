package br.edu.unoesc.componentes;

import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

public class Navegacao{

	private Tabs tabs = new Tabs();
	private Tab principal = new Tab("Início");
	private Tab tipoSafra = new Tab("Tipo de safra");
	private Tab addSafra = new Tab("Adicionar safra");
	private Tab procedimento = new Tab("Realizar Procedimento");
	private Tab colher = new Tab("Finalizar safra");
	private Tab editarUser = new Tab("Editar Usuário");	
	private Tab historico = new Tab("Histórico");
	private Tab sair = new Tab(new Icon(VaadinIcon.SIGN_OUT));
	
	public Component menu(int posicao) {
		HorizontalLayout menu = new HorizontalLayout();
		tabs.add(principal, tipoSafra, addSafra, procedimento, colher, historico, editarUser, sair);
		tabs.setWidth("100%");
		tabs.setFlexGrowForEnclosedTabs(1);

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

}
