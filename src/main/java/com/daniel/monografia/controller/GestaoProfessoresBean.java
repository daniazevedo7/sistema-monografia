package com.daniel.monografia.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import org.primefaces.context.RequestContext;

import com.daniel.monografia.model.LinhaPesquisa;
import com.daniel.monografia.model.Professor;
import com.daniel.monografia.model.Usuario;
import com.daniel.monografia.repository.LinhaPesquisas;
import com.daniel.monografia.repository.Professores;
import com.daniel.monografia.service.CadastroProfessorService;
import com.daniel.monografia.util.FacesMessages;

@Named
@ViewScoped
public class GestaoProfessoresBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private Professores professores;
    
    @Inject
    private LinhaPesquisas linhaPesquisas;
    
    @Inject
    private FacesMessages messages;
    
    private List<Professor> listaProfessores;
    
    private List<LinhaPesquisa> listaLinhaPesquisas;
    
    @Inject
    private CadastroProfessorService cadastroProfessorService;
      
    private String termoPesquisa;

	private Professor professor;
	
	private Usuario usuario;
    
    @PostConstruct
	private void iniciar() {
    	professor = new Professor();
    	
    	usuario = new Usuario();
  	  	this.professor.setUsuario(new Usuario());
  	  	this.professor.getUsuario().setTipo('P');
        
        listaLinhaPesquisas = linhaPesquisas.todas();
	}
    
    public void prepararNovoProfessor() {
      
    }
    
    public void prepararEdicao() {
    	
    }
    
    public void salvar() {
    	try{
    		cadastroProfessorService.salvar(professor);

    		atualizarRegistros();

    		messages.info("Professor salvo com sucesso!");

    		RequestContext.getCurrentInstance().update(Arrays.asList(
    				"frm:professoresDataTable", "frm:messages"));
    	}	catch(PersistenceException e){
    		e.printStackTrace();
    		messages.alerta("Matrícula já cadastrada");

    		RequestContext.getCurrentInstance().update(Arrays.asList(
    				"frm:professoresDataTable", "frm:messages"));
    	}
    }
    
   /* public void excluir() {
        cadastroAlunoService.excluir(aluno);
        
        aluno = null;
        
        atualizarRegistros();
        
        messages.info("ALuno excluída com sucesso!");
    }*/
    
    public void pesquisar() {
        listaProfessores = professores.pesquisar(termoPesquisa);
        
        if (listaProfessores.isEmpty()) {
            messages.info("Sua consulta não retornou registros.");
        }
    }
    
    public void todosProfessores() {
    	listaProfessores = professores.todos();
    }
    
    
    private void atualizarRegistros() {
        if (jaHouvePesquisa()) {
            pesquisar();
        } else {
        	todosProfessores();
        }
    }
    
    private boolean jaHouvePesquisa() {
        return termoPesquisa != null && !"".equals(termoPesquisa);
    }
    
	public List<Professor> getListaProfessores() {
		return listaProfessores;
	}

	public void setListaProfessores(List<Professor> listaProfessores) {
		this.listaProfessores = listaProfessores;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTermoPesquisa() {
        return termoPesquisa;
    }
    
    public void setTermoPesquisa(String termoPesquisa) {
        this.termoPesquisa = termoPesquisa;
    }

	public List<LinhaPesquisa> getListaLinhaPesquisas() {
		return listaLinhaPesquisas;
	}

	public void setListaLinhaPesquisas(List<LinhaPesquisa> listaLinhaPesquisas) {
		this.listaLinhaPesquisas = listaLinhaPesquisas;
	}

	public boolean isProfessorSeleciona() {
        return professor != null && professor.getId() != null;
    }
}