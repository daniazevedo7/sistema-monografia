package com.daniel.monografia.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.daniel.monografia.model.Monografia;

public class Monografias implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public Monografias() {

	}

	public Monografias(EntityManager manager) {
		this.manager = manager;
	}

	public Monografia porId(Long id) {
		return manager.find(Monografia.class, id);
	}

	public List<Monografia> pesquisar(String nome) {
		String jpql = "from Monografia where titulo like :titulo";
		
		TypedQuery<Monografia> query = manager
				.createQuery(jpql, Monografia.class);
		
		query.setParameter("titulo", nome + "%");
		
		return query.getResultList();
	}
	
	public List<Monografia> todas() {
        return manager.createQuery("from Monografia", Monografia.class).getResultList();
        
   }

	public Monografia guardar(Monografia monografia) {
		return manager.merge(monografia);
	}

}
