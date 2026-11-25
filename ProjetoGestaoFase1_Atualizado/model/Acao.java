package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Acao {

    private int id;
    private String titulo;
    private String descricao;
    private LocalDateTime data;
    private int maxParticipantes;
    private ArrayList<Voluntario> inscritos;

    public Acao(int id, String titulo, String descricao, LocalDateTime data, int maxParticipantes) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.maxParticipantes = maxParticipantes;
        this.inscritos = new ArrayList<Voluntario>();
    }

    public int getId() {
        return id;
    }

    public abstract int calcularPontuacao();

    public abstract String detalhesEspecificos();

    public boolean estaLotada() {
        return inscritos.size() >= maxParticipantes;
    }

    public boolean jaInscrito(Voluntario voluntario) {
        for (Voluntario v : inscritos) {
            if (v.getEmail().equals(voluntario.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public void adicionarVoluntario(Voluntario voluntario) {
        inscritos.add(voluntario);
    }

    public String exibirDetalhes() {
        String texto = "ID: " + id + "\n"
                + "Título: " + titulo + "\n"
                + "Descrição: " + descricao + "\n"
                + "Data: " + data + "\n"
                + "Pontuação: " + calcularPontuacao() + "\n"
                + detalhesEspecificos() + "\n"
                + "Voluntários inscritos (" + inscritos.size() + "/" + maxParticipantes + "):";

        for (Voluntario v : inscritos) {
            texto += "\n - " + v.getNome();
        }
        return texto;
    }
}
