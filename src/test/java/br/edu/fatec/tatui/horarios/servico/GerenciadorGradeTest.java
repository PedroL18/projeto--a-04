package br.edu.fatec.tatui.horarios.servico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.edu.fatec.tatui.horarios.dominio.AlocacaoHorario;
import br.edu.fatec.tatui.horarios.dominio.DiaSemana;
import br.edu.fatec.tatui.horarios.dominio.Disciplina;
import br.edu.fatec.tatui.horarios.dominio.Disponibilidade;
import br.edu.fatec.tatui.horarios.dominio.GradeHoraria;
import br.edu.fatec.tatui.horarios.dominio.Professor;
import br.edu.fatec.tatui.horarios.dominio.Sala;
import br.edu.fatec.tatui.horarios.dominio.TipoConflito;
import br.edu.fatec.tatui.horarios.dominio.Turma;
import java.time.Duration;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GerenciadorGradeTest {

    private Professor ana;
    private Professor bruno;
    private Turma turma1;
    private Turma turma2;
    private Sala lab1;
    private Sala lab2;
    private Disciplina es;
    private GerenciadorGrade gerenciador;

    @BeforeEach
    void preparar() {
        ana = new Professor(1, "Ana", "Mestre");
        bruno = new Professor(2, "Bruno", "Doutor");
        for (DiaSemana dia : DiaSemana.values()) {
            ana.cadastrarDisponibilidade(new Disponibilidade(dia, LocalTime.of(7, 0), LocalTime.of(22, 0)));
            bruno.cadastrarDisponibilidade(new Disponibilidade(dia, LocalTime.of(7, 0), LocalTime.of(22, 0)));
        }
        turma1 = new Turma(1, "ADS-1", "Noite", 30);
        turma2 = new Turma(2, "ADS-2", "Noite", 30);
        lab1 = new Sala(1, "Lab 1", "Laboratório", 40);
        lab2 = new Sala(2, "Lab 2", "Laboratório", 40);
        es = new Disciplina(1, "Engenharia de Software", 80);
        gerenciador = new GerenciadorGrade(new GradeHoraria("2026-2"));
    }

    private AlocacaoHorario alocar(Professor p, Turma t, Sala s, DiaSemana d, int ini, int fim) {
        return gerenciador.alocar(p, t, es, s, d, LocalTime.of(ini, 0), LocalTime.of(fim, 0));
    }

    @Test
    @DisplayName("CT-001 — Alocação válida é registrada na grade")
    void alocacaoValida() {
        AlocacaoHorario a = alocar(ana, turma1, lab1, DiaSemana.SEGUNDA, 8, 10);
        assertEquals(1, gerenciador.getGrade().getAlocacoes().size());
        assertTrue(gerenciador.getGrade().getAlocacoes().contains(a));
    }

    @Test
    @DisplayName("CT-002 — Conflito de professor é bloqueado")
    void conflitoDeProfessor() {
        alocar(ana, turma1, lab1, DiaSemana.SEGUNDA, 8, 10);
        ConflitoHorarioException e = assertThrows(ConflitoHorarioException.class,
                () -> alocar(ana, turma2, lab2, DiaSemana.SEGUNDA, 9, 11));
        assertEquals(TipoConflito.PROFESSOR, e.getTipo());
    }

    @Test
    @DisplayName("CT-003 — Conflito de sala é bloqueado")
    void conflitoDeSala() {
        alocar(ana, turma1, lab1, DiaSemana.SEGUNDA, 8, 10);
        ConflitoHorarioException e = assertThrows(ConflitoHorarioException.class,
                () -> alocar(bruno, turma2, lab1, DiaSemana.SEGUNDA, 9, 11));
        assertEquals(TipoConflito.SALA, e.getTipo());
    }

    @Test
    @DisplayName("CT-004 — Conflito de turma é bloqueado")
    void conflitoDeTurma() {
        alocar(ana, turma1, lab1, DiaSemana.SEGUNDA, 8, 10);
        ConflitoHorarioException e = assertThrows(ConflitoHorarioException.class,
                () -> alocar(bruno, turma1, lab2, DiaSemana.SEGUNDA, 9, 11));
        assertEquals(TipoConflito.TURMA, e.getTipo());
    }

    @Test
    @DisplayName("CT-005 — Horário fora da disponibilidade do professor é bloqueado")
    void foraDaDisponibilidade() {
        Professor parcial = new Professor(3, "Carla", "Mestre");
        parcial.cadastrarDisponibilidade(new Disponibilidade(DiaSemana.SEGUNDA, LocalTime.of(8, 0), LocalTime.of(12, 0)));
        ConflitoHorarioException e = assertThrows(ConflitoHorarioException.class,
                () -> alocar(parcial, turma1, lab1, DiaSemana.SEGUNDA, 11, 13));
        assertEquals(TipoConflito.INDISPONIBILIDADE_PROFESSOR, e.getTipo());
    }

    @Test
    @DisplayName("CT-006 — Professor sem disponibilidade cadastrada não pode ser alocado")
    void semDisponibilidade() {
        Professor novo = new Professor(4, "Diego", "Especialista");
        ConflitoHorarioException e = assertThrows(ConflitoHorarioException.class,
                () -> alocar(novo, turma1, lab1, DiaSemana.SEGUNDA, 8, 10));
        assertEquals(TipoConflito.INDISPONIBILIDADE_PROFESSOR, e.getTipo());
    }

    @Test
    @DisplayName("CT-007 — Aulas consecutivas (fim = início) não conflitam")
    void aulasConsecutivas() {
        alocar(ana, turma1, lab1, DiaSemana.SEGUNDA, 8, 10);
        alocar(ana, turma1, lab1, DiaSemana.SEGUNDA, 10, 12);
        assertEquals(2, gerenciador.getGrade().getAlocacoes().size());
    }

    @Test
    @DisplayName("CT-008 — Horário com início igual ou posterior ao fim é rejeitado")
    void horarioInvalido() {
        assertThrows(IllegalArgumentException.class, () -> alocar(ana, turma1, lab1, DiaSemana.SEGUNDA, 10, 10));
        assertThrows(IllegalArgumentException.class, () -> alocar(ana, turma1, lab1, DiaSemana.SEGUNDA, 11, 9));
        assertTrue(gerenciador.getGrade().getAlocacoes().isEmpty());
    }

    @Test
    @DisplayName("CT-009 — Alocação rejeitada não altera a grade")
    void rejeicaoNaoAlteraGrade() {
        alocar(ana, turma1, lab1, DiaSemana.SEGUNDA, 8, 10);
        assertThrows(ConflitoHorarioException.class, () -> alocar(ana, turma2, lab2, DiaSemana.SEGUNDA, 8, 10));
        assertEquals(1, gerenciador.getGrade().getAlocacoes().size());
    }

    @Test
    @DisplayName("CT-010 — Validação de conflito responde em menos de 2 segundos com grade grande")
    void desempenhoDaValidacao() {
        // 6 dias x 14 horas x 30 salas = 2.520 aulas já registradas
        for (DiaSemana dia : DiaSemana.values()) {
            for (int hora = 7; hora < 21; hora++) {
                for (int s = 1; s <= 30; s++) {
                    Professor p = new Professor(1000 + s, "P" + s, null);
                    p.cadastrarDisponibilidade(new Disponibilidade(dia, LocalTime.of(7, 0), LocalTime.of(22, 0)));
                    gerenciador.alocar(p, new Turma(1000 + s, "T" + s, "Noite", 30), es,
                            new Sala(1000 + s, "S" + s, "Sala", 40), dia, LocalTime.of(hora, 0), LocalTime.of(hora + 1, 0));
                }
            }
        }
        AlocacaoHorario candidata = new AlocacaoHorario(ana, turma1, es, lab1,
                DiaSemana.SABADO, LocalTime.of(21, 0), LocalTime.of(22, 0));
        assertTimeout(Duration.ofSeconds(2), () -> gerenciador.detectarConflito(candidata));
    }

    @Test
    @DisplayName("CT-012 — Aulas em dias diferentes não conflitam")
    void diasDiferentes() {
        alocar(ana, turma1, lab1, DiaSemana.SEGUNDA, 8, 10);
        alocar(ana, turma1, lab1, DiaSemana.TERCA, 8, 10);
        assertEquals(2, gerenciador.getGrade().getAlocacoes().size());
    }
}
