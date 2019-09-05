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
import br.edu.unoesc.model.Safra;
import br.edu.unoesc.model.TipoSafra;
import br.edu.unoesc.repositories.SafraRepository;
import br.edu.unoesc.repositories.TipoSafraRepository;
import br.edu.unoesc.security.SecurityUtils;

@PageTitle("Gestão de Safra")
@Route("adicionar-safra")
@HtmlImport("frontend://styles/tema.html")
public class AdicionarSafra extends VerticalLayout {

	private static final long serialVersionUID = -758224394683384330L;

	private FormLayout form = new FormLayout();
	private TextField descricao = new TextField();
	private ComboBox<TipoSafra> tipo = new ComboBox<>();
	private DatePicker data= new DatePicker();
	private NumberField tamanhoPlantacao = new NumberField();
	private TextField tipoSemente = new TextField();
	private TextArea observacao = new TextArea();
	private HorizontalLayout actions = new HorizontalLayout();
	private Button limpar = new Botoes().limparCampos();
	private Button salvar = new Botoes().salvar();
	private Navegacao nav = new Navegacao();
	
	private SafraRepository repository;
	private TipoSafraRepository tipoRepository;
	
	@Autowired
	public AdicionarSafra(SafraRepository repository, TipoSafraRepository tipoRepository) {
		this.repository = repository;
		this.tipoRepository = tipoRepository;
		
		criar();
		binder();
		add(nav.menu(3), new H2("Adicionar safra"), form, actions);

	}

	public void criar() {
		descricao.setPlaceholder("Propriedade, Local, Condição");
		descricao.setAutofocus(true);
		descricao.setValueChangeMode(ValueChangeMode.EAGER);
		
		data.setPlaceholder("Início da safra");
		data.setI18n(new DataPickerPt().dataPt());

		tipo.setItems(this.tipoRepository.findByAtivos(SecurityUtils.getUsuarioLogado().getCodigo()));
		
		tamanhoPlantacao.setPlaceholder("Hectares");
		tamanhoPlantacao.setValueChangeMode(ValueChangeMode.EAGER);

		tipoSemente.setPlaceholder("marca ou tipo");
		tipoSemente.setValueChangeMode(ValueChangeMode.EAGER);
		tipoSemente.setPlaceholder("Informações extras");

		observacao.setValueChangeMode(ValueChangeMode.EAGER);
		descricaoForm();
		actions.add(salvar, limpar);
	}

	public void binder() {
		Binder<Safra> binder = new Binder<>(Safra.class);
		
		binder.forField(descricao).asRequired("A descrição é obrigatória").bind("descricao");
		binder.forField(data).asRequired("A data é obrigatoria").bind("data");
		binder.forField(tipo).asRequired("O tipo é obrigatório").bind("tipo");
		binder.forField(tamanhoPlantacao).asRequired("O tamanho é obrigatório")
		.withValidator(new DoubleRangeValidator("O tamanho não é válido", 0.0, 9999999.0))
		.bind("tamanho");
		
		binder.forField(tipoSemente).asRequired("O tipo é obrigatório").bind("tipoSemente");
		binder.forField(observacao).bind("observacao");
		
		binder.addStatusChangeListener(status ->{
			salvar.setEnabled(!status.hasValidationErrors());
		});
		
		salvar.addClickListener(e ->{
				try {
					Safra safraSalvo = new Safra();
					binder.writeBean(safraSalvo);
					salvar(safraSalvo);
					binder.readBean(null);
					Dialog sucesso = new DialogMensagem().sucesso("Safra salva com sucesso");
					sucesso.open();
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
	
	private void salvar(Safra safraSalvo) {
		safraSalvo.setEmAtividade(true);
		safraSalvo.setUsuario(SecurityUtils.getUsuarioLogado().getUsuario());
		this.repository.save(safraSalvo); 
	}
	
	private void descricaoForm() {
		descricao.setLabel("Descrição");
		tipo.setLabel("Tipo");
		data.setLabel("Data de plantação");
		tamanhoPlantacao.setLabel("Tamanho da plantação");
		tipoSemente.setLabel("Semente");
		observacao.setLabel("Observação");
		
		form.add(descricao);
		form.add(tipo);
		form.add(data);
		form.add(tamanhoPlantacao);
		form.add(tipoSemente);
		form.add(observacao);
	}

	public void limpar() {
		descricao.clear();
		data.clear();
		tipo.clear();
		tamanhoPlantacao.clear();
		tipoSemente.clear();
		observacao.clear();
	}
	
}
