	package com.daniel.monografia.controller.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.daniel.monografia.model.LinhaPesquisa;

public class LinhaPesquisaConverter implements Converter {

	private List<LinhaPesquisa> listaLinhaPesquisas;
	
	public LinhaPesquisaConverter(List<LinhaPesquisa> listaLinhaPesquisas) {
		this.listaLinhaPesquisas = listaLinhaPesquisas;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		System.out.println("RETORNO MÉTODO getAsObject: " + value);
		if (value == null || value.isEmpty()) {
			throw new ConverterException("Erro de Conversão: value null ou vazio");

		}
		
		 Long id = Long.valueOf(value);
		 
		 for(LinhaPesquisa linhaPesquisa: listaLinhaPesquisas){
		 if(id.equals(linhaPesquisa.getId())){ 
			 return linhaPesquisa; 
			} 
		 }
		 
		 return null;
		 
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		System.out.println("PARAMETRO MÉTODO getAsString : "+value);
		if(value == null){
			return "";
		}
		
		try{
			if(value instanceof LinhaPesquisa && value != null){
				
				return String.valueOf(((LinhaPesquisa) value).getId());
			}else{
			
			}
		}catch(NumberFormatException e){
			throw new ConverterException(new FacesMessage(String.format("Selecione uma Linha de Pesquisa", value)), e);
		}
		return null;
		
	}
	
}
