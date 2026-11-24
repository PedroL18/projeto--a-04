package br.edu.fatec.tatui.horarios.servico;

import br.edu.fatec.tatui.horarios.dominio.TipoConflito;

/** Lançada quando uma alocação é rejeitada por violar uma regra de negócio. */
public class ConflitoHorarioException extends RuntimeException {

    private final TipoConflito tipo;

    public ConflitoHorarioException(TipoConflito tipo) {
        super(tipo.getDescricao());
        this.tipo = tipo;
    }

    public TipoConflito getTipo() {
        return tipo;
    }
}
