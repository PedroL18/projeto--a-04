package br.edu.fatec.tatui.horarios.dominio;

/** Ambiente físico (sala de aula ou laboratório) em que ocorre uma aula. */
public record Sala(int id, String nome, String tipo, int capacidade) {

    public Sala {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da sala é obrigatório");
        }
        if (capacidade <= 0) {
            throw new IllegalArgumentException("A capacidade deve ser maior que zero");
        }
    }
}
