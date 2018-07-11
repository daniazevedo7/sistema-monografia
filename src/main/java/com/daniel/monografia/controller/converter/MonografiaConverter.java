package com.daniel.monografia.controller.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.daniel.monografia.model.Monografia;

public class MonografiaConverter implements Converter{

	private List<Monografia> listaMonografias;

	public MonografiaConverter(List<Monografia> listaMonografias) {
		this.listaMonografias = listaMonografias;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value == null || value.isEmpty()){
			throw new ConverterException("Erro de Conversão: value null ou vazio");
				
		} 
		
		Long id = Long.valueOf(value);
		
		for(Monografia monografia: listaMonografias){
			if(id.equals(monografia.getId())){
				return monografia;
			}
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value instanceof Monografia && value != null){
			Monografia monografia = (Monografia) value;
			return monografia.getId().toString();
		}
	
		return null;
	}
	
}
