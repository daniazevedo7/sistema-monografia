package com.daniel.monografia.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.daniel.monografia.controller.converter.MonografiaConverter;
import com.daniel.monografia.model.Aluno;
import com.daniel.monografia.model.BancaDefesa;
import com.daniel.monografia.model.LinhaPesquisa;
import com.daniel.monografia.model.Monografia;
import com.daniel.monografia.model.Professor;
import com.daniel.monografia.model.Usuario;
import com.daniel.monografia.repository.BancaDefesas;
import com.daniel.monografia.repository.Monografias;
import com.daniel.monografia.repository.Professores;
import com.daniel.monografia.service.CadastroBancaDefesaService;
import com.daniel.monografia.util.FacesMessages;

@Named
@ViewScoped
public class GestaoBancaDefesaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private BancaDefesas bancaDefesas;

	@Inject
	private Professores professores;
	
	@Inject
	private Monografias monografias;

	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroBancaDefesaService cadastroBancaDefesaService;
	
	private Converter monografiaConverter;

	private List<BancaDefesa> listaBancaDefesas;

	private List<Professor> listaProfessores;
	
	private List<Monografia> listaMonografias;

	private String termoPesquisa;

	private BancaDefesa bancaDefesa;

	private Aluno aluno;

	private Professor professor;

	private Monografia monografia;
	
	private LinhaPesquisa linhaPesquisa;
	
	private Usuario usuario;

	public void handleSelect(SelectEvent event) {
		
		this.monografia = (Monografia) event.getObject();
		
	}

	@PostConstruct
	private void iniciar() {
		
		bancaDefesa = new BancaDefesa();
		//this.bancaDefesa.setProfessor(new Professor());
		//this.bancaDefesa.setMonografia(new Monografia());
		//this.bancaDefesa.setAluno(aluno);
		
		monografia = new Monografia();
		this.bancaDefesa.setMonografia(new Monografia());
		
		usuario = new Usuario();
		

		//aluno = new Aluno();
		//this.aluno.setPessoa(new Pessoa());

		listaProfessores = professores.todos();
		
		listaMonografias = monografias.todas();
	}

	public void prepararNovaBanca() {
		bancaDefesa = new BancaDefesa();
		this.bancaDefesa.setMonografia(new Monografia());
		this.bancaDefesa.setProfessor(new Professor());
	}

	public void prepararEdicao() {

	}

	public void salvar() {
		try{

			cadastroBancaDefesaService.salvar(bancaDefesa);

			atualizarRegistros();

			messages.info("Banca marcada com sucesso!");

			RequestContext.getCurrentInstance().update(Arrays.asList
					("frm:bancaDefesasDataTable", "frm:messages"));
		}	catch(PersistenceException e){
			e.printStackTrace();
			messages.alerta("A banca dessa Monografia já foi marcada");

			RequestContext.getCurrentInstance().update(Arrays.asList
					("frm:bancaDefesasDataTable", "frm:messages"));
		}

	}

	public void pesquisar() {
		listaBancaDefesas = bancaDefesas.pesquisar(termoPesquisa);

		if (listaBancaDefesas.isEmpty()) {
			messages.info("Sua consulta não retornou registros.");
		}
	}

	public void todasBancaDefesas() {
		listaBancaDefesas = bancaDefesas.todas();
	}

	public List<Monografia> completarMonografia(String termo) {
		List<Monografia> listaMonografias = monografias.pesquisar(termo);
	
	 monografiaConverter = new MonografiaConverter(listaMonografias);
	
	 	return listaMonografias;
	 }

	private void atualizarRegistros() {
		if (jaHouvePesquisa()) {
			pesquisar();
		} else {
			todasBancaDefesas();
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

	public List<BancaDefesa> getListaBancaDefesas() {
		return listaBancaDefesas;
	}

	public void setListaBancaDefesas(List<BancaDefesa> listaBancaDefesas) {
		this.listaBancaDefesas = listaBancaDefesas;
	}

	public List<Professor> getListaProfessores() {
		return listaProfessores;
	}

	public void setListaProfessores(List<Professor> listaProfessores) {
		this.listaProfessores = listaProfessores;
	}

	public List<Monografia> getListaMonografias() {
		return listaMonografias;
	}

	public void setListaMonografias(List<Monografia> listaMonografias) {
		this.listaMonografias = listaMonografias;
	}

	public BancaDefesa getBancaDefesa() {
		return bancaDefesa;
	}

	public void setBancaDefesa(BancaDefesa bancaDefesa) {
		this.bancaDefesa = bancaDefesa;
	}

	public Monografia getMonografia() {
		return monografia;
	}

	public void setMonografia(Monografia monografia) {
		this.monografia = monografia;
	}

	public LinhaPesquisa getLinhaPesquisa() {
		return linhaPesquisa;
	}

	public void setLinhaPesquisa(LinhaPesquisa linhaPesquisa) {
		this.linhaPesquisa = linhaPesquisa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Converter getMonografiaConverter() {
		return monografiaConverter;
	}

	public boolean isBancaDefesaSeleciona() {
		return bancaDefesa != null && bancaDefesa.getId() != null;
	}
}