package br.edu.unoesc.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import br.edu.unoesc.componentes.Botoes;
import br.edu.unoesc.componentes.Navegacao;
import br.edu.unoesc.idioma.DataPickerPt;
import br.edu.unoesc.model.Colheita;
import br.edu.unoesc.repositories.ColheitaRepository;
import br.edu.unoesc.security.SecurityUtils;

@PageTitle("Gestão de Safra")
@Route("historico")
@HtmlImport("frontend://styles/tema.html")
public class Historico extends VerticalLayout {

	private static final long serialVersionUID = 4566219162908209696L;

	private Grid<Colheita> grid = new Grid<>();
	private FormLayout form = new FormLayout();
	private TextField tipo = new TextField();
	private DatePicker dataInicio= new DatePicker();
	private DatePicker dataFim= new DatePicker();
	private Navegacao nav = new Navegacao();
	private Button filtra = new Botoes().filtrar(); 

	private ColheitaRepository repository;
	
	@Autowired
	public Historico(ColheitaRepository repository) {
		this.repository = repository;

		criarGrid();
		criarForm();
		filtros();
		add(nav.menu(6), new H2("Histórico"), form, grid);
	}
	
	private void filtros() {
		filtra.addClickListener(e -> {
			if(tipo.isEmpty()) {
				if(dataInicio.isEmpty() && dataFim.isEmpty()) {
					grid.setItems(this.repository.findColheitas(SecurityUtils.getUsuarioLogado().getCodigo()));
				}else {
					grid.setItems(this.repository.findColheitasEntreDatas(SecurityUtils.getUsuarioLogado().getCodigo(), 
							dataInicio.getValue(), dataFim.getValue()));
				}
			}else {
				if(dataInicio.isEmpty() && dataFim.isEmpty()) {
					grid.setItems(this.repository.findColheitasPorTipo(SecurityUtils.getUsuarioLogado().getCodigo(), 
							tipo.getValue()));
				}else {
					grid.setItems(this.repository.findColheitasFiltradas(SecurityUtils.getUsuarioLogado().getCodigo(), 
							tipo.getValue(), dataInicio.getValue(), dataFim.getValue()));
				}
			}
		});		
	}

	private void criarForm() {
		tipo.setLabel("Tipo");
		dataInicio.setLabel("Data inicial");
		dataFim.setLabel("Data final");
		tipo.setPlaceholder("Tipo da safra");
		
		tipo.setSizeFull();
		dataInicio.setSizeFull();
		dataFim.setSizeFull();
		filtra.setSizeFull();
		
		dataInicio.setI18n(new DataPickerPt().dataPt());
		dataFim.setI18n(new DataPickerPt().dataPt());
		form.add(tipo, dataInicio, dataFim, filtra);
		form.setResponsiveSteps(new ResponsiveStep("450px", 1),
								new ResponsiveStep("720px", 2),
								new ResponsiveStep("940px", 4));
	}

	private void criarGrid() {
		grid.setItems(this.repository.findColheitas(SecurityUtils.getUsuarioLogado().getCodigo()));
		grid.addColumn(Colheita::tipo).setHeader("Tipo");
		grid.addColumn(Colheita::dataFormatada).setHeader("Data da colheita");
		grid.addColumn(Colheita::getQtdColhida).setHeader("Quantidade colhida");
		grid.addColumn(Colheita::lucros).setHeader("Lucros");
		grid.addComponentColumn(item -> createButton(grid, item)).setHeader("Ver Procedimentos");
		grid.setSelectionMode(Grid.SelectionMode.NONE);
		grid.getColumns().forEach(column -> column.setAutoWidth(true));
		grid.recalculateColumnWidths();
	}
	
	private Button createButton(Grid<Colheita> grid, Colheita item) {
		Button button = new Button("Procedimentos", clickEvent -> {
			this.grid.getUI().ifPresent(ui -> ui.navigate("historico-procedimentos/"+item.getCodigo()));
		});
		return button;
	}
}
