package com.daniel.monografia.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.daniel.monografia.model.Monografia;

public class SchemaGeneration {
	
	public static void main(String[] args) {		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaMonografiaPU");
		
		EntityManager em = emf.createEntityManager();
		
		List<Monografia> lista = em.createQuery("from Monografia", Monografia.class)
				.getResultList();
		
		System.out.println(lista);
		
		em.close();
		emf.close();
	}

}
