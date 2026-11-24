package br.edu.fatec.tatui.horarios.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AlocacaoHorarioTest {

    private final Professor ana = new Professor(1, "Ana", "Mestre");
    private final Turma turma = new Turma(1, "ADS-1", "Noite", 30);
    private final Sala sala = new Sala(1, "Lab 1", "Laboratório", 40);
    private final Disciplina es = new Disciplina(1, "Engenharia de Software", 80);

    private AlocacaoHorario aula(DiaSemana d, int ini, int fim) {
        return new AlocacaoHorario(ana, turma, es, sala, d, LocalTime.of(ini, 0), LocalTime.of(fim, 0));
    }

    @Test
    @DisplayName("Horários que se cruzam no mesmo dia se sobrepõem")
    void sobreposicao() {
        assertTrue(aula(DiaSemana.SEGUNDA, 8, 10).sobrepoe(aula(DiaSemana.SEGUNDA, 9, 11)));
        assertTrue(aula(DiaSemana.SEGUNDA, 8, 12).sobrepoe(aula(DiaSemana.SEGUNDA, 9, 10)));
    }

    @Test
    @DisplayName("Horários consecutivos ou em dias diferentes não se sobrepõem")
    void semSobreposicao() {
        assertFalse(aula(DiaSemana.SEGUNDA, 8, 10).sobrepoe(aula(DiaSemana.SEGUNDA, 10, 12)));
        assertFalse(aula(DiaSemana.SEGUNDA, 8, 10).sobrepoe(aula(DiaSemana.TERCA, 8, 10)));
    }

    @Test
    @DisplayName("Conflito de professor é identificado e horários sem cruzamento não geram conflito")
    void tipoDeConflito() {
        AlocacaoHorario base = aula(DiaSemana.SEGUNDA, 8, 10);
        assertEquals(Optional.of(TipoConflito.PROFESSOR), base.tipoConflitoCom(aula(DiaSemana.SEGUNDA, 9, 11)));
        assertEquals(Optional.empty(), base.tipoConflitoCom(aula(DiaSemana.SEGUNDA, 10, 11)));
    }

    @Test
    @DisplayName("Campos obrigatórios nulos são rejeitados")
    void camposNulos() {
        assertThrows(NullPointerException.class, () -> new AlocacaoHorario(null, turma, es, sala,
                DiaSemana.SEGUNDA, LocalTime.of(8, 0), LocalTime.of(10, 0)));
    }
}
