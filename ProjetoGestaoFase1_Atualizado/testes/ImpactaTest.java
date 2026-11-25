package testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import exceptions.AcaoLotadaException;
import exceptions.EmailDuplicadoException;
import exceptions.InscricaoDuplicadaException;
import model.Acao;
import model.OficinaEcologica;
import model.PlantioMudas;
import model.MutiraoReciclagem;
import service.Impacta;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class ImpactaTest {

    private LocalDateTime data = LocalDateTime.of(2026, 12, 5, 14, 0);

    @Test
    public void testePontuacaoPlantio() {
        Acao acao = new PlantioMudas(1, "Plantio", "Praça", data, 10, 10);
        assertEquals(25, acao.calcularPontuacao());
    }

    @Test
    public void testePontuacaoMutirao() {
        Acao acao = new MutiraoReciclagem(2, "Mutirão", "Bairro", data, 10, 3);
        assertEquals(12, acao.calcularPontuacao());
    }

    @Test
    public void testePontuacaoOficinaComKit() {
        Acao acao = new OficinaEcologica(3, "Oficina", "Campus", data, 10, 2, true);
        assertEquals(16, acao.calcularPontuacao());
    }

    @Test
    public void testePontuacaoOficinaSemKit() {
        Acao acao = new OficinaEcologica(4, "Oficina", "Campus", data, 10, 2, false);
        assertEquals(6, acao.calcularPontuacao());
    }

    @Test
    public void testePolimorfismoComLista() {
        Acao[] acoes = new Acao[3];
        acoes[0] = new PlantioMudas(1, "A", "a", data, 5, 1);
        acoes[1] = new MutiraoReciclagem(2, "B", "b", data, 5, 1);
        acoes[2] = new OficinaEcologica(3, "C", "c", data, 5, 1, true);

        assertEquals(7, acoes[0].calcularPontuacao());
        assertEquals(4, acoes[1].calcularPontuacao());
        assertEquals(13, acoes[2].calcularPontuacao());
    }

    // Ranking de voluntários

    @Test
    public void testeOrdenacaoRanking() {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Cristiano", "cristiano@email.com", "4");
        sistema.cadastrarVoluntario("Samuel", "samuel@email.com", "3");
        sistema.cadastrarVoluntario("Gabriel", "gabriel@email.com", "2");
        sistema.cadastrarVoluntario("Tatiana", "tatiana@email.com", "1");

        int mutirao = sistema.cadastrarMutirao("Mutirão", "Reciclagem", "2026-12-05T14:00", 10, 3);
        int plantio = sistema.cadastrarPlantio("Plantio", "Mudas", "2026-12-06T08:00", 10, 10);

        sistema.inscreverVoluntario("tatiana@email.com", mutirao);
        sistema.inscreverVoluntario("gabriel@email.com", mutirao);
        sistema.inscreverVoluntario("samuel@email.com", plantio);

        String[] ranking = sistema.listarVoluntarios();

        assertTrue(ranking[0].startsWith("Nome: Samuel"));
        assertTrue(ranking[1].startsWith("Nome: Gabriel"));
        assertTrue(ranking[2].startsWith("Nome: Tatiana"));
        assertTrue(ranking[3].startsWith("Nome: Cristiano"));
    }

    // Exceções

    @Test
    public void testeEmailDuplicado() {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Tatiana", "tatiana@email.com", "1");

        assertThrows(EmailDuplicadoException.class, () -> {
            sistema.cadastrarVoluntario("Outra Tatiana", "tatiana@email.com", "2");
        });
    }

    @Test
    public void testeAcaoLotada() {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Tatiana", "tatiana@email.com", "1");
        sistema.cadastrarVoluntario("Gabriel", "gabriel@email.com", "2");
        int id = sistema.cadastrarMutirao("Mutirão", "Teste", "2026-12-05T14:00", 1, 2);

        sistema.inscreverVoluntario("tatiana@email.com", id);

        assertThrows(AcaoLotadaException.class, () -> {
            sistema.inscreverVoluntario("gabriel@email.com", id);
        });
    }

    @Test
    public void testeInscricaoDuplicada() {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Tatiana", "tatiana@email.com", "1");
        int id = sistema.cadastrarMutirao("Mutirão", "Teste", "2026-12-05T14:00", 5, 2);

        sistema.inscreverVoluntario("tatiana@email.com", id);

        assertThrows(InscricaoDuplicadaException.class, () -> {
            sistema.inscreverVoluntario("tatiana@email.com", id);
        });
    }
}
