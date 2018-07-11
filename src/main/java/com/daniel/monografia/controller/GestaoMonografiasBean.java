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
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.PersistenceException;

import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.daniel.monografia.controller.converter.AlunoConverter;
import com.daniel.monografia.controller.converter.LinhaPesquisaConverter;
import com.daniel.monografia.controller.converter.ProfessorConverter;
import com.daniel.monografia.model.Aluno;
import com.daniel.monografia.model.LinhaPesquisa;
import com.daniel.monografia.model.Monografia;
import com.daniel.monografia.model.Professor;
import com.daniel.monografia.model.Usuario;
import com.daniel.monografia.repository.Alunos;
import com.daniel.monografia.repository.LinhaPesquisas;
import com.daniel.monografia.repository.Monografias;
import com.daniel.monografia.repository.Professores;
import com.daniel.monografia.service.CadastroMonografiaService;
import com.daniel.monografia.util.EnviarEmail;
import com.daniel.monografia.util.FacesMessages;

@Named
@ViewScoped
public class GestaoMonografiasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuario usuario;

	@Inject
	private Monografias monografias;

	@Inject
	private FacesMessages messages;

	@Inject
	private Professores professores;

	@Inject
	private Alunos alunos;

	@Inject
	private LinhaPesquisas linhaPesquisas;

	@Inject
	private CadastroMonografiaService cadastroMonografiaService;

	private List<Monografia> listaMonografias;

	private List<Monografia> listaMonografiasFiltradas;
	
	private String[] listaLinhaPesquisasFiltradas;

	private List<LinhaPesquisa> listaLinhaPesquisas;

	private List<Aluno> listaAlunos;
	
	private List<String> nomeLinhaPesquisas;

	private String termoPesquisa;

	private Converter professorConverter;

	private Converter alunoConverter;

	private Converter linhaPesquisaConverter;

	private Monografia monografia;

	private Aluno aluno;

	private Professor professor;

	private LinhaPesquisa linhaPesquisa;
	
	private StreamedContent file;
	
	private InputStream stream;
	
	private Monografia selecionada;

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
	private void iniciar() {
		monografia = new Monografia();
		this.monografia.setLinhaPesquisa(new LinhaPesquisa());
		
		
		professor = new Professor();
		usuario = new Usuario();
		this.professor.setUsuario(new Usuario());

		listaLinhaPesquisas = linhaPesquisas.todas();
		
		listaMonografias = monografias.todas();
	}

	public void prepararNovaMonografia() {
		monografia = new Monografia();
		this.monografia.setLinhaPesquisa(new LinhaPesquisa());
	}

	public void prepararEdicao() {
		professorConverter = new ProfessorConverter(Arrays.asList(monografia.getProfessor()));
		alunoConverter = new AlunoConverter(Arrays.asList(monografia.getAluno()));
	}

	public void salvar() throws IOException, FileNotFoundException{
		try {

			if(monografia.getCaminho() != null){
				Path origem = Paths.get(monografia.getCaminho());
				Path destino = Paths.get("C:/Uploads/Monografia/" + monografia.getTitulo() + ".pdf");
				Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			}

			cadastroMonografiaService.salvar(monografia);

			atualizarRegistros();

			messages.info("Monografia salva com sucesso!");

			RequestContext.getCurrentInstance().update(Arrays.asList
					("frm:monografiasDataTable", "frm:messages"));

		} catch (IOException  erro) {
			messages.alerta("Erro ao salvar a Monografia!");
			erro.printStackTrace();


		} catch(PersistenceException e){
			e.printStackTrace();
			messages.alerta("Aluno já iniciou projeto de Monografia");
			RequestContext.getCurrentInstance().update(Arrays.asList
					("frm:monografiasDataTable", "frm:messages"));
		}

	}
	
	public void lancarNota(){
		try {
			this.monografia.setDefendida(true);
			
			cadastroMonografiaService.salvar(monografia);

			atualizarRegistros();

			messages.info("Nota lançada com sucesso!");

			RequestContext.getCurrentInstance().update(Arrays.asList("frm:monografiasDataTable", "frm:messages"));

		} catch (Exception erro) {
			messages.alerta("Erro ao lançar nota!");
			erro.printStackTrace();
		}
	}

	public void pesquisar() {
		listaMonografias = monografias.pesquisar(termoPesquisa);

		if (listaMonografias.isEmpty()) {
			messages.info("Sua consulta não retornou registros.");
		}
	}

	public void todasMonografias() {
		listaMonografias = monografias.todas();
	}

	public List<Professor> completarProfessor(String termo) {
		List<Professor> listaProfessores = professores.pesquisar(termo);

		professorConverter = new ProfessorConverter(listaProfessores);

		return listaProfessores;
	}

	public List<Aluno> completarAluno(String termo) {
		List<Aluno> listaAlunos = alunos.pesquisar(termo);

		alunoConverter = new AlunoConverter(listaAlunos);

		return listaAlunos;
	}

	public List<LinhaPesquisa> getSelecionarLinhaPesquisa(String termo) {
		List<LinhaPesquisa> listaLinhaPesquisas = linhaPesquisas.pesquisar(termo);
		linhaPesquisaConverter = new LinhaPesquisaConverter(listaLinhaPesquisas);
		return listaLinhaPesquisas;
	}

	private void atualizarRegistros() {
		if (jaHouvePesquisa()) {
			pesquisar();
		} else {
			todasMonografias();
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
			monografia.setCaminho(arquivoTemp.toString());
			System.out.println("Caminho upload: " + monografia.getCaminho());
			
			Messages.addGlobalInfo("Upload realizado com sucesso");
		} catch (IOException erro) {
			Messages.addGlobalInfo("Ocorreu um erro ao tentar realizar o upload de arquivo");
			erro.printStackTrace();
		}
	}
	
	/*public void prepararArquivo(Monografia monografia) {

	    String nome = monografia.getTitulo();

	    stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/fotos/" + nome);
	    file = new DefaultStreamedContent(stream, "image/jpg", nome);
	}*/
	
	/*public StreamedContent getFile() throws FileNotFoundException {
        InputStream stream = new FileInputStream(monografia.getCaminho() + ".pdf");
        file = new DefaultStreamedContent(stream, "application/pdf", monografia.getCaminho() + ".pdf");
        return file;
    }*/
	
	public StreamedContent getFile() throws FileNotFoundException {
		try{
			InputStream stream = new FileInputStream("C:/Uploads/Monografia/" + monografia.getTitulo() +".pdf");
			file = new DefaultStreamedContent(stream, "application/pdf", monografia.getTitulo()+".pdf");

		} catch (IOException erro) {
			messages.alerta("Monografia não disponível para download ");
			erro.printStackTrace();
		}
		return file;
	}
	
	public void setFile(StreamedContent file) {
		this.file = file;
	}
	
	public void enviarEmailProfessor()throws AddressException, MessagingException, AuthenticationFailedException  {
		try {
			EnviarEmail enviarEmail = new EnviarEmail();
			enviarEmail.enviarEmail(usuario.getUsuarioEmail(), usuario.getSenhaEmail(), professor.getUsuario().getEmail(),
					usuario.getAssunto(), usuario.getMensagem());

			messages.info("Convite enviado com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:monografiasDataTable", "frm:messages"));
		} catch (Exception e) {
			e.printStackTrace();

			messages.alerta("Convite não enviado!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:monografiasDataTable", "frm:messages"));
		}
	}

    /*public void download(Monografia selecionada) {
        this.setSelecionada(selecionada);
    }*/
	
	public List<Monografia> getListaMonografias() {
		return listaMonografias;
	}

	public void setListaMonografias(List<Monografia> listaMonografias) {
		this.listaMonografias = listaMonografias;
	}

	public List<Monografia> getListaMonografiasFiltradas() {
		return listaMonografiasFiltradas;
	}

	public void setListaMonografiasFiltradas(List<Monografia> listaMonografiasFiltradas) {
		this.listaMonografiasFiltradas = listaMonografiasFiltradas;
	}

	public String[] getListaLinhaPesquisasFiltradas() {
		return listaLinhaPesquisasFiltradas;
	}

	public void setListaLinhaPesquisasFiltradas(String[] listaLinhaPesquisasFiltradas) {
		this.listaLinhaPesquisasFiltradas = listaLinhaPesquisasFiltradas;
	}

	public List<LinhaPesquisa> getListaLinhaPesquisas() {
		return listaLinhaPesquisas;
	}

	public void setListaLinhaPesquisas(List<LinhaPesquisa> listaLinhaPesquisas) {
		this.listaLinhaPesquisas = listaLinhaPesquisas;
	}

	public List<Aluno> getListaAlunos() {
		return listaAlunos;
	}

	public void setListaAlunos(List<Aluno> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}

	public List<String> getNomeLinhaPesquisas() {
		return nomeLinhaPesquisas;
	}

	public void setNomeLinhaPesquisas(List<String> nomeLinhaPesquisas) {
		this.nomeLinhaPesquisas = nomeLinhaPesquisas;
	}

	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}

	public Converter getProfessorConverter() {
		return professorConverter;
	}

	public Converter getAlunoConverter() {
		return alunoConverter;
	}

	public Converter getLinhaPesquisaConverter() {
		return linhaPesquisaConverter;
	}

	public Monografia getMonografia() {
		return monografia;
	}

	public void setMonografia(Monografia monografia) {
		this.monografia = monografia;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public LinhaPesquisa getLinhaPesquisa() {
		return linhaPesquisa;
	}

	public void setLinhaPesquisa(LinhaPesquisa linhaPesquisa) {
		this.linhaPesquisa = linhaPesquisa;
	}

	public boolean isMonografiaSeleciona() {
		return monografia != null && monografia.getId() != null;
	}

	public Monografia getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(Monografia selecionada) {
		this.selecionada = selecionada;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}