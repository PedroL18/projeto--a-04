# Estratégia de Testes

> Issue: `[TEST] Definir estratégia de testes`

## Objetivo
Verificar, de forma automática e repetível, que o sistema cumpre os requisitos de [`requisitos.md`](../requisitos/requisitos.md), em especial a detecção de conflitos de horário.

## O que será testado
- **Prioridade alta (requisitos Essenciais):** alocação, conflitos de professor, sala e turma, disponibilidade, cadastros e persistência.
- **Prioridade média (Importantes):** alteração da grade, consultas, autenticação e acesso por perfil, desempenho da validação.
- **Desejável:** interface (apenas verificação manual no início).
- Para cada requisito, cenários **válidos e inválidos** (ver [casos de teste](casos-de-teste.md)).

## Quando será testado
- **A cada alteração:** o autor roda `mvn test` localmente antes do commit e antes de abrir o Pull Request.
- **A cada Pull Request e push:** o GitHub Actions compila e executa todos os testes ([`ci.yml`](../../.github/workflows/ci.yml)).
- **Antes de cada release (v0.1, v0.2, v0.3 e v1.0):** execução completa e conferência manual dos critérios de aceitação pendentes.

## Quem é responsável
- **Autor da funcionalidade:** escreve os testes junto com o código e garante que passam.
- **Revisor do Pull Request (outro integrante):** confere se os testes cobrem os cenários do requisito, inclusive os inválidos.
- **Equipe:** acompanha o resultado do GitHub Actions; falha sem justificativa impede o merge.

## Critérios
- Todos os testes passam (`mvn test` sem falhas), localmente e no GitHub Actions.
- Cada requisito Essencial possui ao menos um caso de teste válido e um inválido, automatizados.
- Toda correção de defeito vem acompanhada de um teste que o reproduza.
- Um teste que falha de forma intermitente deve ser corrigido ou removido, não ignorado.

## Como executar
```bash
mvn test
```
## Convenções
- Nome da classe de teste: `<Classe>Test`.
- Cada teste automatizado de um caso de teste usa `@DisplayName("CT-XXX — descrição")`, para rastrear o caso até o código.
- Dados dos testes são criados no próprio teste (sem depender de arquivos externos).
