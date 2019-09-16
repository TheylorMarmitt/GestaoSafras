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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
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
import br.edu.unoesc.model.Colheita;
import br.edu.unoesc.model.Safra;
import br.edu.unoesc.repositories.ColheitaRepository;
import br.edu.unoesc.repositories.SafraRepository;
import br.edu.unoesc.security.SecurityUtils;

@PageTitle("Gestão de Safra")
@Route("finalizar")
@HtmlImport("frontend://styles/tema.html")
public class FinalizarSafra extends VerticalLayout {

	private static final long serialVersionUID = 7651511130449481242L;

	private FormLayout form = new FormLayout();
	private ComboBox<Safra> safra = new ComboBox<Safra>();
	private DatePicker dataColheita = new DatePicker();
	private NumberField qtdColhida = new NumberField();
	private NumberField valorVenda = new NumberField();

	private HorizontalLayout actions = new HorizontalLayout();
	private Navegacao nav = new Navegacao();
	private Button limpar = new Botoes().limparCampos();
	private Button finalizar = new Button("Finalizar");
	
	private ColheitaRepository repository;
	private SafraRepository safraRepository;
	
	@Autowired
	public FinalizarSafra(ColheitaRepository repository, SafraRepository safraRepository) {
		this.repository = repository;
		this.safraRepository = safraRepository;
		criar();
		binder();
		add(nav.menu(5), new H2("Finalizar Safra"), form, actions);
	}
	
	public void binder() {
		Binder<Colheita> binder = new Binder<>(Colheita.class);
		
		binder.forField(safra).asRequired("A seleção de safra é obrigatória").bind("safra");
		binder.forField(dataColheita).asRequired("A data é obrigatoria").bind("dataColheita");
		binder.forField(qtdColhida).asRequired("A quantidade colhida é obrigatório")
		.withValidator(new DoubleRangeValidator("A quantidade não é válida", 0.0, 9999999.0))
		.bind("qtdColhida");
		
		binder.forField(valorVenda).asRequired("O valor de venda é obrigatório")
		.withValidator(new DoubleRangeValidator("O valor não é válido", 0.0, 999999999.0))
		.bind("valorVenda");
		
		binder.addStatusChangeListener(status ->{
			finalizar.setEnabled(!status.hasValidationErrors());
		});
		
		finalizar.addClickListener(e ->{
				try {
					Colheita colheitaSalvo = new Colheita();
					binder.writeBean(colheitaSalvo);
					salvar(colheitaSalvo);
					Dialog sucesso = new DialogMensagem().finalizacaoSucedida(colheitaSalvo);
					sucesso.open();
					binder.readBean(null);
				} catch (ValidationException e1) {
					// fazer div com erros
					Dialog dialog = new DialogMensagem().erroForm();
					dialog.add(new Html(
						e1.getValidationErrors().stream().map(err ->
							"<p>" + err.getErrorMessage() + "</p>")
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

	private void salvar(Colheita colheitaSalvo) {
		Safra safra = colheitaSalvo.getSafra();
		safra.setEmAtividade(false);
		this.repository.save(colheitaSalvo);
		this.safraRepository.save(safra);
	}

	private void criar() {
		safra.setPlaceholder("safra");

		dataColheita.setI18n(new DataPickerPt().dataPt());

		qtdColhida.setPlaceholder("em sacas");
		qtdColhida.setValueChangeMode(ValueChangeMode.EAGER);
		
		valorVenda.setPlaceholder("R$ por saca");
		valorVenda.setValueChangeMode(ValueChangeMode.EAGER);
		finalizar.setThemeName("primary");
		descricaoForm();
		
		safra.setItems(this.safraRepository.findByEmAtividade(SecurityUtils.getUsuarioLogado().getCodigo()));
		actions.add(finalizar);
	}

	// adicionando descricaos nos campos do formulario
	private void descricaoForm() {
		safra.setLabel("Selecione a safra");
		dataColheita.setLabel("Data da colheita");
		qtdColhida.setLabel("Quantidade colhida");
		valorVenda.setLabel("Valor de venda");
		form.add(safra);
		form.add(dataColheita);
		form.add(qtdColhida);
		form.add(valorVenda);
	}

	// limpar campos
	private void limpar() {
		safra.clear();
		dataColheita.clear();
		qtdColhida.clear();
		valorVenda.clear();
	}
}
