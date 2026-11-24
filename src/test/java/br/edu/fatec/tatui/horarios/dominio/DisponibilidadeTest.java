package br.edu.fatec.tatui.horarios.dominio;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DisponibilidadeTest {

    private final Disponibilidade manha =
            new Disponibilidade(DiaSemana.SEGUNDA, LocalTime.of(8, 0), LocalTime.of(12, 0));

    @Test
    @DisplayName("CT-013 — Intervalo dentro da janela é coberto")
    void coberturaDentroDaJanela() {
        assertTrue(manha.cobre(DiaSemana.SEGUNDA, LocalTime.of(8, 0), LocalTime.of(10, 0)));
        assertTrue(manha.cobre(DiaSemana.SEGUNDA, LocalTime.of(8, 0), LocalTime.of(12, 0)));
    }

    @Test
    @DisplayName("CT-014 — Intervalo fora da janela ou em outro dia não é coberto")
    void coberturaForaDaJanela() {
        assertFalse(manha.cobre(DiaSemana.SEGUNDA, LocalTime.of(11, 0), LocalTime.of(13, 0)));
        assertFalse(manha.cobre(DiaSemana.SEGUNDA, LocalTime.of(7, 0), LocalTime.of(9, 0)));
        assertFalse(manha.cobre(DiaSemana.TERCA, LocalTime.of(8, 0), LocalTime.of(10, 0)));
    }

    @Test
    @DisplayName("Janela com início após o fim é rejeitada")
    void janelaInvalida() {
        assertThrows(IllegalArgumentException.class,
                () -> new Disponibilidade(DiaSemana.SEGUNDA, LocalTime.of(12, 0), LocalTime.of(8, 0)));
    }
}
