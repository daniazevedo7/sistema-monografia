package com.daniel.monografia.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.daniel.monografia.model.BancaDefesa;
import com.daniel.monografia.repository.BancaDefesas;
import com.daniel.monografia.util.Transacional;

public class CadastroBancaDefesaService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private BancaDefesas bancaDefesas;

    @Transacional
    public void salvar(BancaDefesa bancaDefesa) {
   
    	bancaDefesas.guardar(bancaDefesa);
    }
}
