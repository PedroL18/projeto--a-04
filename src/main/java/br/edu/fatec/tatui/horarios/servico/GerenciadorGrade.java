package br.edu.fatec.tatui.horarios.servico;

import br.edu.fatec.tatui.horarios.dominio.AlocacaoHorario;
import br.edu.fatec.tatui.horarios.dominio.DiaSemana;
import br.edu.fatec.tatui.horarios.dominio.Disciplina;
import br.edu.fatec.tatui.horarios.dominio.GradeHoraria;
import br.edu.fatec.tatui.horarios.dominio.Professor;
import br.edu.fatec.tatui.horarios.dominio.Sala;
import br.edu.fatec.tatui.horarios.dominio.TipoConflito;
import br.edu.fatec.tatui.horarios.dominio.Turma;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Orquestra a criação de alocações: valida as regras de negócio
 * (RN-001 a RN-004 e RN-006) antes de registrar a aula na grade.
 */
public class GerenciadorGrade {

    private final GradeHoraria grade;

    public GerenciadorGrade(GradeHoraria grade) {
        this.grade = Objects.requireNonNull(grade, "A grade é obrigatória");
    }

    /**
     * Cria e registra uma alocação (UC-004).
     *
     * @throws ConflitoHorarioException se houver conflito ou indisponibilidade do professor
     * @throws IllegalArgumentException se os dados ou o intervalo de horário forem inválidos
     */
    public AlocacaoHorario alocar(Professor professor, Turma turma, Disciplina disciplina, Sala sala,
                                  DiaSemana dia, LocalTime inicio, LocalTime fim) {
        AlocacaoHorario nova = new AlocacaoHorario(professor, turma, disciplina, sala, dia, inicio, fim);
        if (!validarDisponibilidade(nova)) {
            throw new ConflitoHorarioException(TipoConflito.INDISPONIBILIDADE_PROFESSOR);
        }
        detectarConflito(nova).ifPresent(tipo -> {
            throw new ConflitoHorarioException(tipo);
        });
        grade.adicionarAlocacao(nova);
        return nova;
    }

    /** Varre a grade em busca de conflito de professor, sala ou turma (UC-005). */
    public Optional<TipoConflito> detectarConflito(AlocacaoHorario candidata) {
        for (AlocacaoHorario existente : grade.getAlocacoes()) {
            Optional<TipoConflito> tipo = candidata.tipoConflitoCom(existente);
            if (tipo.isPresent()) {
                return tipo;
            }
        }
        return Optional.empty();
    }

    /** Verifica se o horário está dentro de uma janela de disponibilidade do professor (RN-004). */
    public boolean validarDisponibilidade(AlocacaoHorario alocacao) {
        return alocacao.getProfessor().estaDisponivel(
                alocacao.getDiaSemana(), alocacao.getHorarioInicio(), alocacao.getHorarioFim());
    }

    public GradeHoraria getGrade() {
        return grade;
    }
}
