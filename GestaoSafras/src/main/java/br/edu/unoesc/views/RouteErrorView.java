package br.edu.unoesc.views;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;

import br.edu.unoesc.componentes.Botoes;

@Tag(Tag.DIV)
@HtmlImport("frontend://styles/tema.html")
public class RouteErrorView extends VerticalLayout
        implements HasErrorParameter<NotFoundException> {

	private static final long serialVersionUID = 6140241374957982807L;

	@Override
    public int setErrorParameter(BeforeEnterEvent event,
            ErrorParameter<NotFoundException> parameter) {
	    getStyle().set("background-color", "#9e0000");
	    getStyle().set("height", "100%");
		add(mensagem());
        return HttpServletResponse.SC_NOT_FOUND;
    }
	
	private Div mensagem() {
		Div d = new Div();
		d.getStyle().set("padding-top", "10%");
		d.getStyle().set("text-align", "center");
		H1 h1 = new H1("Erro 404");
		h1.getStyle().set("color", "#eee");
		h1.getStyle().set("font-size", "80px");
		h1.getStyle().set("margin-bottom", "5px");
		H3 h3 = new H3("Esta página não existe!");
		h3.getStyle().set("margin-bottom", "50px");
		h3.getStyle().set("color", "#eee");
		
		Button b = new Botoes().inicio();
		b.addClickListener(e -> {
            b.getUI().ifPresent(ui -> ui.navigate("inicio"));
        });
		
		d.add(h1, h3, b);
		return d;
	}
		
}