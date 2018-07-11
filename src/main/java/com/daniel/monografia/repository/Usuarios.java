package com.daniel.monografia.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.daniel.monografia.model.Usuario;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Usuarios() {

	}

	public Usuarios(EntityManager manager) {
		this.manager = manager;
	}

	/*public Usuario porId(Long id){
		return manager.find(Usuario.class, id);
	}*/

	/*public List<Usuario> pesquisar(String nome){
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();

		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		Root<Usuario> root = criteriaQuery.from(Usuario.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.like(root.get("nome"),nome + '%'));

		TypedQuery<Usuario> query = manager.createQuery(criteriaQuery);

		return query.getResultList();
	}*/

	//Método login usando a API Criteria do JPA
	/*public Usuario login(Long matricula, String senha) {
		  CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();		

		  try {
		  CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);		
		  Root <Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);	
		  Root <Pessoa>  pessoaRoot = criteriaQuery.from(Pessoa.class);

		  criteriaQuery.select(usuarioRoot);

		  criteriaQuery.where(
				  criteriaBuilder.equal(pessoaRoot.get("matricula"), matricula),
		  		  criteriaBuilder.equal(usuarioRoot.get("senha"), senha));		

		  TypedQuery<Usuario> query = manager.createQuery(criteriaQuery);

		  return (Usuario) query.getSingleResult();
		  } catch (NoResultException e) {
	            return null;
	      }
	}*/

	//Método login usando JPQL
	/*public Usuario login(Long matricula, String senha) {
		try {
            Usuario usuario = (Usuario) manager
                       .createQuery(
                                   "SELECT u from Usuario u join u.pessoa p where p.matricula = :matricula and u.senha = :senha ", Usuario.class)
                       .setParameter("matricula", matricula)
                       .setParameter("senha", senha).getSingleResult();

            return usuario;
      } catch (NoResultException e) {
            return null;
      }
   }*/

	public Usuario porMatricula(Long matricula){
		return manager.find(Usuario.class, matricula);
	}
	
	public Usuario login(Long matricula, String senha) {
		try {
			Usuario usuario = (Usuario) manager
					.createQuery(
							"SELECT u from Usuario u where u.matricula = :matricula and u.senha = :senha ", Usuario.class)
					.setParameter("matricula", matricula)
					.setParameter("senha", senha).getSingleResult();

			return usuario;
		} catch (NoResultException e) {
			return null;
		}
	}
}
