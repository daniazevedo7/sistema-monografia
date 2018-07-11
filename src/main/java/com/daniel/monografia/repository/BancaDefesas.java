package com.daniel.monografia.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.daniel.monografia.model.BancaDefesa;

public class BancaDefesas implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public BancaDefesas() {

	}

	public BancaDefesas(EntityManager manager) {
		this.manager = manager;
	}

	public BancaDefesa porId(Long id) {
		return manager.find(BancaDefesa.class, id);
	}

	public List<BancaDefesa> pesquisar(String local) {
		String jpql = "from BancaDefesa where local like :local";
		
		TypedQuery<BancaDefesa> query = manager
				.createQuery(jpql, BancaDefesa.class);
		
		query.setParameter("local", local + "%");
		
		return query.getResultList();
	}
	
	public List<BancaDefesa> todas() {
        return manager.createQuery("from BancaDefesa", BancaDefesa.class).getResultList();
        
   }

	public BancaDefesa guardar(BancaDefesa bancaDefesa) {
		return manager.merge(bancaDefesa);
	}

}
