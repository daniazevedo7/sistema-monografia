package com.daniel.monografia.util;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import com.daniel.monografia.controller.GestaoUsuariosBean;
import com.daniel.monografia.model.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener, Serializable {
	
	@Override
	public void afterPhase(PhaseEvent event) {
		String paginaAtual = Faces.getViewId();
		
		FacesContext facesContext = event.getFacesContext();
		
		boolean ehPaginaAutenticacao = paginaAtual.contains("Login.xhtml");
		
		if(!ehPaginaAutenticacao){
			GestaoUsuariosBean gestaoUsuariosBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{gestaoUsuariosBean}", GestaoUsuariosBean.class);
			
			if(gestaoUsuariosBean == null){
				Faces.navigate("Login.xhtml");
				return;
			}
			
			Usuario usuario = gestaoUsuariosBean.getUsuarioLogado();
			if(usuario == null){
				Faces.navigate("Login.xhtml");
				return;
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}