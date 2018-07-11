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
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.daniel.monografia.controller.converter.AlunoConverter;
import com.daniel.monografia.controller.converter.ProfessorConverter;
import com.daniel.monografia.model.Aluno;
import com.daniel.monografia.model.LinhaPesquisa;
import com.daniel.monografia.model.OrgaoFomento;
import com.daniel.monografia.model.Professor;
import com.daniel.monografia.model.Projeto;
import com.daniel.monografia.model.Usuario;
import com.daniel.monografia.repository.Alunos;
import com.daniel.monografia.repository.LinhaPesquisas;
import com.daniel.monografia.repository.OrgaosFomento;
import com.daniel.monografia.repository.Professores;
import com.daniel.monografia.repository.Projetos;
import com.daniel.monografia.service.CadastroProjetoService;
import com.daniel.monografia.util.EnviarEmail;
import com.daniel.monografia.util.FacesMessages;

@Named
@ViewScoped
public class GestaoProjetosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Projetos projetos;

	@Inject
	private OrgaosFomento orgaosFomento;

	@Inject
	private LinhaPesquisas linhaPesquisas;

	@Inject
	private Alunos alunos;

	@Inject
	private Professores professores;

	@Inject
	private FacesMessages messages;

	@Inject
	private CadastroProjetoService cadastroProjetoService;

	private List<Projeto> listaProjetos;

	private List<Aluno> listaAlunos;

	private List<Professor> listaProfessores;

	private List<OrgaoFomento> listaOrgaoFomento;

	private List<LinhaPesquisa> listaLinhaPesquisas;

	private String termoPesquisa;

	private Projeto projeto;

	private Professor professor;

	private Aluno aluno;

	private Usuario usuario;

	private ProfessorConverter professorConverter;

	private AlunoConverter alunoConverter;

	private StreamedContent file;
	
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
	private void iniciar(){
		listaAlunos = alunos.todos();
		listaOrgaoFomento = orgaosFomento.todos();
		listaProfessores= professores.todos();
		listaLinhaPesquisas = linhaPesquisas.todas();

		aluno = new Aluno();

		usuario = new Usuario();
		//this.aluno.setUsuario(new Usuario());


		projeto = new Projeto();
		this.projeto.setAluno(new Aluno());
		this.projeto.setProfessor(new Professor());

	}

	public void prepararNovoProjeto() {
		projeto = new Projeto();
		this.projeto.setAluno(new Aluno());
	}

	public void prepararEdicao() {
		professorConverter = new ProfessorConverter(Arrays.asList(projeto.getProfessor()));
		alunoConverter = new AlunoConverter(Arrays.asList(projeto.getAluno()));
	}

	public void salvar() throws IOException, FileNotFoundException {
		try {

			if(projeto.getCaminho() != null){
				Path origem = Paths.get(projeto.getCaminho());
				Path destino = Paths.get("C:/Uploads/Projeto/" + projeto.getTitulo() + ".pdf");
				Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			}

			cadastroProjetoService.salvar(projeto);

			atualizarRegistros();

			messages.info("Projeto salvo com sucesso!");

			RequestContext.getCurrentInstance().update(Arrays.asList(
					"frm:projetosDataTable", "frm:messages"));

		} catch (Exception erro) {
			messages.alerta("Erro ao salvar o Projeto!");
			erro.printStackTrace();
		}
	}

	//	    public void excluir() {
	//	        cadastroProjetoService.excluir(projeto);
	//	        
	//	        projeto = null;
	//	        
	//	        atualizarRegistros();
	//	        
	//	        messages.info("Projeto excluída com sucesso!");
	//	    }

	public void pesquisar() {
		listaProjetos = projetos.pesquisar(termoPesquisa);

		if (listaProjetos.isEmpty()) {
			messages.info("Sua consulta não retornou registros.");
		}
	}

	public void todosProjetos() {
		listaProjetos = projetos.todos();
	}

	private void atualizarRegistros() {
		if (jaHouvePesquisa()) {
			pesquisar();
		} else {
			todosProjetos();
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
			projeto.setCaminho(arquivoTemp.toString());
			System.out.println("Caminho upload: " + projeto.getCaminho());

			Messages.addGlobalInfo("Upload realizado com sucesso");
		} catch (IOException erro) {
			Messages.addGlobalInfo("Ocorreu um erro ao tentar realizar o upload de arquivo");
			erro.printStackTrace();
		}
	}

	public StreamedContent getFile() throws FileNotFoundException {
		try{
			InputStream stream = new FileInputStream("C:/Uploads/Projeto/" + projeto.getTitulo() +".pdf");
			file = new DefaultStreamedContent(stream, "application/pdf", projeto.getTitulo()+".pdf");

		} catch (IOException erro) {
			messages.alerta("Projeto não disponível para download");
			erro.printStackTrace();
		}
		return file;
	}
	
	

	public void setFile(StreamedContent file) {
		this.file = file;
	}
	
	public void enviarEmailAluno()throws AddressException, MessagingException, AuthenticationFailedException  {
		try {
			EnviarEmail enviarEmail = new EnviarEmail();
			enviarEmail.enviarEmail(usuario.getUsuarioEmail(), usuario.getSenhaEmail(), aluno.getUsuario().getEmail(),
					usuario.getAssunto(), usuario.getMensagem());

			messages.info("Convite enviado com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:projetosDataTable", "frm:messages"));
		} catch (Exception e) {
			e.printStackTrace();

			messages.alerta("Convite não enviado!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:projetosDataTable", "frm:messages"));
		}
	}
	
	public void enviarEmailProfessor()throws AddressException, MessagingException, AuthenticationFailedException  {
		try {
			EnviarEmail enviarEmail = new EnviarEmail();
			enviarEmail.enviarEmail(usuario.getUsuarioEmail(), usuario.getSenhaEmail(), professor.getUsuario().getEmail(),
					usuario.getAssunto(), usuario.getMensagem());

			messages.info("Convite enviado com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:projetosDataTable", "frm:messages"));
		} catch (Exception e) {
			e.printStackTrace();

			messages.alerta("Convite não enviado!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:projetosDataTable", "frm:messages"));
		}
	}


	public List<Projeto> getListaProjetos() {
		return listaProjetos;
	}

	public List<Aluno> getListaAlunos() {
		return listaAlunos;
	}

	public void setListaAlunos(List<Aluno> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}

	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<OrgaoFomento> getListaOrgaoFomento() {
		return listaOrgaoFomento;
	}

	public void setListaOrgaoFomento(List<OrgaoFomento> listaOrgaoFomento) {
		this.listaOrgaoFomento = listaOrgaoFomento;
	}

	public List<LinhaPesquisa> getListaLinhaPesquisas() {
		return listaLinhaPesquisas;
	}

	public void setListaLinhaPesquisas(List<LinhaPesquisa> listaLinhaPesquisas) {
		this.listaLinhaPesquisas = listaLinhaPesquisas;
	}

	public List<Professor> getListaProfessores() {
		return listaProfessores;
	}

	public void setListaProfessores(List<Professor> listaProfessores) {
		this.listaProfessores = listaProfessores;
	}

	public ProfessorConverter getProfessorConverter() {
		return professorConverter;
	}

	public void setProfessorConverter(ProfessorConverter professorConverter) {
		this.professorConverter = professorConverter;
	}

	public AlunoConverter getAlunoConverter() {
		return alunoConverter;
	}

	public void setAlunoConverter(AlunoConverter alunoConverter) {
		this.alunoConverter = alunoConverter;
	}

	public boolean isProjetoSeleciona() {
		return projeto != null && projeto.getId() != null;
	}
}
