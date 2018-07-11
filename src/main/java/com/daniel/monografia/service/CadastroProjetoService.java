package com.daniel.monografia.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.daniel.monografia.model.Projeto;
import com.daniel.monografia.repository.Projetos;
import com.daniel.monografia.util.Transacional;

public class CadastroProjetoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
    private Projetos projetos;

    @Transacional
    public void salvar(Projeto projeto) {
   
    	projetos.guardar(projeto);
    }

    @Transacional
    public void excluir(Projeto projeto) {
    	projetos.remover(projeto);
    }
}
