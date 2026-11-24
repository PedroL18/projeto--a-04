# Backlog Inicial

> Issue: `[PLAN] Criar backlog inicial`

Este backlog transforma os requisitos de [`docs/requisitos/requisitos.md`](../requisitos/requisitos.md) em atividades. A relação segue o formato **Requisito → Issue → Atividade**.


## RF-001 — Gerenciar cadastros de professores, disciplinas, salas e turmas (Essencial)

→ Issue #BL-01 — Implementar cadastro de professores, disciplinas, salas e turmas ✅
→ Issue #BL-02 — Validar dados obrigatórios dos cadastros ✅ *(as classes de domínio já validam nome e valores no construtor)*
→ Issue #BL-03 — Criar testes dos cadastros ✅

## RF-002 — Informar disponibilidade do professor (Essencial)

→ Issue #BL-04 — Implementar registro, atualização e remoção de disponibilidade ✅ *(o registro já existe em `Professor.cadastrarDisponibilidade`)*
→ Issue #BL-05 — Criar testes de disponibilidade ✅ *(CT-013 e CT-014)*

## RF-003 — Montar a grade horária (Essencial)

→ Issue #BL-06 — Implementar a montagem da grade (`GerenciadorGrade.alocar`) ✅
→ Issue #BL-07 — Criar testes da alocação de horários ✅ *(CT-001, CT-007, CT-008, CT-012)*

## RF-004 — Detectar e bloquear conflitos de horário (Essencial)

→ Issue #BL-08 — Implementar a detecção de conflitos de professor, sala e turma ✅
→ Issue #BL-09 — Criar testes dos cenários de conflito ✅ *(CT-002 a CT-006 e CT-009)*

## RF-005 — Alterar a grade com revalidação (Importante)

→ Issue #BL-10 — Implementar alteração e remoção de alocações com revalidação ✅
→ Issue #BL-11 — Criar testes da alteração de alocações ✅

## RF-006 — Consultar o calendário (Importante)

→ Issue #BL-12 — Implementar consultas por professor, sala e turma ✅ *(domínio: `GradeHoraria.consultarPor*`)*
→ Issue #BL-13 — Criar a interface de consulta do calendário ✅

## RF-007 / RN-005 / RNF-002 — Autenticação e controle de acesso (Importante)

→ Issue #BL-14 — Implementar `Usuario`, `Coordenador` e `Professor` com autenticação ✅
→ Issue #BL-15 — Implementar controle de acesso por perfil (RBAC) ✅
→ Issue #BL-16 — Criar testes de autenticação e de acesso negado ✅

## RNF-003 — Persistência dos dados (Essencial)

→ Issue #BL-17 — Implementar a camada de persistência (ver DEC-002) ✅
→ Issue #BL-18 — Criar testes de persistência ✅

## RNF-001 — Validação em menos de 2 segundos (Importante)

→ Issue #BL-19 — Criar teste de desempenho da validação ✅ *(CT-010)*

## RNF-004 — Interface simples (Desejável)

→ Issue #BL-20 — Implementar a interface do sistema ✅
## RNF-005 — Versionamento e documentação (Importante)

→ Issue #BL-21 — Configurar o GitHub Actions (compilação e testes) ✅
