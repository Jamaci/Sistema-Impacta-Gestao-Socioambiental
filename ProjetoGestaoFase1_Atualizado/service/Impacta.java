package service;

import exceptions.AcaoLotadaException;
import exceptions.EmailDuplicadoException;
import exceptions.InscricaoDuplicadaException;
import model.Acao;
import model.OficinaEcologica;
import model.PlantioMudas;
import model.MutiraoReciclagem;
import model.Voluntario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Impacta {

    private ArrayList<Voluntario> voluntarios;
    private ArrayList<Acao> acoes;
    private int proximoId;

    public Impacta() {
        voluntarios = new ArrayList<Voluntario>();
        acoes = new ArrayList<Acao>();
        proximoId = 1;
    }

    // Parte 1: Voluntários

    public boolean cadastrarVoluntario(String nome, String email, String matricula) {
        if (buscarVoluntario(email) != null) {
            throw new EmailDuplicadoException("E-mail já cadastrado: " + email);
        }
        voluntarios.add(new Voluntario(nome, email, matricula));
        return true;
    }

    public String exibirVoluntario(String email) {
        Voluntario v = buscarVoluntario(email);
        if (v == null) {
            return "Voluntário não encontrado";
        }
        return v.exibir();
    }

    public String[] listarVoluntarios() {
        ArrayList<Voluntario> copia = new ArrayList<Voluntario>(voluntarios);

        Collections.sort(copia, new Comparator<Voluntario>() {
            @Override
            public int compare(Voluntario a, Voluntario b) {
                if (a.getPontuacao() != b.getPontuacao()) {
                    return b.getPontuacao() - a.getPontuacao();
                }
                return a.getNome().compareToIgnoreCase(b.getNome());
            }
        });

        String[] lista = new String[copia.size()];
        for (int i = 0; i < copia.size(); i++) {
            lista[i] = copia.get(i).exibir();
        }
        return lista;
    }

    // Parte 2: Ações

    public int cadastrarPlantio(String titulo, String descricao, String data,
                                int maxParticipantes, int qtdMudas) {
        int id = proximoId++;
        acoes.add(new PlantioMudas(id, titulo, descricao, LocalDateTime.parse(data),
                maxParticipantes, qtdMudas));
        return id;
    }

    public int cadastrarMutirao(String titulo, String descricao, String data,
                                int maxParticipantes, int duracaoHoras) {
        int id = proximoId++;
        acoes.add(new MutiraoReciclagem(id, titulo, descricao, LocalDateTime.parse(data),
                maxParticipantes, duracaoHoras));
        return id;
    }

    public int cadastrarOficina(String titulo, String descricao, String data,
                                int maxParticipantes, int duracaoHoras, boolean kitMaterial) {
        int id = proximoId++;
        acoes.add(new OficinaEcologica(id, titulo, descricao, LocalDateTime.parse(data),
                maxParticipantes, duracaoHoras, kitMaterial));
        return id;
    }

    public boolean inscreverVoluntario(String emailVoluntario, int idAcao) {
        Voluntario voluntario = buscarVoluntario(emailVoluntario);
        Acao acao = buscarAcao(idAcao);

        if (voluntario == null || acao == null) {
            return false;
        }
        if (acao.jaInscrito(voluntario)) {
            throw new InscricaoDuplicadaException("Voluntário já inscrito nesta ação");
        }
        if (acao.estaLotada()) {
            throw new AcaoLotadaException("A ação está lotada");
        }

        acao.adicionarVoluntario(voluntario);
        voluntario.adicionarParticipacao(acao.calcularPontuacao());
        return true;
    }

    public String exibirDetalhesAcao(int idAcao) {
        Acao acao = buscarAcao(idAcao);
        if (acao == null) {
            return "Ação não encontrada";
        }
        return acao.exibirDetalhes();
    }

    // Métodos auxiliares

    private Voluntario buscarVoluntario(String email) {
        for (Voluntario v : voluntarios) {
            if (v.getEmail().equals(email)) {
                return v;
            }
        }
        return null;
    }

    private Acao buscarAcao(int id) {
        for (Acao a : acoes) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }
}
