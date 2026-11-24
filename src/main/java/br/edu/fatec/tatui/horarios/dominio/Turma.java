package br.edu.fatec.tatui.horarios.dominio;

/** Grupo de alunos que assiste às aulas. */
public record Turma(int id, String nome, String periodo, int qtdAlunos) {

    public Turma {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da turma é obrigatório");
        }
        if (qtdAlunos < 0) {
            throw new IllegalArgumentException("A quantidade de alunos não pode ser negativa");
        }
    }
}
