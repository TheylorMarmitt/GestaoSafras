package br.edu.unoesc.teste.view;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;

class TesteCompenentesVaadin {

	@Test
	void botao() {
		Text t = new Text("");
		Button b = new Button();
		b.setThemeName("primary");
		b.addClickListener(e ->{
			t.setText("clicado");
		});
		
		b.click();
		assertEquals("clicado", t.getText());
		assertEquals("primary", b.getThemeName());
		
	}
	
	@Test
	void textField() {
		TextField tf = new TextField();
		tf.setPlaceholder("placeholder");
		assertEquals("placeholder", tf.getPlaceholder());
		
	}
	
	@Test
	void comboBox() {
		ComboBox<String> teste = new ComboBox<>();
		List<String> lista = new ArrayList<String>();
		lista.add("item1");
		lista.add("item2");
		teste.setItems(lista);
		
		teste.setErrorMessage("erro");
		
		assertEquals("erro", teste.getErrorMessage());
	}

}
