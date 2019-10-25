package br.edu.unoesc.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import br.edu.unoesc.componentes.Botoes;
import br.edu.unoesc.componentes.DialogDecisao;
import br.edu.unoesc.componentes.DialogMensagem;
import br.edu.unoesc.componentes.Navegacao;
import br.edu.unoesc.model.TipoSafra;
import br.edu.unoesc.service.TipoSafraService;

@PageTitle("Gestão de Safra")
@Route("tipo-safra")
@HtmlImport("frontend://styles/tema.html")
public class TipoSafraView extends VerticalLayout {

	private static final long serialVersionUID = -4699539674940062252L;

	private TextField nome = new TextField();
	private FormLayout form = new FormLayout();
	private Grid<TipoSafra> grid = new Grid<>();
	private HorizontalLayout actions = new HorizontalLayout();
	private Navegacao nav = new Navegacao();
	private Button salvar = new Botoes().salvar();
	private Button novo = new Botoes().novo();
	
	//	edição
	private TipoSafra tiposafra;
	private boolean editando = false;
	
	private TipoSafraService tipoService;

	@Autowired
	public TipoSafraView(TipoSafraService tipoService) {
		this.tipoService = tipoService;
		criar();
		binder();
		add(nav.menu(2), new H2("Tipo de Safra"), form, actions, grid);
	}
	
	public void binder() {
		Binder<TipoSafra> binder = new Binder<>(TipoSafra.class);
		
		binder.forField(nome).asRequired("O nome é obrigatório").bind("nome");
		
		binder.addStatusChangeListener(status ->{
			salvar.setEnabled(!status.hasValidationErrors());
		});
		
		binder.readBean(new TipoSafra());
		
		salvar.addClickListener(e ->{
			try {
				TipoSafra tipoSalvo = new TipoSafra();
				binder.writeBean(tipoSalvo);
				salvar(tipoSalvo);
				binder.readBean(new TipoSafra());
			} catch (ValidationException e1) {
				
			}
		});
		
		novo.addClickListener(e -> {
			editando = false;
			nome.clear();
			nome.focus();
		});
	}

	private void salvar(TipoSafra tipoSalvo) {
		if (editando) {
			this.tiposafra.setNome(tipoSalvo.getNome());
			this.tipoService.atualizar(this.tiposafra);
			grid.setItems(this.tipoService.tiposAtivos());
		} else {
			this.tipoService.salvar(nome.getValue());
			Dialog sucesso = new DialogMensagem().sucesso("Tipo salvo com sucesso");
			sucesso.open();
			grid.setItems(this.tipoService.tiposAtivos());
			nome.clear();
		}
		editando = false; 
	}

	private Button createEditButton(Grid<TipoSafra> grid, TipoSafra item) {
		Button button = new Botoes().editar();
		button.addClickListener(e -> {
			nome.setValue(item.getNome());
			editando = true;
			this.tiposafra = item;
		});
		return button;
	}

	private Button createRemoveButton(Grid<TipoSafra> grid, TipoSafra item) {
		Button button = new Botoes().excluir();
		button.addClickListener(e -> {
			Dialog dialog = new DialogDecisao().excluir(grid, item, this.tipoService);
			dialog.open();
		});
		button.getStyle().set("color", "#fff");
		button.getStyle().set("background-color", "#e60000");
		return button;
	}

	private void criar() {
		nome.setPlaceholder("nome da safra");
		nome.setAutofocus(true);
		nome.setValueChangeMode(ValueChangeMode.EAGER);
		nome.setLabel("Nome");
		
		form.add(nome);
		form.setResponsiveSteps(new ResponsiveStep("450px", 1),
				new ResponsiveStep("720px", 3),
				new ResponsiveStep("940px", 4));
		
		grid.setItems(this.tipoService.tiposAtivos());
		grid.addColumn(TipoSafra::getNome).setHeader("Nome");
		grid.addComponentColumn(item -> createEditButton(grid, item)).setHeader("Editar Item");
		grid.addComponentColumn(item -> createRemoveButton(grid, item)).setHeader("Remover Item");
		grid.setSelectionMode(Grid.SelectionMode.NONE);
		grid.getColumns().forEach(column -> column.setAutoWidth(true));
		grid.recalculateColumnWidths();
		
		actions.add(salvar, novo);
	}
}
