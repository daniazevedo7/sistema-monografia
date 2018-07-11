package com.daniel.monografia.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.daniel.monografia.model.Artigo;
import com.daniel.monografia.repository.Artigos;
import com.daniel.monografia.util.Transacional;

public class CadastroArtigoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
    private Artigos artigos;

    @Transacional
    public void salvar(Artigo artigo) {
   
    	artigos.guardar(artigo);
    }

}
