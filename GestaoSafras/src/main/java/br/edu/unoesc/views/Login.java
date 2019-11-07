package br.edu.unoesc.views;

import com.vaadin.flow.router.*;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.PWA;

import java.util.Collections;

@PageTitle("Gestão Safras")
@Route("login")
@HtmlImport("frontend://styles/tema.html")
@PWA(name = "Gestão Safras", shortName = "GestãoSafras",
offlineResources = { "frontend/styles/style.css", 
"frontend/styles/bootstrap.min.css", "frontend/styles/bootstrap.css", 
"frontend/styles/bootstrap-theme.css", "frontend/styles/bootstrap-them-min.css",
"frontend/fontes/foundation-icons.css","frontend/fontes/foundation-icons.eot",
"frontend/fontes/foundation-icons.svg",
"frontend/fontes/foundation-icons.ttf","frontend/fontes/foundation-icons.woff",
"frontend/fontes/svgs/fi-social-github.svg","frontend/icons/icon.png", 
"frontend/fontes/comfortaaregular.ttf"}, themeColor = "#25621e")
public class Login extends VerticalLayout implements BeforeEnterObserver {

    private static final long serialVersionUID = -2857064336200457491L;

    private Button botao = new Button();
    private LoginForm login = new LoginForm();

    public Login() {
        login.setAction("/do_login");
        login.setI18n(createPortugueseI18n());
        criarBotao();
        getStyle().set("padding-left", "0px");
        getStyle().set("padding-right", "0px");
        getStyle().set("padding-bottom", "0px");
        
		add(cabecalho(),login, botao, rodape());

        login.addForgotPasswordListener(e -> {
            login.getUI().ifPresent(ui -> ui.navigate("recuperar-senha"));
        });

        botao.addClickListener(e -> {
            botao.getUI().ifPresent(ui -> ui.navigate("cadastrar-usuario"));
        });

    }

	@Override
	public void beforeEnter(BeforeEnterEvent event) { 
		// informa ao usuário sobre erro de autenticação
		if(!event.getLocation().getQueryParameters().getParameters().getOrDefault("error", Collections
				.emptyList()).isEmpty()) {
			login.setError(true); //
		}
	}

    private void criarBotao() {
        botao = new Button();
        botao.setThemeName("secondary");
        setAlignItems(Alignment.CENTER);
        botao.setIcon(new Icon(VaadinIcon.SIGN_IN));
        botao.setText("Cadastrar-se");
    }

    private LoginI18n createPortugueseI18n() {
        final LoginI18n i18n = LoginI18n.createDefault();

        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Gestão de Safra");
        i18n.getHeader().setDescription("Faça a gestão das safras de sua lavoura");
        i18n.getForm().setUsername("Usuário");
        i18n.getForm().setTitle("Acesse:");
        i18n.getForm().setSubmit("Entrar");
        i18n.getForm().setPassword("Senha");
        i18n.getForm().setForgotPassword("Esqueci minha senha");
        i18n.getErrorMessage().setTitle("Usuário/senha inválidos");
        i18n.getErrorMessage()
                .setMessage("Confira seu usuário e senha e tente novamente.");
        i18n.setAdditionalInformation(
                "Caso não tenha cadastro clique em cadastrar-se");
        return i18n;
    }
    
    private Div cabecalho() {
    	Div d = new Div();
        H2 h2 = new H2("Bem vindo ao sistema Gestão de Safras.");
        h2.getStyle().set("margin-top", "0px");
		d.add(h2, new H3("Faça o login para gerir sua safra."));
		d.getStyle().set("text-align", "center");
		return d;
    }
    
    private Div rodape() {
    	Div rodape = new Div();
    	rodape.setWidthFull();
    	rodape.getStyle().set("margin-top", "40px");
    	rodape.getStyle().set("background-color", "#25621e");
    	rodape.getStyle().set("color", "#fff");
    	rodape.getStyle().set("height", "100%");
    	
    	Div tcc = new Div(new Text("Trabalho de Conclusão de Curso"));
    	tcc.getStyle().set("text-align", "center");
    	tcc.getStyle().set("margin-top", "10px");
    	Div tads = new Div(new Text("Tecnologia em Análise e Desenvolvimento de Sistemas - 2019"));
    	tads.getStyle().set("text-align", "center");
    	tads.getStyle().set("margin-top", "5px");
    	Div uno = new Div( new Text("UNOESC - campus Xanxerê-SC"));
    	uno.getStyle().set("text-align", "center");
    	uno.getStyle().set("margin-top", "5px");
    	Div dev = new Div( new Text("Desenvolvido por: Theylor Marmitt"));
    	dev.getStyle().set("text-align", "center");
    	dev.getStyle().set("margin-top", "5px");
    	Div icon_credit = new Div( new Text("Icon made by Freepik from www.flaticon.com"));
    	icon_credit.getStyle().set("text-align", "center");
    	icon_credit.getStyle().set("margin-top", "5px");
   
    	rodape.add(tcc);
    	rodape.add(tads);
    	rodape.add(uno);
    	rodape.add(dev);
    	rodape.add(icon_credit);
    	return rodape;
    }
}
