package br.edu.fatec.tatui.horarios;

import br.edu.fatec.tatui.horarios.dominio.DiaSemana;
import br.edu.fatec.tatui.horarios.dominio.Disciplina;
import br.edu.fatec.tatui.horarios.dominio.Disponibilidade;
import br.edu.fatec.tatui.horarios.dominio.GradeHoraria;
import br.edu.fatec.tatui.horarios.dominio.Professor;
import br.edu.fatec.tatui.horarios.dominio.Sala;
import br.edu.fatec.tatui.horarios.dominio.Turma;
import br.edu.fatec.tatui.horarios.servico.ConflitoHorarioException;
import br.edu.fatec.tatui.horarios.servico.GerenciadorGrade;
import java.time.LocalTime;

/** Demonstração em linha de comando do caso de uso "Montar Grade Horária". */
public class Main {

    public static void main(String[] args) {
        Professor professor = new Professor(1, "Prof. Exemplo", "Mestre");
        professor.cadastrarDisponibilidade(new Disponibilidade(DiaSemana.SEGUNDA, LocalTime.of(8, 0), LocalTime.of(12, 0)));

        Turma turma = new Turma(1, "ADS 3º semestre", "Noite", 35);
        Disciplina disciplina = new Disciplina(1, "Engenharia de Software", 80);
        Sala laboratorio = new Sala(1, "Laboratório 2", "Laboratório", 40);

        GerenciadorGrade gerenciador = new GerenciadorGrade(new GradeHoraria("2026-2"));

        System.out.println("Registrando aula das 08:00 às 10:00...");
        System.out.println("OK: " + gerenciador.alocar(professor, turma, disciplina, laboratorio,
                DiaSemana.SEGUNDA, LocalTime.of(8, 0), LocalTime.of(10, 0)));

        System.out.println("Tentando registrar outra aula das 09:00 às 11:00...");
        try {
            gerenciador.alocar(professor, turma, disciplina, laboratorio,
                    DiaSemana.SEGUNDA, LocalTime.of(9, 0), LocalTime.of(11, 0));
        } catch (ConflitoHorarioException e) {
            System.out.println("Rejeitada: " + e.getMessage());
        }
    }
}
