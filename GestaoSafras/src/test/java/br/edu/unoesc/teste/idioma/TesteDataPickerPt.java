package br.edu.unoesc.teste.idioma;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.edu.unoesc.idioma.DataPickerPt;

class TesteDataPickerPt {

	@Test
	void idioma() {
		DataPickerPt pt = new DataPickerPt();
		
		pt.dataPt().getCalendar();
		
		List<String> meses = new ArrayList<>();
		List<String> semana = new ArrayList<>();
		
		meses.add("Janeiro");
		meses.add("Fevereiro");
		meses.add("Março");
		meses.add("Abril");
		meses.add("Maio");
		meses.add("Junho");
		meses.add("Julho");
		meses.add("Agosto");
		meses.add("Setembro");
		meses.add("Outubro");
		meses.add("Novembro");
		meses.add("Dezembro");
		
		semana.add("dom");
		semana.add("seg");
		semana.add("ter");
		semana.add("qua");
		semana.add("qui");
		semana.add("sex");
		semana.add("sab");
		
		assertEquals("Calendário", pt.dataPt().getCalendar());
		assertEquals("Semana", pt.dataPt().getWeek());
		assertEquals(1, pt.dataPt().getFirstDayOfWeek());
		assertEquals(meses, pt.dataPt().getMonthNames());
		assertEquals(semana, pt.dataPt().getWeekdaysShort());
		
	}

}
