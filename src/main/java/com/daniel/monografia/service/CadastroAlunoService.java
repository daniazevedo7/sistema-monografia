package com.daniel.monografia.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.daniel.monografia.model.Aluno;
import com.daniel.monografia.repository.Alunos;
import com.daniel.monografia.util.Transacional;

public class CadastroAlunoService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Alunos alunos;

    @Transacional
    public void salvar(Aluno aluno) {
    	alunos.guardar(aluno);
    }

  /*  @Transacional
    public void excluir(Aluno aluno) {
    	alunos.remover(aluno);
    }*/

}
