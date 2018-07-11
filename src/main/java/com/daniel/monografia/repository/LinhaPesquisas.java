package com.daniel.monografia.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.daniel.monografia.model.LinhaPesquisa;

public class LinhaPesquisas implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public LinhaPesquisas() {
		
	}

	public LinhaPesquisas(EntityManager manager) {
		this.manager = manager;
	}
	
	public LinhaPesquisa porId(Long id) {
		return manager.find(LinhaPesquisa.class, id);
	}

	public List<LinhaPesquisa> pesquisar(String nome) {
		try {
			String jpql = "from LinhaPesquisa where nome like :nome";

			TypedQuery<LinhaPesquisa> query = manager.createQuery(jpql, LinhaPesquisa.class);

			query.setParameter("nome", nome + "%");

			return query.getResultList();

		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<LinhaPesquisa> todas() {
        return manager.createQuery("from LinhaPesquisa", LinhaPesquisa.class).getResultList();
   }
	
	public LinhaPesquisa guardar(LinhaPesquisa linhaPesquisa) {
		return manager.merge(linhaPesquisa);
	}
	
}
