package br.edu.fatec.tatui.horarios.dominio;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Uma aula na grade horária: associa professor, turma, disciplina e sala
 * a um dia da semana e a um intervalo de horário. É imutável.
 */
public final class AlocacaoHorario {

    private final Professor professor;
    private final Turma turma;
    private final Disciplina disciplina;
    private final Sala sala;
    private final DiaSemana diaSemana;
    private final LocalTime horarioInicio;
    private final LocalTime horarioFim;

    public AlocacaoHorario(Professor professor, Turma turma, Disciplina disciplina, Sala sala,
                           DiaSemana diaSemana, LocalTime horarioInicio, LocalTime horarioFim) {
        this.professor = Objects.requireNonNull(professor, "O professor é obrigatório");
        this.turma = Objects.requireNonNull(turma, "A turma é obrigatória");
        this.disciplina = Objects.requireNonNull(disciplina, "A disciplina é obrigatória");
        this.sala = Objects.requireNonNull(sala, "A sala é obrigatória");
        this.diaSemana = Objects.requireNonNull(diaSemana, "O dia da semana é obrigatório");
        this.horarioInicio = Objects.requireNonNull(horarioInicio, "O horário de início é obrigatório");
        this.horarioFim = Objects.requireNonNull(horarioFim, "O horário de fim é obrigatório");
        if (!horarioInicio.isBefore(horarioFim)) {
            throw new IllegalArgumentException("O horário de início deve ser anterior ao horário de fim");
        }
    }

    /** Indica se as duas aulas ocupam o mesmo dia e têm horários que se cruzam. */
    public boolean sobrepoe(AlocacaoHorario outra) {
        return diaSemana == outra.diaSemana
                && horarioInicio.isBefore(outra.horarioFim)
                && outra.horarioInicio.isBefore(horarioFim);
    }

    /** Retorna o motivo do conflito com outra alocação, ou vazio se não houver conflito. */
    public Optional<TipoConflito> tipoConflitoCom(AlocacaoHorario outra) {
        if (!sobrepoe(outra)) {
            return Optional.empty();
        }
        if (professor.equals(outra.professor)) {
            return Optional.of(TipoConflito.PROFESSOR);
        }
        if (sala.equals(outra.sala)) {
            return Optional.of(TipoConflito.SALA);
        }
        if (turma.equals(outra.turma)) {
            return Optional.of(TipoConflito.TURMA);
        }
        return Optional.empty();
    }

    public boolean conflitaCom(AlocacaoHorario outra) {
        return tipoConflitoCom(outra).isPresent();
    }

    public Professor getProfessor() {
        return professor;
    }

    public Turma getTurma() {
        return turma;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Sala getSala() {
        return sala;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return horarioFim;
    }

    @Override
    public String toString() {
        return String.format("%s %s-%s | %s | %s | %s | %s", diaSemana, horarioInicio, horarioFim,
                disciplina.nome(), turma.nome(), sala.nome(), professor.getNome());
    }
}
