package com.daniel.monografia.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.daniel.monografia.model.Coordenador;
import com.daniel.monografia.repository.Coordenadores;
import com.daniel.monografia.util.Transacional;

public class CadastroCoordenadorService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Coordenadores coordenadores;

    @Transacional
    public void salvar(Coordenador coordenador) {
    	coordenadores.guardar(coordenador);
    }

  /*  @Transacional
    public void excluir(Aluno aluno) {
    	alunos.remover(aluno);
    }*/

}
