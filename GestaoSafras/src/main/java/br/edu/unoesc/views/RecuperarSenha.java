package br.edu.unoesc.views;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import br.edu.unoesc.componentes.Botoes;
import br.edu.unoesc.componentes.DialogMensagem;
import br.edu.unoesc.componentes.DivRecuperarSenha;
import br.edu.unoesc.model.Usuario;
import br.edu.unoesc.service.UsuarioService;

@PageTitle("Gestão Safras")
@Route("recuperar-senha")
@HtmlImport("frontend://styles/tema.html")
public class RecuperarSenha extends VerticalLayout{

	private static final long serialVersionUID = -4768200441380586041L;

	private FormLayout form = new FormLayout();
	private EmailField email = new EmailField();
	private Button recuperar = new Botoes().recuperar();
	private Button voltar = new Botoes().voltar();
	
	private UsuarioService usuarioService;
	
	@Autowired
	public RecuperarSenha(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
		
		setSizeUndefined();
		getStyle().set("padding", "0px");
		
		criar();
		binder();
		
		Div d = new DivRecuperarSenha().divRecupera();
		email.getStyle().set("width", "150px");
		email.getStyle().set("text-align", "left");
		
		form.getStyle().set("margin-top", "30px");
		form.getStyle().set("text-align", "center");
		recuperar.getStyle().set("margin-top", "50px");
		voltar.getStyle().set("margin-top", "10px");
		
		Div vl = new Div();
		vl.getStyle().set("text-align", "center");
		vl.getStyle().set("width", "100%");
		vl.getStyle().set("margin-top", "20px");
		vl.add(voltar);	
		
		d.add(form, recuperar, vl);
		d.getStyle().set("padding", "30px");

		add(d);
	}
	
	public void binder() {
		Binder<Usuario> binder = new Binder<>(Usuario.class);
		
		binder.forField(email).withValidator(new EmailValidator(
			    "Insira um e-mail válido")).bind(Usuario::getEmail, Usuario::setEmail);
		
		binder.addStatusChangeListener(status ->{
			recuperar.setEnabled(!status.hasValidationErrors());
		});
		
		recuperar.addClickListener(e ->{
			try {
				Usuario usuarioSalvo = new Usuario();
				binder.writeBean(usuarioSalvo);
				verificaEmail(usuarioSalvo);
				binder.readBean(new Usuario());
			} catch (ValidationException e1) {				
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
		
	}

	private void verificaEmail(Usuario usuarioSalvo) {
		Usuario user = this.usuarioService.buscaPorEmail(usuarioSalvo.getEmail());
		if(user == null) {
			Dialog dialog = new DialogMensagem().erroMensagem("E-mail inválido", "Informe seu e-mail de login") ;
			dialog.open();
		}else {
			this.usuarioService.atualizar(usuarioSalvo);
			recuperar.getUI().ifPresent(ui -> ui.navigate("login"));
		}
	}

	private void criar() {
		email.setPlaceholder("nome@exemplo.com");
		email.setValueChangeMode(ValueChangeMode.ON_BLUR);
		email.setLabel("E-mail");
		voltar.setThemeName("secondary");
		
		voltar.addClickListener(e ->{
			voltar.getUI().ifPresent(ui -> ui.navigate("login"));
		});
		
		form.add(email);
	}
	
}
