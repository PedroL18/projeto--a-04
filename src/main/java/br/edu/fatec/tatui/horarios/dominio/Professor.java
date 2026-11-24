package br.edu.fatec.tatui.horarios.dominio;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Docente que ministra aulas e informa sua disponibilidade de horários. */
public class Professor {

    private final int id;
    private final String nome;
    private final String titulacao;
    private final List<Disponibilidade> disponibilidades = new ArrayList<>();

    public Professor(int id, String nome, String titulacao) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do professor é obrigatório");
        }
        this.id = id;
        this.nome = nome;
        this.titulacao = titulacao;
    }

    public void cadastrarDisponibilidade(Disponibilidade disponibilidade) {
        disponibilidades.add(Objects.requireNonNull(disponibilidade, "A disponibilidade é obrigatória"));
    }

    /** Indica se o professor informou uma janela que cobre todo o intervalo. */
    public boolean estaDisponivel(DiaSemana dia, LocalTime inicio, LocalTime fim) {
        return disponibilidades.stream().anyMatch(d -> d.cobre(dia, inicio, fim));
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTitulacao() {
        return titulacao;
    }

    public List<Disponibilidade> getDisponibilidades() {
        return Collections.unmodifiableList(disponibilidades);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Professor outro && outro.id == id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return nome;
    }
}
