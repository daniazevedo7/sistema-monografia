package com.daniel.monografia.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "projeto")
public class Projeto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(nullable = false, length = 120)
	private String titulo;
	
	@NotEmpty
	@Column(nullable = false)
	private String descricao;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "professor_id")
	private Professor professor;
	
	
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE},  fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name="projeto_aluno", joinColumns=
	{@JoinColumn(name="projeto_id")}, inverseJoinColumns=
	{@JoinColumn(name="aluno_id")})
	private List<Aluno> listaAlunos;
	
	@NotNull
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="orgao_fomento_id")
	private OrgaoFomento orgaoFomento;
	
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, fetch=FetchType.EAGER)
	@JoinTable(name="projeto_linhapesquisa", joinColumns=
	{@JoinColumn(name="projeto_id")}, inverseJoinColumns=
	{@JoinColumn(name="linha_pesquisa_id")})
	private List<LinhaPesquisa> listaLinhaPesquisas;

	@Transient
	private Aluno aluno;
	
	@Transient
	private String caminho;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public List<Aluno> getListaAlunos() {
		return listaAlunos;
	}

	public void setListaAlunos(List<Aluno> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public OrgaoFomento getOrgaoFomento() {
		return orgaoFomento;
	}

	public void setOrgaoFomento(OrgaoFomento orgaoFomento) {
		this.orgaoFomento = orgaoFomento;
	}

	public List<LinhaPesquisa> getListaLinhaPesquisas() {
		return listaLinhaPesquisas;
	}

	public void setListaLinhaPesquisas(List<LinhaPesquisa> listaLinhaPesquisas) {
		this.listaLinhaPesquisas = listaLinhaPesquisas;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Projeto other = (Projeto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Projeto [id=" + id + "]";
	}
	
	/*@Override
	 public String toString() {
	     return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
	 }*/

	
}
