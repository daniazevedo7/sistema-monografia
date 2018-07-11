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

import com.daniel.monografia.model.Coordenador;
import com.daniel.monografia.model.LinhaPesquisa;
import com.daniel.monografia.model.Usuario;
import com.daniel.monografia.repository.Coordenadores;
import com.daniel.monografia.repository.LinhaPesquisas;
import com.daniel.monografia.service.CadastroCoordenadorService;
import com.daniel.monografia.util.FacesMessages;

@Named
@ViewScoped
public class GestaoCoordenadoresBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private Coordenadores coordenadores;
    
    @Inject
    private LinhaPesquisas linhaPesquisas;
    
    @Inject
    private FacesMessages messages;
    
    private List<Coordenador> listaCoordenadores;
    
    private List<LinhaPesquisa> listaLinhaPesquisas;
    
    @Inject
    private CadastroCoordenadorService cadastroCoordenadorService;
      
    private String termoPesquisa;

	private Coordenador coordenador;
	
	private Usuario usuario;
	
    @PostConstruct
	private void iniciar() {
    	coordenador = new Coordenador();
    	
    	usuario = new Usuario();
  	  	this.coordenador.setUsuario(new Usuario());
  	  	this.coordenador.getUsuario().setTipo('C');
        
        listaLinhaPesquisas = linhaPesquisas.todas();
	}
    
    public void prepararNovoCoordenador() {
      
    }
    
    public void prepararEdicao() {
    	
    }
    
    public void salvar() {
    	try{
    		cadastroCoordenadorService.salvar(coordenador);

    		atualizarRegistros();

    		messages.info("Coordenador salvo com sucesso!");

    		RequestContext.getCurrentInstance().update(Arrays.asList(
    				"frm:coordenadoresDataTable", "frm:messages"));
    	}	catch(PersistenceException e){
    		e.printStackTrace();
    		messages.alerta("Matrícula já cadastrada");

    		RequestContext.getCurrentInstance().update(Arrays.asList(
    				"frm:coordenadoresDataTable", "frm:messages"));
    	}
    }
    
   /* public void excluir() {
        cadastroAlunoService.excluir(aluno);
        
        aluno = null;
        
        atualizarRegistros();
        
        messages.info("ALuno excluída com sucesso!");
    }*/
    
    public void pesquisar() {
        listaCoordenadores = coordenadores.pesquisar(termoPesquisa);
        
        if (listaCoordenadores.isEmpty()) {
            messages.info("Sua consulta não retornou registros.");
        }
    }
    
    public void todosCoordenadores() {
    	listaCoordenadores = coordenadores.todos();
    }
    
    
    private void atualizarRegistros() {
        if (jaHouvePesquisa()) {
            pesquisar();
        } else {
        	todosCoordenadores();
        }
    }
    
    private boolean jaHouvePesquisa() {
        return termoPesquisa != null && !"".equals(termoPesquisa);
    }
  
	public List<Coordenador> getListaCoordenadores() {
		return listaCoordenadores;
	}

	public void setListaCoordenadores(List<Coordenador> listaCoordenadores) {
		this.listaCoordenadores = listaCoordenadores;
	}

	public Coordenador getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Coordenador coordenador) {
		this.coordenador = coordenador;
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

	public boolean isCoordenadorSeleciona() {
        return coordenador != null && coordenador.getId() != null;
    }
}