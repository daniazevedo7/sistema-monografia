package com.daniel.monografia.controller.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.daniel.monografia.model.Aluno;

public class AlunoConverter implements Converter{

	private List<Aluno> listaAlunos;

	public AlunoConverter(List<Aluno> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value == null || value.isEmpty()){
			throw new ConverterException("Erro de Conversão: value null ou vazio");
				
		} 
		
		Long id = Long.valueOf(value);
		
		for(Aluno aluno: listaAlunos){
			if(id.equals(aluno.getId())){
				return aluno;
			}
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value instanceof Aluno && value != null){
			Aluno aluno = (Aluno) value;
			return aluno.getId().toString();
		}
	
		return null;
	}
	
}
