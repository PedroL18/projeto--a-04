package br.edu.fatec.tatui.horarios.dominio;

/** Disciplina a ser ministrada. */
public record Disciplina(int id, String nome, int cargaHoraria) {

    public Disciplina {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da disciplina é obrigatório");
        }
        if (cargaHoraria <= 0) {
            throw new IllegalArgumentException("A carga horária deve ser maior que zero");
        }
    }
}
