package com.daniel.monografia.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.daniel.monografia.model.Artigo;
import com.daniel.monografia.model.Projeto;

public class Artigos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Artigos() {
		
	}

	public Artigos(EntityManager manager) {
		this.manager = manager;
	}
	
	public Artigo porId(Long id) {
		return manager.find(Artigo.class, id);
	}
	
	public List<Artigo> pesquisar(String nome) {
		String jpql = "from Artigo where titulo like :titulo";
		
		TypedQuery<Artigo> query = manager
				.createQuery(jpql, Artigo.class);
		
		query.setParameter("titulo", nome + "%");
		
		return query.getResultList();
	}
	
	public List<Artigo> todos() {
        return manager.createQuery("from Artigo", Artigo.class).getResultList();
   }

	public Artigo guardar(Artigo artigo) {
		return manager.merge(artigo);
	}
}
