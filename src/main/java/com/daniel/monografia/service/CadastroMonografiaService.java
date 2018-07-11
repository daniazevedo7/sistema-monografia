package com.daniel.monografia.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.daniel.monografia.model.Monografia;
import com.daniel.monografia.repository.Monografias;
import com.daniel.monografia.util.Transacional;

public class CadastroMonografiaService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Monografias monografias;

    @Transacional
    public void salvar(Monografia monografia) {
   
    	monografias.guardar(monografia);
    }

}
