insert into usuario (id, matricula, senha, tipo) values (1, '11111111', '1234', 'A');
insert into usuario (id, matricula, senha, tipo) values (2, '22222222', '1234', 'A');
insert into usuario (id, matricula, senha, tipo) values (3, '33333333', '1234', 'A');
insert into usuario (id, matricula, senha, tipo) values (4, '44444444', '1234', 'A');
insert into usuario (id, matricula, senha, tipo) values (5, '55555555', '1234', 'A');
insert into usuario (id, matricula, senha, tipo) values (6, '22222222', '1234', 'A');
insert into usuario (id, matricula, senha, tipo) values (7, '33333333', '1234', 'A');
insert into usuario (id, matricula, senha, tipo) values (8, '44444444', '1234', 'A');
insert into usuario (id, matricula, senha, tipo) values (9, '55555555', '1234', 'A');
insert into usuario (id, matricula, senha, tipo) values (10, '66666666', '1234', 'A');
insert into usuario (id, matricula, senha, tipo) values (11, '77777777', '1234', 'A');
insert into usuario (id, matricula, senha, tipo) values (12, '88888888', '1234', 'A');
insert into usuario (id, matricula, senha, tipo) values (13, '99999999', '1234', 'P');
insert into usuario (id, matricula, senha, tipo) values (14, '12222222', '1234', 'P');
insert into usuario (id, matricula, senha, tipo) values (15, '13333333', '1234', 'P');
insert into usuario (id, matricula, senha, tipo) values (16, '14444444', '1234', 'P');
insert into usuario (id, matricula, senha, tipo) values (17, '15555555', '1234', 'P');
insert into usuario (id, matricula, senha, tipo) values (18, '16666666', '1234', 'A');

insert into aluno (id, nome, email) values (1, 'Daniel Azevedo', 'd.azevedo.s7@gmail.com');
insert into aluno (id, nome, email) values (2, 'Monique Maciel', 'monique@hotmail.com');
insert into aluno (id, nome, email) values (3, 'Thais Martins', 'd.azevedo.s7@gmail.com');
insert into aluno (id, nome, email) values (4, 'Izabela Andrea', 'azevedo.iza97@gmail.com');
insert into aluno (id, nome, email) values (5, 'Gabriel de Sá', 'd.azevedo.s7@gmail.com');
insert into aluno (id, nome, email) values (6, 'Sofia Azevedo', 'd.azevedo.s7@gmail.com');
insert into aluno (id, nome, email) values (7, 'Lindenberg Vieira', 'd.azevedo.s7@gmail.com');
insert into aluno (id, nome, email) values (8, 'Caio Gomes', 'd.azevedo.s7@gmail.com');
insert into aluno (id, nome, email) values (9, 'Denise Azevedo', 'd.azevedo.s7@gmail.com');
insert into aluno (id, nome, email) values (10, 'Seli Azevedo', 'd.azevedo.s7@gmail.com');
insert into aluno (id, nome, email) values (11, 'Deodorio Martins', 'd.azevedo.s7@gmail.com');
insert into aluno (id, nome, email) values (12, 'Edcarlos Rodrigues', 'd.azevedo.s7@gmail.com');

insert into professor (id, nome, email) values (1, 'Adriana Aparicio', 'd.azevedo.s7@gmail.com');
insert into professor (id, nome, email) values (2, 'João de Sousa', 'd.azevedo.s7@gmail.com');
insert into professor (id, nome, email) values (3, 'André de Oliveira', 'd.azevedo.s7@gmail.com');
insert into professor (id, nome, email) values (4, 'Antonio da Silva', 'd.azevedo.s7@gmail.com');
insert into professor (id, nome, email) values (5,'José de Almeida', 'd.azevedo.s7@gmail.com');

insert into coordenador (id, nome, email) values (1,'Alexandre Moreira', 'alexandre@uva.br');

