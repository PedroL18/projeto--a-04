# DEC-003 — Organização das classes e tipos do domínio

**Status:** Aceita · **Issue:** `[DECISION] Registrar decisões de arquitetura`

## Problema
Era preciso decidir onde ficam as regras de conflito e como representar dados como dia da semana e horário, para manter o código simples, correto e testável.

## Alternativas
1. Colocar toda a validação em uma única classe de serviço.
2. Distribuir a regra: a entidade sabe se conflita com outra, o serviço orquestra e decide.
3. Representar dia e horário como `String`, como no modelo inicial do documento.
4. Usar `enum DiaSemana` e `java.time.LocalTime`.

## Decisão
- A regra "estas duas aulas conflitam?" fica em `AlocacaoHorario`; a orquestração (verificar disponibilidade, varrer a grade, registrar ou rejeitar) fica em `GerenciadorGrade`.
- `AlocacaoHorario` é **imutável** e valida seus dados no construtor.
- Dia da semana é um `enum DiaSemana` e horários são `LocalTime`.
- Entidades de dados simples (`Disciplina`, `Turma`, `Sala`, `Disponibilidade`) são `record` do Java 17.
- Conflitos são sinalizados por `ConflitoHorarioException`, que carrega um `TipoConflito`.

## Justificativa
- Cada classe fica com uma única responsabilidade.
- Tipos fortes impedem valores inválidos (por exemplo, um dia escrito errado) e permitem comparar horários corretamente.
- Imutabilidade evita que uma aula já validada seja alterada sem nova validação (RN-006).
- Records reduzem código repetitivo.

## Consequências
- O modelo do documento original (`diaSemana: String`, `horarioInicio: Time`) foi atualizado para os tipos acima; o diagrama de classes reflete essa mudança.
- Alterar uma aula significa criar uma nova alocação e revalidá-la (UC-006).
- A varredura da grade é linear no número de aulas. Foi medida no CT-010 com cerca de 2,5 mil aulas, dentro do limite de 2 s do RNF-001; se a grade crescer muito, será preciso indexar por dia.
