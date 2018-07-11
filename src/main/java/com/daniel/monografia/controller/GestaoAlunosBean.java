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

import com.daniel.monografia.model.Aluno;
import com.daniel.monografia.model.LinhaPesquisa;
import com.daniel.monografia.model.Usuario;
import com.daniel.monografia.repository.Alunos;
import com.daniel.monografia.repository.LinhaPesquisas;
import com.daniel.monografia.repository.Usuarios;
import com.daniel.monografia.service.CadastroAlunoService;
import com.daniel.monografia.util.FacesMessages;

@Named
@ViewScoped
public class GestaoAlunosBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private Alunos alunos;
    
    @Inject
    private Usuarios usuarios;
    
    @Inject
    private LinhaPesquisas linhaPesquisas;
    
    @Inject
    private FacesMessages messages;
    
    private List<Aluno> listaAlunos;
    
    private List<LinhaPesquisa> listaLinhaPesquisas;
    
    @Inject
    private CadastroAlunoService cadastroAlunoService;
      
    private String termoPesquisa;

	private Aluno aluno;
	
	private Usuario usuario;
    
    @PostConstruct
	private void iniciar() {
    	  aluno = new Aluno();
    	  
    	  usuario = new Usuario();
    	  this.aluno.setUsuario(new Usuario());
    	  this.aluno.getUsuario().setTipo('A');
    	  
		  listaLinhaPesquisas = linhaPesquisas.todas();
		  
	}
    
    public void prepararNovoAluno() {
      
    }
    
    public void prepararEdicao() {
    	
    }
    
    public void salvar() {
    	try{
    		cadastroAlunoService.salvar(aluno);


    		atualizarRegistros();

    		messages.info("Aluno salvo com sucesso!");

    		RequestContext.getCurrentInstance().update(Arrays.asList(
    				"frm:alunosDataTable", "frm:messages"));
    	}	catch(PersistenceException e){
    		e.printStackTrace();
    		messages.alerta("Matrícula já cadastrada");

    		RequestContext.getCurrentInstance().update(Arrays.asList(
    				"frm:alunosDataTable", "frm:messages"));
    	}

    }
    	
    
   /* public void excluir() {
        cadastroAlunoService.excluir(aluno);
        
        aluno = null;
        
        atualizarRegistros();
        
        messages.info("ALuno excluída com sucesso!");
    }*/
    
    public void pesquisar() {
        listaAlunos = alunos.pesquisar(termoPesquisa);
        
        if (listaAlunos.isEmpty()) {
            messages.info("Sua consulta não retornou registros.");
        }
    }
    
    public void todosAlunos() {
    	listaAlunos = alunos.todos();
    }
    
    
    private void atualizarRegistros() {
        if (jaHouvePesquisa()) {
            pesquisar();
        } else {
        	todosAlunos();
        }
    }
    
    private boolean jaHouvePesquisa() {
        return termoPesquisa != null && !"".equals(termoPesquisa);
    }
    
   
    public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
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

	public List<Aluno> getListaAlunos() {
		return listaAlunos;
	}

	public void setListaAlunos(List<Aluno> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}

	public List<LinhaPesquisa> getListaLinhaPesquisas() {
		return listaLinhaPesquisas;
	}

	public void setListaLinhaPesquisas(List<LinhaPesquisa> listaLinhaPesquisas) {
		this.listaLinhaPesquisas = listaLinhaPesquisas;
	}

	public boolean isAlunoSeleciona() {
        return aluno != null && aluno.getId() != null;
    }
}