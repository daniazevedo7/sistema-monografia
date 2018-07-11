package com.daniel.monografia.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.daniel.monografia.model.Professor;

public class Professores implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Professores(){
		
	}
	
	public Professores(EntityManager manager){
		this.manager = manager;
	}
	
	public List<Professor> pesquisar(String nome){
		try {
            String jpql = "select p from Professor p where p.usuario.nome like :nome";
            
            TypedQuery<Professor> query = manager.createQuery(jpql, Professor.class);
            
            query.setParameter("nome", nome + "%");
            
            return query.getResultList();
      } catch (NoResultException e) {
            return null;
      }
   }
	
	public List<Professor> todos() {
        return manager.createQuery("from Professor", Professor.class).getResultList();
        
   }
	
	public Professor guardar(Professor professor) {
		return manager.merge(professor);
	}
} 
