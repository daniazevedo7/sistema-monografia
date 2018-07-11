package com.daniel.monografia.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.daniel.monografia.model.LinhaPesquisa;
import com.daniel.monografia.model.Monografia;
import com.daniel.monografia.repository.LinhaPesquisas;
import com.daniel.monografia.service.CadastroLinhaPesquisaService;
import com.daniel.monografia.util.FacesMessages;

@Named
@ViewScoped
public class GestaoLinhaPesquisasBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private LinhaPesquisas linhaPesquisas;;
    
    @Inject
    private FacesMessages messages;
    
    private List<Monografia> listaMonografias;
    
    private List<LinhaPesquisa> listaLinhaPesquisas; ;
    
    @Inject
    private CadastroLinhaPesquisaService cadastroLinhaPesquisaService;
    
    private String termoPesquisa;

	private LinhaPesquisa linhaPesquisa;


	@PostConstruct
	private void iniciar() {
		linhaPesquisa = new LinhaPesquisa();
		
	}
	
    public void prepararNovaLinhaPesquisa() {
        
    }
    
    public void prepararEdicao() {
    	
    }
    
    public void salvar () {
    	try{
    		cadastroLinhaPesquisaService.salvar(linhaPesquisa);
            
            atualizarRegistros();
            
            messages.info("Linha de Pesquisa salva com sucesso!");
            
            RequestContext.getCurrentInstance().update(Arrays.asList(
                    "frm:linhaPesquisasDataTable", "frm:messages"));
        	
    	}catch(Exception e){
    		 messages.alerta("Linha de Pesquisa já cadastrada");
    	}
    	
    }
    
    public void pesquisar() {
    	listaLinhaPesquisas = linhaPesquisas.pesquisar(termoPesquisa);
        
        if (listaLinhaPesquisas.isEmpty()) {
            messages.info("Sua consulta não retornou registros.");
        }
    }
    
    public void todasLinhaPesquisas() {
    	listaLinhaPesquisas = linhaPesquisas.todas();
    }
    
    private void atualizarRegistros() {
        if (jaHouvePesquisa()) {
            pesquisar();
        } else {
        	todasLinhaPesquisas();
        }
    }
    
    private boolean jaHouvePesquisa() {
        return termoPesquisa != null && !"".equals(termoPesquisa);
    }
    
    public String getTermoPesquisa() {
        return termoPesquisa;
    }
    
    public void setTermoPesquisa(String termoPesquisa) {
        this.termoPesquisa = termoPesquisa;
    }

	public LinhaPesquisa getLinhaPesquisa() {
		return linhaPesquisa;
	}

	public void setLinhaPesquisa(LinhaPesquisa linhaPesquisa) {
		this.linhaPesquisa = linhaPesquisa;
	}

	public List<Monografia> getListaMonografias() {
		return listaMonografias;
	}


	public void setListaMonografias(List<Monografia> listaMonografias) {
		this.listaMonografias = listaMonografias;
	}


	public List<LinhaPesquisa> getListaLinhaPesquisas() {
		return listaLinhaPesquisas;
	}

	public void setListaLinhaPesquisas(List<LinhaPesquisa> listaLinhaPesquisas) {
		this.listaLinhaPesquisas = listaLinhaPesquisas;
	}

	public boolean isLinhaPesquisaSeleciona() {
        return linhaPesquisa != null && linhaPesquisa.getId() != null;
    }
}