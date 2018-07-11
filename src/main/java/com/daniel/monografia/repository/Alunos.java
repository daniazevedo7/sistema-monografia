package com.daniel.monografia.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.daniel.monografia.model.Aluno;

public class Alunos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public Alunos() {
		
	}

	public Alunos(EntityManager manager) {
		this.manager = manager;
	}
	
	public Aluno porId(Long id) {
		return manager.find(Aluno.class, id);
	}
	
	public List<Aluno> pesquisar(String nome){
		try {
            String jpql = "select a from Aluno a where a.usuario.nome like :nome";
            
            TypedQuery<Aluno> query = manager.createQuery(jpql, Aluno.class);
            
            query.setParameter("nome", nome + "%");
            
            return query.getResultList();
      } catch (NoResultException e) {
            return null;
      }
   }
	
	public List<Aluno> todos() {
        return manager.createQuery("from Aluno", Aluno.class).getResultList();
   }
	
	public Aluno guardar(Aluno aluno) {
		return manager.merge(aluno);
	}

}
