package com.daniel.monografia.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.daniel.monografia.model.Artigo;
import com.daniel.monografia.model.LinhaPesquisa;
import com.daniel.monografia.model.Monografia;
import com.daniel.monografia.model.Projeto;
import com.daniel.monografia.repository.Artigos;
import com.daniel.monografia.repository.LinhaPesquisas;
import com.daniel.monografia.repository.Monografias;
import com.daniel.monografia.repository.Projetos;
import com.daniel.monografia.service.CadastroArtigoService;
import com.daniel.monografia.util.FacesMessages;

@Named
@ViewScoped
public class GestaoArtigosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Artigos artigos;

	@Inject
	private Monografias monografias;

	@Inject
	private Projetos projetos;

	@Inject
	private LinhaPesquisas linhaPesquisas;

	@Inject
	private FacesMessages messages;

	private List<Artigo> listaArtigos;

	private List<Monografia> listaMonografias;

	private List<Projeto> listaProjetos;

	private List<LinhaPesquisa> listaLinhaPesquisas;

	@Inject
	private CadastroArtigoService cadastroArtigoService;

	private String termoPesquisa;

	private Monografia monografia;

	private Projeto projeto;

	private Artigo artigo;

	private StreamedContent file;


	@PostConstruct
	private void iniciar() {
		artigo = new Artigo();
		this.artigo.setMonografia(new Monografia());

		listaMonografias = monografias.todas();

		setListaProjetos(projetos.todos());

		listaLinhaPesquisas = linhaPesquisas.todas();

	}

	public void prepararNovoArtigo() {
		artigo = new Artigo();
	}

	public void prepararEdicao() {

	}

	public void salvar() throws IOException, FileNotFoundException {
		try {

			if(artigo.getCaminho() != null){
				Path origem = Paths.get(artigo.getCaminho());
				Path destino = Paths.get("C:/Uploads/Artigo/" + artigo.getTitulo() + ".pdf");
				Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			}
			cadastroArtigoService.salvar(artigo);

			atualizarRegistros();

			messages.info("Artigo salvo com sucesso!");

			RequestContext.getCurrentInstance().update(Arrays.asList(
					"frm:artigosDataTable", "frm:messages"));

		} catch (Exception erro) {
			messages.alerta("Erro ao salvar a Artigo!");
			erro.printStackTrace();
		}
	}

	public void pesquisar() {
		listaArtigos = artigos.pesquisar(termoPesquisa);

		if (listaArtigos.isEmpty()) {
			messages.info("Sua consulta não retornou registros.");
		}
	}

	public void todosArtigos() {
		listaArtigos = artigos.todos();
	}

	private void atualizarRegistros() {
		if (jaHouvePesquisa()) {
			pesquisar();
		} else {
			todosArtigos();
		}
	}

	private boolean jaHouvePesquisa() {
		return termoPesquisa != null && !"".equals(termoPesquisa);
	}

	public void upload(FileUploadEvent evento) {
		try {
			UploadedFile arquivoUpload = evento.getFile();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(arquivoUpload.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			artigo.setCaminho(arquivoTemp.toString());
			System.out.println("Caminho upload: " + artigo.getCaminho());

			Messages.addGlobalInfo("Upload realizado com sucesso");
		} catch (IOException erro) {
			Messages.addGlobalInfo("Ocorreu um erro ao tentar realizar o upload de arquivo");
			erro.printStackTrace();
		}
	}

	public StreamedContent getFile() throws FileNotFoundException {
		try{
			InputStream stream = new FileInputStream("C:/Uploads/Artigo/" + artigo.getTitulo() +".pdf");
			file = new DefaultStreamedContent(stream, "application/pdf", artigo.getTitulo()+".pdf");

		} catch (IOException erro) {
			messages.alerta("Artigo não disponível para download ");
			erro.printStackTrace();
		}
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}


	public Monografia getMonografia() {
		return monografia;
	}


	public void setMonografia(Monografia monografia) {
		this.monografia = monografia;
	}

	public Artigo getArtigo() {
		return artigo;
	}

	public void setArtigo(Artigo artigo) {
		this.artigo = artigo;
	}

	public List<Artigo> getListaArtigos() {
		return listaArtigos;
	}

	public void setListaArtigos(List<Artigo> listaArtigos) {
		this.listaArtigos = listaArtigos;
	}

	public List<Monografia> getListaMonografias() {
		return listaMonografias;
	}


	public List<Projeto> getListaProjetos() {
		return listaProjetos;
	}

	public void setListaProjetos(List<Projeto> listaProjetos) {
		this.listaProjetos = listaProjetos;
	}

	public List<LinhaPesquisa> getListaLinhaPesquisas() {
		return listaLinhaPesquisas;
	}

	public void setListaLinhaPesquisas(List<LinhaPesquisa> listaLinhaPesquisas) {
		this.listaLinhaPesquisas = listaLinhaPesquisas;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public void setListaMonografias(List<Monografia> listaMonografias) {
		this.listaMonografias = listaMonografias;
	}


	public boolean isArtigoSeleciona() {
		return artigo != null && artigo.getId() != null;
	}
}