package model;

import java.time.LocalDateTime;

public class MutiraoReciclagem extends Acao {

    private int duracaoHoras;

    public MutiraoReciclagem(int id, String titulo, String descricao, LocalDateTime data,
                             int maxParticipantes, int duracaoHoras) {
        super(id, titulo, descricao, data, maxParticipantes);
        this.duracaoHoras = duracaoHoras;
    }

    @Override
    public int calcularPontuacao() {
        return 4 * duracaoHoras;
    }

    @Override
    public String detalhesEspecificos() {
        return "Tipo: Mutirão de Reciclagem | Duração: " + duracaoHoras + " hora(s)";
    }
}
