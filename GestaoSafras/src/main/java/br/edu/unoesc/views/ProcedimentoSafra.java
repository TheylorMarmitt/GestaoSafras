package br.edu.unoesc.views;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.DoubleRangeValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import br.edu.unoesc.componentes.Botoes;
import br.edu.unoesc.componentes.DialogMensagem;
import br.edu.unoesc.componentes.Navegacao;
import br.edu.unoesc.idioma.DataPickerPt;
import br.edu.unoesc.model.Procedimento;
import br.edu.unoesc.model.Safra;
import br.edu.unoesc.service.ProcedimentoService;
import br.edu.unoesc.service.SafraService;

@PageTitle("Gestão Safras")
@Route("procedimento")
@HtmlImport("frontend://styles/tema.html")
public class ProcedimentoSafra extends VerticalLayout {

	private static final long serialVersionUID = -5204749799997834952L;

	private FormLayout form = new FormLayout();
	private ComboBox<Safra> safra = new ComboBox<>();
	private TextField descricao = new TextField();
	private TextField tipo = new TextField();
	private DatePicker dataP = new DatePicker();
	private NumberField quantidade = new NumberField();
	private NumberField valor = new NumberField();
	private TextArea obs = new TextArea();
	private HorizontalLayout actions = new HorizontalLayout();

	private Button salvar = new Botoes().salvar();
	private Button limpar = new Botoes().limparCampos();
	private Navegacao nav = new Navegacao();

	private ProcedimentoService procedService;
	private SafraService safraService;
	
	@Autowired
	public ProcedimentoSafra(ProcedimentoService procedService, SafraService safraService) {
		this.procedService = procedService;
		this.safraService = safraService;
		
		criar();
		safra.setItems(this.safraService.todasEmAtividade());
		binder();
		
		add(nav.menu(4),new H2("Realizar Procedimento"), form, actions);
	}
	
	public void binder() {
		Binder<Procedimento> binder = new Binder<>(Procedimento.class);
		
		binder.forField(safra).asRequired("A seleção de safra é obrigatória").bind("safra");
		binder.forField(descricao).asRequired("A descrição é obrigatória").bind("descricao");
		binder.forField(tipo).asRequired("O tipo é obrigatório").bind("tipo");
		binder.forField(dataP).asRequired("A data é obrigatória").bind("data");
		
		binder.forField(quantidade).asRequired("A quantidade do produto é obrigatório")
		.withValidator(new DoubleRangeValidator("A quantidade não é válida", 0.0, 9999999.0))
		.bind("quantidade");
		
		binder.forField(valor).asRequired("O valor gasto é obrigatório")
		.withValidator(new DoubleRangeValidator("O valor não é válida", 0.0, 999999999.0))
		.bind("valorGasto");
		
		binder.forField(obs).bind("observacao");
		
		binder.addStatusChangeListener(status ->{
			salvar.setEnabled(!status.hasValidationErrors());
		});
		
		salvar.addClickListener(e ->{
				try {
					Procedimento procedimentoSalvo = new Procedimento();
					binder.writeBean(procedimentoSalvo);
					salvar(procedimentoSalvo);
					Dialog sucesso = new DialogMensagem().sucesso("Procedimento salvo com sucesso");
					sucesso.open();
					binder.readBean(null);
				} catch (ValidationException e1) {
					// fazer div com erros
					Dialog dialog = new DialogMensagem().erroForm();
					dialog.add(new Html(
						e1.getValidationErrors().stream().map(err ->
							"<p> • " + err.getErrorMessage() + "</p>")
						.collect(Collectors.joining("\n"))
						)
					);
					dialog.open();
				}
		});
		
		limpar.addClickListener(event -> {
			limpar();
		});
		
	}

	private void salvar(Procedimento procedimentoSalvo) {
		this.procedService.salvar(procedimentoSalvo);
	}

	private void criar() {
		safra.setPlaceholder("Selecione a safra");
		safra.setAutofocus(true);
		
		descricao.setValueChangeMode(ValueChangeMode.EAGER);
		descricao.setPlaceholder("Ex: Pulverização");

		tipo.setPlaceholder("Ex: inseticida");
		tipo.setValueChangeMode(ValueChangeMode.EAGER);

		dataP.setPlaceholder("Data do procedimento");
		dataP.setI18n(new DataPickerPt().dataPt());

		quantidade.setPlaceholder("Produto aplicado");
		quantidade.setValueChangeMode(ValueChangeMode.EAGER);
		
		valor.setPlaceholder("Gasto do procedimento");
		valor.setValueChangeMode(ValueChangeMode.EAGER);

		obs.setPlaceholder("Complemento");
		obs.setValueChangeMode(ValueChangeMode.EAGER);
		criarFormulario();
		
		actions.add(salvar, limpar);
	}

	private void criarFormulario() {
		Label lb = new Label();
		safra.setLabel("Safra");
		descricao.setLabel("Descrição");
		tipo.setLabel("Tipo");
		dataP.setLabel("Data do procedimento");
		quantidade.setLabel("Quantidade aplicada");
		valor.setLabel("Valor total gasto");
		obs.setLabel("Observação");
		
		form.add(safra);
		form.add(lb);
		form.add(descricao);
		form.add(tipo);
		form.add(dataP);
		form.add(quantidade);
		form.add(valor);
		form.add(obs);
	}

	private void limpar() {
		safra.clear();
		descricao.clear();
		tipo.clear();
		dataP.clear();
		quantidade.clear();
		valor.clear();
		obs.clear();
	}
}
