package com.daniel.monografia.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.daniel.monografia.model.Professor;
import com.daniel.monografia.repository.Professores;
import com.daniel.monografia.util.Transacional;

public class CadastroProfessorService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Professores professores;

    @Transacional
    public void salvar(Professor professor) {
    	professores.guardar(professor);
    }

  /*  @Transacional
    public void excluir(Aluno aluno) {
    	alunos.remover(aluno);
    }*/

}
