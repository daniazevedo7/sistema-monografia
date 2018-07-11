package com.daniel.monografia.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.daniel.monografia.model.LinhaPesquisa;
import com.daniel.monografia.repository.LinhaPesquisas;
import com.daniel.monografia.util.Transacional;

public class CadastroLinhaPesquisaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
    private LinhaPesquisas linhaPesquisas;

    @Transacional
    public void salvar(LinhaPesquisa linhaPesquisa) {
   
    	linhaPesquisas.guardar(linhaPesquisa);
    }

}
