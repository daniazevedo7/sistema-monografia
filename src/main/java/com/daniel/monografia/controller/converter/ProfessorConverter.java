package com.daniel.monografia.controller.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.daniel.monografia.model.Professor;

public class ProfessorConverter implements Converter {

	private List<Professor> listaProfessores;

	public ProfessorConverter(List<Professor> listaProfessores) {
		this.listaProfessores = listaProfessores;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value == null || value.isEmpty()){
			throw new ConverterException("Erro de Conversão: value null ou vazio");
				
		} 

		Long id = Long.valueOf(value);
		for (Professor professor : listaProfessores) {
			if (id.equals(professor.getId())) {
				return professor;
			}
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value instanceof Professor && value != null){
			Professor professor = (Professor) value;
			return professor.getId().toString();
		}
		return null;
	}
	
}
