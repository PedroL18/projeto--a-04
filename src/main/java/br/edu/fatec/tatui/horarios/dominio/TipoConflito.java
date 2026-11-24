package br.edu.fatec.tatui.horarios.dominio;

/** Motivos pelos quais uma alocação de horário é rejeitada. */
public enum TipoConflito {
    PROFESSOR("O professor já possui aula neste dia e horário"),
    SALA("A sala já está ocupada neste dia e horário"),
    TURMA("A turma já possui aula neste dia e horário"),
    INDISPONIBILIDADE_PROFESSOR("O horário está fora da disponibilidade informada pelo professor");

    private final String descricao;

    TipoConflito(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
