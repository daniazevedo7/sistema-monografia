package com.daniel.monografia.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.daniel.monografia.model.LinhaPesquisa;
import com.daniel.monografia.model.OrgaoFomento;

public class OrgaosFomento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public OrgaosFomento() {
		
	}

	public OrgaosFomento(EntityManager manager) {
		this.manager = manager;
	}
	
	public OrgaosFomento porId(Long id) {
		return manager.find(OrgaosFomento.class, id);
	}
	
	public List<OrgaoFomento> pesquisar(String nome) {
		String jpql = "from OrgaoFomento where titulo like :nome";
		
		TypedQuery<OrgaoFomento> query = manager
				.createQuery(jpql, OrgaoFomento.class);
		
		query.setParameter("nome", nome);
		
		return query.getResultList();
	}
	
	
	public List<OrgaoFomento> todos() {
        return manager.createQuery("from OrgaoFomento", OrgaoFomento.class).getResultList();
   }
	
	public OrgaoFomento guardar(OrgaoFomento orgaoFomento) {
		return manager.merge(orgaoFomento);
	}
	
}
