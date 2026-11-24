package br.edu.fatec.tatui.horarios.dominio;

import java.time.LocalTime;
import java.util.Objects;

/** Janela de horário, em um dia da semana, em que o professor pode lecionar. */
public record Disponibilidade(DiaSemana diaSemana, LocalTime horarioInicio, LocalTime horarioFim) {

    public Disponibilidade {
        Objects.requireNonNull(diaSemana, "O dia da semana é obrigatório");
        Objects.requireNonNull(horarioInicio, "O horário de início é obrigatório");
        Objects.requireNonNull(horarioFim, "O horário de fim é obrigatório");
        if (!horarioInicio.isBefore(horarioFim)) {
            throw new IllegalArgumentException("O horário de início deve ser anterior ao horário de fim");
        }
    }

    /** Indica se o intervalo informado cabe inteiramente nesta janela. */
    public boolean cobre(DiaSemana dia, LocalTime inicio, LocalTime fim) {
        return diaSemana == dia && !inicio.isBefore(horarioInicio) && !fim.isAfter(horarioFim);
    }
}
