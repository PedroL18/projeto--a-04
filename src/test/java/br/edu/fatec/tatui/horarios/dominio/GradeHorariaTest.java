package br.edu.fatec.tatui.horarios.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GradeHorariaTest {

    private final Professor ana = new Professor(1, "Ana", "Mestre");
    private final Professor bruno = new Professor(2, "Bruno", "Doutor");
    private final Turma t1 = new Turma(1, "ADS-1", "Noite", 30);
    private final Turma t2 = new Turma(2, "ADS-2", "Noite", 30);
    private final Sala lab = new Sala(1, "Lab 1", "Laboratório", 40);
    private final Sala sala = new Sala(2, "Sala 5", "Sala", 40);
    private final Disciplina es = new Disciplina(1, "Engenharia de Software", 80);

    private AlocacaoHorario aula(Professor p, Turma t, Sala s, DiaSemana d, int ini, int fim) {
        return new AlocacaoHorario(p, t, es, s, d, LocalTime.of(ini, 0), LocalTime.of(fim, 0));
    }

    @Test
    @DisplayName("CT-011 — Consulta por professor, sala e turma retorna apenas as aulas correspondentes")
    void consultas() {
        GradeHoraria grade = new GradeHoraria("2026-2");
        AlocacaoHorario a1 = aula(ana, t1, lab, DiaSemana.TERCA, 10, 12);
        AlocacaoHorario a2 = aula(ana, t1, lab, DiaSemana.SEGUNDA, 8, 10);
        AlocacaoHorario a3 = aula(bruno, t2, sala, DiaSemana.SEGUNDA, 8, 10);
        grade.adicionarAlocacao(a1);
        grade.adicionarAlocacao(a2);
        grade.adicionarAlocacao(a3);

        assertEquals(List.of(a2, a1), grade.consultarPorProfessor(ana), "ordenadas por dia e horário");
        assertEquals(List.of(a3), grade.consultarPorSala(sala));
        assertEquals(List.of(a3), grade.consultarPorTurma(t2));
        assertTrue(grade.consultarPorProfessor(new Professor(99, "Sem aulas", null)).isEmpty());
    }

    @Test
    @DisplayName("Remoção de alocação")
    void remocao() {
        GradeHoraria grade = new GradeHoraria("2026-2");
        AlocacaoHorario a1 = aula(ana, t1, lab, DiaSemana.SEGUNDA, 8, 10);
        grade.adicionarAlocacao(a1);
        assertTrue(grade.removerAlocacao(a1));
        assertTrue(grade.getAlocacoes().isEmpty());
    }
}
