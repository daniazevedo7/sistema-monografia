package com.daniel.monografia.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.daniel.monografia.model.Projeto;

public class Projetos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Projetos() {
		
	}

	public Projetos(EntityManager manager) {
		this.manager = manager;
	}
	
	public Projeto porId(Long id) {
		return manager.find(Projeto.class, id);
	}
	
	public List<Projeto> pesquisar(String titulo) {
		String jpql = "from Projeto where titulo like :titulo";
		
		TypedQuery<Projeto> query = manager
				.createQuery(jpql, Projeto.class);
		
		query.setParameter("titulo", titulo + "%");
		
		return query.getResultList();
	}
	
	public List<Projeto> todos() {
        return manager.createQuery("from Projeto", Projeto.class).getResultList();
   }

	public Projeto guardar(Projeto projeto) {
		return manager.merge(projeto);
	}

	public void remover(Projeto projeto) {
		projeto = porId(projeto.getId());
		manager.remove(projeto);
	}
}
