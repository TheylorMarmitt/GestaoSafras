package br.edu.unoesc.conversor;

import java.time.LocalDate;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class SqlDateToLocalDateConverter implements Converter<LocalDate, java.sql.Date> {
	
	private static final long serialVersionUID = -4818666610742149588L;

	@Override
	public Result<java.sql.Date> convertToModel(LocalDate value, ValueContext context) {
		if (value == null) {
			return Result.ok(null);
		}
		return Result.ok(java.sql.Date.valueOf(value));
	}

	@Override
	public LocalDate convertToPresentation(java.sql.Date value, ValueContext context) {
		return value.toLocalDate();
	}
}
