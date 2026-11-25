# 🌱 Impacta

## 🎯 Objetivo

Apoiar a logística de ações socioambientais da faculdade, permitindo o cadastro de voluntários e de ações comunitárias, o controle de inscrições e o acompanhamento do engajamento dos alunos por meio de uma pontuação de impacto.

## 🌟 Funcionalidades

**Voluntários**
- Cadastro com nome, e-mail e matrícula (o e-mail é único)
- Exibição de nome, quantidade de ações e pontuação de impacto acumulada
- Ranking em ordem decrescente de pontuação, com desempate por ordem alfabética do nome

**Ações Socioambientais**
- Cadastro de ações com título, descrição, data e capacidade máxima de voluntários
- Inscrição de voluntários nas ações
- Exibição dos detalhes da ação, com pontuação calculada e lista de inscritos

## 🌳 Tipos de Ação e Pontuação

| Tipo | Atributos específicos | Pontuação |
|------|----------------------|-----------|
| Plantio de Mudas | Quantidade de mudas | 5 pontos base + 2 por muda |
| Mutirão de Reciclagem | Duração em horas | 4 pontos por hora |
| Oficina Ecológica | Duração em horas e kit de material | 3 pontos por hora + 10 de bônus se houver kit |

## ⚠️ Exceções

- `EmailDuplicadoException`: tentativa de cadastrar um e-mail já existente
- `AcaoLotadaException`: inscrição em uma ação que atingiu a capacidade máxima
- `InscricaoDuplicadaException`: inscrição do mesmo voluntário duas vezes na mesma ação

## 🧪 Testes

Testes unitários com JUnit 5 que validam:
- O cálculo polimórfico de pontuação dos 3 tipos de ação
- A ordenação do ranking de voluntários
- O lançamento das exceções

## 💻 Tecnologias

- **Linguagem**: Java
- **Testes**: JUnit 5
- **Conceitos**: Orientação a Objetos, Herança, Polimorfismo e Tratamento de Exceções

## 🚀 Como Executar

O projeto não possui classe `Main`: as funcionalidades ficam na classe `Impacta` e são validadas pelos testes.

```bash[
[[[git clone https://github.com/SEU-USUARIO/impacta.git](https://github.com/Jamaci/Gest-o-de-A-es-Socioambientais/edit/main/README.md)](https://github.com/Jamaci/Gest-o-de-A-es-Socioambientais)](https://github.com/Jamaci/Gest-o-de-A-es-Socioambientais/tree/main)](https://github.com/Jamaci/Sistema-Impacta-Gestao-Socioambiental)
```

Abra a pasta em uma IDE (IntelliJ, Eclipse ou VS Code) com o JUnit 5 configurado e execute a classe `ImpactaTest`.

## 📁 Estrutura

```
Impacta.java                      # Classe principal do sistema
Voluntario.java                   # Voluntário
Acao.java                         # Classe abstrata das ações
PlantioMudas.java                 # Ação: plantio de mudas
MutiraoReciclagem.java            # Ação: mutirão de reciclagem
OficinaEcologica.java             # Ação: oficina ecológica
EmailDuplicadoException.java
AcaoLotadaException.java
InscricaoDuplicadaException.java
ImpactaTest.java                  # Testes unitários
```

## 🎓 Contexto

Projeto acadêmico desenvolvido no curso de Sistemas de Informação da Unifacisa.
