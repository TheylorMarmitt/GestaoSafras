package br.edu.unoesc.componentes;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;

public class DivRecuperarSenha {
	
	public Div divRecupera() {
		Div d = new Div();
		
		H2 header = new H2("Recuperar senha");
		header.getStyle().set("margin-bottom", "100px");
		
		d.getStyle().set("margin", "auto");
		d.getStyle().set("margin-top", "50px");
		d.getStyle().set("width", "400px");
		d.getStyle().set("height", "550px");
		d.getStyle().set("border", "solid 1px");
		d.getStyle().set("border-radius", "20px");
		d.getStyle().set("padding", "0px !important");
		
		Text text = new Text("Informe seu e-mail de login no nosso sistema,"
				+ " e enviaremos uma nova senha temporária para o acesso.");
		
		d.getStyle().set("text-align", "center");
		
		d.add(header, text);
		return d;
		
	}

}
