package com.daniel.monografia.controller.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.daniel.monografia.model.Usuario;

public class UsuarioConverter implements Converter {

	private List<Usuario> listaUsuarios;

	public UsuarioConverter(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		System.out.println("NOME: " + value);
		if(value == null){
			return null;	
		}
		
		Long id = Long.valueOf(value);
		
		for(Usuario usuario: listaUsuarios){
			if(id.equals(usuario.getId())){
				return usuario;
			}
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null){
			return null;
		}
	
		Usuario usuario = (Usuario) value;
		
		return usuario.getId().toString();
	}
}
