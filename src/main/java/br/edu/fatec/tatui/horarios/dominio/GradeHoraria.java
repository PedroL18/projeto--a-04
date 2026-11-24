package br.edu.fatec.tatui.horarios.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/** Conjunto das alocações de um semestre. Atende às consultas de calendário. */
public class GradeHoraria {

    private static final Comparator<AlocacaoHorario> ORDEM_CRONOLOGICA =
            Comparator.comparing(AlocacaoHorario::getDiaSemana)
                    .thenComparing(AlocacaoHorario::getHorarioInicio);

    private final String semestre;
    private final List<AlocacaoHorario> alocacoes = new ArrayList<>();

    public GradeHoraria(String semestre) {
        if (semestre == null || semestre.isBlank()) {
            throw new IllegalArgumentException("O semestre é obrigatório");
        }
        this.semestre = semestre;
    }

    public void adicionarAlocacao(AlocacaoHorario alocacao) {
        alocacoes.add(Objects.requireNonNull(alocacao, "A alocação é obrigatória"));
    }

    public boolean removerAlocacao(AlocacaoHorario alocacao) {
        return alocacoes.remove(alocacao);
    }

    public List<AlocacaoHorario> consultarPorProfessor(Professor professor) {
        return filtrar(a -> a.getProfessor().equals(professor));
    }

    public List<AlocacaoHorario> consultarPorSala(Sala sala) {
        return filtrar(a -> a.getSala().equals(sala));
    }

    public List<AlocacaoHorario> consultarPorTurma(Turma turma) {
        return filtrar(a -> a.getTurma().equals(turma));
    }

    public List<AlocacaoHorario> getAlocacoes() {
        return Collections.unmodifiableList(alocacoes);
    }

    public String getSemestre() {
        return semestre;
    }

    private List<AlocacaoHorario> filtrar(Predicate<AlocacaoHorario> criterio) {
        return alocacoes.stream().filter(criterio).sorted(ORDEM_CRONOLOGICA).toList();
    }
}
