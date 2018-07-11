package com.daniel.monografia.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.daniel.monografia.model.Aluno;
import com.daniel.monografia.model.Professor;
import com.daniel.monografia.model.Usuario;
import com.daniel.monografia.repository.Usuarios;
import com.daniel.monografia.util.EnviarEmail;
import com.daniel.monografia.util.FacesMessages;

@Named
@javax.enterprise.context.SessionScoped
public class GestaoUsuariosBean  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuario usuario;
	
	private Professor professor;
	
	private Aluno aluno;

	@Inject
	private Usuarios usuarios;

	private Usuario usuarioLogado;

	@Inject
	private FacesMessages messages;
	
	private Converter usuarioConverter;
	
	public void handleSelect(SelectEvent event) {
		// Aluno aluno = (Aluno)event.getObject();
		if(event.equals(aluno)){
			this.aluno = (Aluno) event.getObject();
		}
		
		if(event.equals(professor)){
			this.professor = (Professor) event.getObject();
		}
	}
	
	@PostConstruct
	public void iniciar(){
		usuario = new Usuario();
		aluno = new Aluno();
		professor = new Professor();
		
		this.professor.setUsuario(new Usuario());
		this.aluno.setUsuario(new Usuario());
	
	}

	/*public List<Usuario> completarUsuario(String termo) {
        List<Usuario> listaUsuarios = usuarios.pesquisar(termo);
        
        setUsuarioConverter(new UsuarioConverter(listaUsuarios));
        
        return listaUsuarios;
    }*/

	public void login() {
		try {
			usuarioLogado = usuarios.login(usuario.getMatricula(), usuario.getSenha());
			if (usuarioLogado == null) {
				messages.info("Matrícula e/ou senha incorretos");
				return;
			}
			Faces.redirect("./paginas/Index.xhtml");

		} catch (IOException erro) {
			erro.printStackTrace();
			Messages.addGlobalError(erro.getMessage());
		}
	}

	public String logout() {
		usuarioLogado = null;
		return "/paginas/Login.xhtml?faces-redirect=true";
	}

	public void enviarEmail()throws AddressException, MessagingException, AuthenticationFailedException, java.lang.NullPointerException{
		try {
			EnviarEmail enviarEmail = new EnviarEmail();
			enviarEmail.enviarEmail(usuario.getUsuarioEmail(), usuario.getSenhaEmail(), professor.getUsuario().getEmail(),
					usuario.getAssunto(), usuario.getMensagem());
		} catch (Exception e) {
			e.printStackTrace();
					
			messages.alerta("Convite não enviado!");
		}
		messages.info("Convite enviado com sucesso!");
		RequestContext.getCurrentInstance().update(Arrays.asList( "frm:messages"));
	}
	
	public boolean temPermissoes(List<String> permissoes){		
		for(String permissao : permissoes){
			if(usuarioLogado.getTipo() == permissao.charAt(0)){
				return true;
			}
		}
		
		return false;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Converter getUsuarioConverter() {
		return usuarioConverter;
	}

	public void setUsuarioConverter(Converter usuarioConverter) {
		this.usuarioConverter = usuarioConverter;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

}
