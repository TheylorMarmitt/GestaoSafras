package br.edu.unoesc.views;

import org.springframework.beans.factory.annotation.Autowired;

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
import br.edu.unoesc.componentes.Navegacao;
import br.edu.unoesc.idioma.DataPickerPt;
import br.edu.unoesc.model.Usuario;
import br.edu.unoesc.security.SecurityUtils;
import br.edu.unoesc.service.UsuarioService;

@PageTitle("Gestão de Safra")
@Route("editar-usuario")
@HtmlImport("frontend://styles/tema.html")
public class EditarUsuario extends VerticalLayout{
	
	private static final long serialVersionUID = -1786196089371099792L;
	
	private FormLayout form = new FormLayout();
	private TextField nome = new TextField();
	private TextField sobrenome = new TextField();
	private EmailField email = new EmailField();
	private PasswordField senha = new PasswordField();
	private PasswordField confirmacaoSenha = new PasswordField();
	private DatePicker dataNascimento = new DatePicker();
	private HorizontalLayout actions = new HorizontalLayout();
	private Navegacao nav = new Navegacao();
	private Button salvar = new Botoes().salvar();

	
	private UsuarioService usuarioService;
	
	@Autowired
	public EditarUsuario( UsuarioService usuarioService){
		this.usuarioService = usuarioService;
		
		criar();
		binder();
		preencherInputs();
		add(nav.menu(7), new H2("Editar Usuário"), form, actions);
	}
	
	private void preencherInputs() {
		nome.setValue(SecurityUtils.getUsuarioLogado().getUsuario().getNome());
		sobrenome.setValue(SecurityUtils.getUsuarioLogado().getUsuario().getSobrenome());
		email.setValue(SecurityUtils.getUsuarioLogado().getUsuario().getEmail());
		dataNascimento.setValue(SecurityUtils.getUsuarioLogado().getUsuario().getDataNascimento());
	}

	public void criar() {
		nome.setAutofocus(true);
		nome.setValueChangeMode(ValueChangeMode.ON_CHANGE);
		sobrenome.setValueChangeMode(ValueChangeMode.ON_CHANGE);
		email.setValueChangeMode(ValueChangeMode.ON_CHANGE);
		senha.setValueChangeMode(ValueChangeMode.ON_CHANGE);
		confirmacaoSenha.setValueChangeMode(ValueChangeMode.ON_CHANGE);
		dataNascimento.setI18n(new DataPickerPt().dataPt());
		salvar.setEnabled(false);

		criarForm();

		actions.setSpacing(true);
		actions.add(salvar);
	}
	
	// faz validações e salva
	public void binder() {
		Binder<Usuario> binder = new Binder<>(Usuario.class);
		
		binder.forField(nome).asRequired("O nome é obrigatorio").bind("nome");
		binder.forField(sobrenome).asRequired("O sobrenome é obrigatorio").bind("sobrenome");
		binder.forField(email).withValidator(new EmailValidator(
			    "Insira um e-mail válido")).bind(Usuario::getEmail, Usuario::setEmail);
		binder.forField(dataNascimento).asRequired("Data de nascimento obrigatória").bind("dataNascimento");
		binder.forField(senha).asRequired("Senha é obrigatoria").bind("senha");
		
		binder.addStatusChangeListener(status ->{
			salvar.setEnabled(!status.hasValidationErrors());
		});
		
		binder.readBean(new Usuario());
		
		salvar.addClickListener(e ->{
			if(senha.isEmpty() || senha.getValue().contentEquals(confirmacaoSenha.getValue())) {
				if(this.usuarioService.buscaPorEmail(email.getValue()) == null ||
						SecurityUtils.getUsuarioLogado().getUsuario().getEmail() == email.getValue()) {
					try {
						Usuario usuarioSalvo = SecurityUtils.getUsuarioLogado().getUsuario();
						binder.writeBean(usuarioSalvo);
						salvar(usuarioSalvo);
						binder.readBean(null);
						confirmacaoSenha.clear();
					} catch (ValidationException e1) {
						Dialog dialog = new DialogMensagem().erroForm();
//						dialog.add(new Html(
//							e1.getValidationErrors().stream().map(err ->
//								"<p>" + err.getErrorMessage() + "</p>")
//							.collect(Collectors.joining("\n"))
//							)
//						);
						dialog.open();
					}
				}else {
					Dialog dialog = new DialogMensagem().erroMensagem("E-mail Inválido", "e-mail já cadastrado, tente fazer o login");
					dialog.open();
				}	
			}else {
				Dialog dialog = new DialogMensagem().erroMensagem("Senhas incompatíveis", 
						"a senha e sua confirmação devem ter o mesmo valor");
				dialog.open();
			}
		});
		
	}
	
	private void salvar(Usuario user) {
		this.usuarioService.salvaAposEdicao(user);
		Dialog dialog = new DialogMensagem().sucesso("Usuário editado com sucesso");
		dialog.open();
	}
	
	private void criarForm() {
		nome.setLabel("Nome");
		sobrenome.setLabel("Sobrenome");
		email.setLabel("E-mail");
		dataNascimento.setLabel("Data de nascimento");
		senha.setLabel("Nova senha");
		confirmacaoSenha.setLabel("Confirme sua senha");
		
		form.add(nome);
		form.add(sobrenome);
		form.add(email);
		form.add(dataNascimento);
		form.add(senha);
		form.add(confirmacaoSenha);
	}
}
