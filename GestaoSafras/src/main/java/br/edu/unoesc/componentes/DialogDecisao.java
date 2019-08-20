package br.edu.unoesc.componentes;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;

import br.edu.unoesc.model.TipoSafra;
import br.edu.unoesc.repositories.TipoSafraRepository;
import br.edu.unoesc.security.SecurityUtils;

public class DialogDecisao {
	
	Dialog dialog = new Dialog();
	
	public Dialog excluir(Grid<TipoSafra> grid, TipoSafra tipo, TipoSafraRepository repository) {
		dialog.setWidth("300px");
		dialog.setHeight("100px");
		
		Div d1 = new Div();
		Label men = new Label("Tem certeza que deseja excluir este tipo de safra ?");
		d1.getStyle().set("color", "#25621e");
		d1.getStyle().set("text-align", "center");		
		d1.setWidth("100%");
		d1.add(men);
		
		Div d3 = new Div();
		Button fechar = new Button("Fechar");
		fechar.setThemeName("secondary");
		fechar.getStyle().set("margin-right", "20px");
		
		Button sim = new Button("Excluir");
		sim.getStyle().set("background-color", "red");
		sim.getStyle().set("color", "#fff");
		sim.setAutofocus(true);
		
		d3.getStyle().set("padding-top", "5%");
		d3.getStyle().set("text-align", "center");
		d3.add(fechar, sim);
		
		d3.setWidthFull();
		
		fechar.addClickListener(e ->{
			dialog.close();
		});
		
		sim.addClickListener(e ->{
			tipo.setAtivo(false);
			repository.saveAndFlush(tipo);
			grid.setItems(repository.findByAtivos(SecurityUtils.getUsuarioLogado().getCodigo()));
			dialog.close();
		});
		
		dialog.add(d1, d3);
		return dialog;
		
	}

}
