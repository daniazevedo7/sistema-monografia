package com.daniel.monografia.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.daniel.monografia.model.Coordenador;

public class Coordenadores implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Coordenadores(){
		
	}
	
	public Coordenadores(EntityManager manager){
		this.manager = manager;
	}
	
	public List<Coordenador> pesquisar(String nome){
		try {
            String jpql = "select p from Coordenador p where p.pessoa.nome like :nome";
            
            TypedQuery<Coordenador> query = manager.createQuery(jpql, Coordenador.class);
            
            query.setParameter("nome", nome + "%");
            
            return query.getResultList();
      } catch (NoResultException e) {
            return null;
      }
   }
	
	public List<Coordenador> todos() {
        return manager.createQuery("from Coordenador", Coordenador.class).getResultList();
        
   }
	
	public Coordenador guardar(Coordenador coordenador) {
		return manager.merge(coordenador);
	}
} 
