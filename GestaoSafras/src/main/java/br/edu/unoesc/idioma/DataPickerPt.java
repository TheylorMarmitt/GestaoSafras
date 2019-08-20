package br.edu.unoesc.idioma;

import java.util.Arrays;

import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;

public class DataPickerPt {

	public DatePickerI18n dataPt() {
		DatePickerI18n dt = new DatePickerI18n()
        .setWeek("Semana").setCalendar("Calendário")
                .setClear("Limpar").setToday("Hoje")
                .setCancel("Cancelar").setFirstDayOfWeek(1)
                .setMonthNames(Arrays.asList("Janeiro", "Fevereiro",
                        "Março", "Abril", "Maio", "Junho",
                        "Julho", "Agosto", "Setembro", "Outubro",
                        "Novembro", "Dezembro")).setWeekdays(
                Arrays.asList("Domingo", "Segunda-feira", "Terça-feira",
                        "Quarta-feira", "Quinta-feira", "Sexta-feira",
                        "Sábado")).setWeekdaysShort(
                Arrays.asList("dom", "seg", "ter", "qua", "qui", "sex",
                        "sab"));
        return dt;                
	}
}