insert into monografia (id, titulo, data_inicio, aluno_id, professor_id) values (1, 'SISTEMA DE CADASTRO DE MONOGRAFIA E PROJETOS', '2017-02-01', 1, 1);
insert into monografia (id, titulo, data_inicio, aluno_id, professor_id) values (2, 'ESTUDO SOBRE VOLUME TRIDIMENSIONAL DE IMAGENS DICOM', '2017-03-10', 2, 2);
insert into monografia (id, titulo, data_inicio, aluno_id, professor_id) values (3, 'ANÁLISE DOS BENEFÍCIOS DO USO DE BIG DATA POR ORGANIZAÇÕES', '2017-08-12', 3, 3);
insert into monografia (id, titulo, data_inicio, aluno_id, professor_id) values (4, 'IMPLANTAÇÃO DA ITIL EM EMPRESAS DE PEQUENO PORTE', '2017-07-20', 4, 4);
insert into monografia (id, titulo, data_inicio, aluno_id, professor_id) values (5, 'O USO DA REALIDADE AUMENTADA ALIADA ÀS NOVAS TENDÊNCIAS DA MEDICINA', '2017-03-03', 5, 5);
insert into monografia (id, titulo, data_inicio, aluno_id, professor_id) values (6, 'GUIAR – ASSISTENTE INTELIGENTE DE INVESTIMENTOS', '2017-03-03', 6, 1);
insert into monografia (id, titulo, data_inicio, aluno_id, professor_id) values (7, 'ARTEMIS: UMA APLICAÇÃO PARA O GERENCIAMENTO DE OPORTUNIDADES', '2017-03-03', 7, 3);
insert into monografia (id, titulo, data_inicio, aluno_id, professor_id) values (8, 'SISTEMA DE GESTÃO PARA ESTACIONAMENTO', '2017-03-03', 8, 2);
insert into monografia (id, titulo, data_inicio, aluno_id, professor_id) values (9, 'SISTEMA PARA AUXILIAR NA DETECÇÃO PRECOCE DO HPV', '2017-03-03', 9, 4);
insert into monografia (id, titulo, data_inicio, aluno_id, professor_id) values (10,'YOUR WAY: UMA NOVA MANEIRA DE DESENVOLVER SISTEMAS', '2017-03-03', 10, 5);
insert into monografia (id, titulo, data_inicio, aluno_id, professor_id) values (11,'UM ESTUDO SOBRE OS REQUISITOS FUNDAMENTAIS DE SEGURANÇA EM APLICAÇÕES WEB','2017-03-03', 11, 1);


insert into linha_pesquisa (id, titulo) values (1, 'ITIL');
insert into linha_pesquisa (id, titulo) values (2, 'Desenvolvimento de Sistemas');
insert into linha_pesquisa (id, titulo) values (3, 'Inteligência Artificial')
insert into linha_pesquisa (id, titulo) values (4, 'Big Data');
insert into linha_pesquisa (id, titulo) values (5, 'Cloud Computing ');
insert into linha_pesquisa (id, titulo) values (6, 'Aplicações para Dispositivos Móveis ');
insert into linha_pesquisa (id, titulo) values (7, 'Redes de Computadores ');
insert into linha_pesquisa (id, titulo) values (8, 'Segurança');
insert into linha_pesquisa (id, titulo) values (9, 'Data Mining');
insert into linha_pesquisa (id, titulo) values (10, 'Banco de Dados');4
insert into linha_pesquisa (id, titulo) values (11, 'Sistemas distribuídos e aplicações');
insert into linha_pesquisa (id, titulo) values (12, 'Programação');
insert into linha_pesquisa (id, titulo) values (13, 'Computação Gráfica');
insert into linha_pesquisa (id, titulo) values (14, 'Realidade Aumentada');
insert into linha_pesquisa (id, titulo) values (15, 'Robótica');
insert into linha_pesquisa (id, titulo) values (16, 'Sistemas Embarcados');
insert into linha_pesquisa (id, titulo) values (17, 'Machine Learning');
insert into linha_pesquisa (id, titulo) values (18, 'Bioinformática');
insert into linha_pesquisa (id, titulo) values (19, 'Engenharia de Software');
insert into linha_pesquisa (id, titulo) values (20, 'Análise de Sistemas');

insert into projeto (id, titulo, descricao, professor_id) values(1,'Aplicativo par auxiliar pessoas surdas','Auxilia pessoas surdas no seu dia dia',11);



