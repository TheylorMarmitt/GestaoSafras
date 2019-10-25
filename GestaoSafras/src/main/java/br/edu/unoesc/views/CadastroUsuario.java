package br.edu.unoesc.views;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import br.edu.unoesc.componentes.Botoes;
import br.edu.unoesc.componentes.DialogMensagem;
import br.edu.unoesc.idioma.DataPickerPt;
import br.edu.unoesc.model.Usuario;
import br.edu.unoesc.service.UsuarioService;

@PageTitle("Gestão de Safra")
@Route("cadastrar-usuario")
@HtmlImport("frontend://styles/tema.html")
public class CadastroUsuario extends VerticalLayout{

	private static final long serialVersionUID = -5497533232441585612L;

	private FormLayout form = new FormLayout();
	private TextField nome = new TextField();
	private TextField sobrenome = new TextField();
	private EmailField email = new EmailField();
	private PasswordField senha = new PasswordField();
	private PasswordField confirmacaoSenha = new PasswordField();
	private DatePicker dataNascimento = new DatePicker();
	private HorizontalLayout actions = new HorizontalLayout();
	private HorizontalLayout retornar = new HorizontalLayout();
	private Button limpar = new Botoes().limparCampos();
	private Button salvar = new Botoes().salvar();
	private Button voltar = new Botoes().voltar();

	private UsuarioService usuarioService;
	
	@Autowired
	public CadastroUsuario(UsuarioService usuarioService){
		this.usuarioService = usuarioService;
		
		add(new H2("Cadastro de Usuário"));
		
		criar();
		binder();			
		add(form, actions, retornar);
	}	
	
	public void criar() {
		nome.setPlaceholder("Primeiro nome");
		nome.setAutofocus(true);
		nome.setValueChangeMode(ValueChangeMode.ON_BLUR);
		sobrenome.setPlaceholder("Segundo nome");
		sobrenome.setValueChangeMode(ValueChangeMode.ON_BLUR);
		email.setPlaceholder("nome@exemplo.com");
		email.setValueChangeMode(ValueChangeMode.ON_BLUR);
		senha.setPlaceholder("Senha de acesso");
		senha.setValueChangeMode(ValueChangeMode.ON_BLUR);
		confirmacaoSenha.setPlaceholder("Confirmação da senha");
		confirmacaoSenha.setValueChangeMode(ValueChangeMode.ON_BLUR);
		dataNascimento.setPlaceholder("Nascimento");
		dataNascimento.setI18n(new DataPickerPt().dataPt());
		
		nome.setLabel("Nome");
		sobrenome.setLabel("Sobrenome");
		email.setLabel("E-mail");
		dataNascimento.setLabel("Data de nascimento");
		senha.setLabel("Senha");
		confirmacaoSenha.setLabel("Confirme sua senha");
		
		form.add(nome);
		form.add(sobrenome);
		form.add(email);
		form.add(dataNascimento);
		form.add(senha);
		form.add(confirmacaoSenha);
		
		voltar.addClickListener(e ->{
			voltar.getUI().ifPresent(ui -> ui.navigate("login"));
		});
		
		retornar.add(voltar);
		
		// Button bar
		actions.setSpacing(true);
		actions.add(salvar, limpar);
		
	}
	
	// faz validações e salva
	public void binder() {
		Binder<Usuario> binder = new Binder<>(Usuario.class);
		
		binder.forField(nome).asRequired("O nome é obrigatorio").bind("nome");
		binder.forField(sobrenome).asRequired("O sobrenome é obrigatorio").bind("sobrenome");
		binder.forField(email).asRequired("O e-mail é obrigatório").withValidator(new EmailValidator(
			    "Insira um e-mail válido")).bind("email");
		binder.forField(dataNascimento).asRequired("Data de nascimento obrigatória").bind("dataNascimento");
		binder.forField(senha).asRequired("Senha é obrigatoria").bind("senha");
		
		binder.addStatusChangeListener(status ->{
			salvar.setEnabled(!status.hasValidationErrors());
		});
		
		binder.readBean(new Usuario());
		
		salvar.addClickListener(e ->{
			if(senha.isEmpty() || senha.getValue().contentEquals(confirmacaoSenha.getValue())) {
				if(this.usuarioService.buscaPorEmail(email.getValue()) == null) {
					try {
						Usuario usuarioSalvo = new Usuario();
						binder.writeBean(usuarioSalvo);
						salvar(usuarioSalvo);
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
				}else {
					Dialog dialog = new DialogMensagem().erroMensagem("E-mail Inválido",
							"E-mail já cadastrado, tente fazer o login");
					dialog.open();
				}
				
			}else {
				Dialog dialog = new DialogMensagem().erroMensagem("Senhas incompatíveis",
						"A senha e a confirmação devem ser iguais");
				dialog.open();
			}
		});
		
		limpar.addClickListener(event -> {
			limpar();
		});
		
	}
	
	private void salvar(Usuario user) {
		this.usuarioService.salvar(user);
		salvar.getUI().ifPresent(ui -> ui.navigate("login"));
	}

	
	public void limpar() {
		nome.clear();
		sobrenome.clear();
		email.clear();
		senha.clear();
		confirmacaoSenha.clear();
		dataNascimento.clear();
	}
	
}