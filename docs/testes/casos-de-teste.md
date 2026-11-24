# Casos de Teste

> Issue: [TEST] Criar casos de teste`

## Alocação e conflitos (UC-004 e UC-005)

### CT-001 — Alocação válida ✅
**Requisitos:** RF-003, CA-003 · **Teste:** `GerenciadorGradeTest.alocacaoValida`
**Entrada:** professor disponível, turma, disciplina e sala livres, segunda-feira das 08:00 às 10:00.
**Resultado esperado:** alocação registrada na grade.

### CT-002 — Conflito de professor ✅
**Requisitos:** RF-004, RN-001, CA-004 · **Teste:** `GerenciadorGradeTest.conflitoDeProfessor`
**Entrada:** mesmo professor em outra turma e sala, segunda-feira das 09:00 às 11:00, havendo aula das 08:00 às 10:00.
**Resultado esperado:** alocação rejeitada com o conflito `PROFESSOR`.

### CT-003 — Conflito de sala ✅
**Requisitos:** RF-004, RN-002 · **Teste:** `GerenciadorGradeTest.conflitoDeSala`
**Entrada:** outro professor e outra turma na mesma sala, com horários que se cruzam.
**Resultado esperado:** alocação rejeitada com o conflito `SALA`.

### CT-004 — Conflito de turma ✅
**Requisitos:** RF-004, RN-003 · **Teste:** `GerenciadorGradeTest.conflitoDeTurma`
**Entrada:** mesma turma com outro professor e outra sala, com horários que se cruzam.
**Resultado esperado:** alocação rejeitada com o conflito `TURMA`.

### CT-005 — Horário fora da disponibilidade ✅
**Requisitos:** RF-002, RN-004 · **Teste:** `GerenciadorGradeTest.foraDaDisponibilidade`
**Entrada:** professor disponível das 08:00 às 12:00, alocação das 11:00 às 13:00.
**Resultado esperado:** alocação rejeitada com `INDISPONIBILIDADE_PROFESSOR`.

### CT-006 — Professor sem disponibilidade cadastrada ✅
**Requisitos:** RN-004 · **Teste:** `GerenciadorGradeTest.semDisponibilidade`
**Entrada:** professor que não informou nenhuma janela.
**Resultado esperado:** alocação rejeitada com `INDISPONIBILIDADE_PROFESSOR`.

### CT-007 — Aulas consecutivas não conflitam ✅
**Requisitos:** RF-003 · **Teste:** `GerenciadorGradeTest.aulasConsecutivas`
**Entrada:** uma aula das 08:00 às 10:00 e outra das 10:00 às 12:00, mesmo professor, turma e sala.
**Resultado esperado:** as duas são registradas.

### CT-008 — Horário inválido ✅
**Requisitos:** RF-003 · **Teste:** `GerenciadorGradeTest.horarioInvalido`
**Entrada:** início igual ao fim, ou início posterior ao fim.
**Resultado esperado:** operação rejeitada com erro de argumento inválido; a grade permanece vazia.

### CT-009 — Rejeição não altera a grade ✅
**Requisitos:** RN-006, CA-005 · **Teste:** `GerenciadorGradeTest.rejeicaoNaoAlteraGrade`
**Entrada:** tentativa de alocação com conflito.
**Resultado esperado:** a grade continua com o mesmo número de aulas.

### CT-010 — Desempenho da validação ✅
**Requisitos:** RNF-001, CA-008 · **Teste:** `GerenciadorGradeTest.desempenhoDaValidacao`
**Entrada:** grade com cerca de 2,5 mil aulas e uma nova alocação candidata.
**Resultado esperado:** detecção de conflito em menos de 2 segundos.

### CT-012 — Aulas em dias diferentes não conflitam ✅
**Requisitos:** RF-003 · **Teste:** `GerenciadorGradeTest.diasDiferentes`
**Entrada:** mesmo professor, turma e sala, no mesmo horário em dias diferentes.
**Resultado esperado:** as duas são registradas.

## Consulta e disponibilidade

### CT-011 — Consulta por professor, sala e turma ✅
**Requisitos:** RF-006, CA-006 · **Teste:** `GradeHorariaTest.consultas`
**Entrada:** grade com aulas de professores, salas e turmas diferentes.
**Resultado esperado:** cada consulta retorna apenas as aulas correspondentes, em ordem de dia e horário; professor sem aulas retorna lista vazia.

### CT-013 — Intervalo dentro da janela de disponibilidade ✅
**Requisitos:** RF-002 · **Teste:** `DisponibilidadeTest.coberturaDentroDaJanela`
**Entrada:** janela segunda 08:00–12:00; intervalo 08:00–10:00.
**Resultado esperado:** intervalo coberto.

### CT-014 — Intervalo fora da janela ou em outro dia ✅
**Requisitos:** RF-002 · **Teste:** `DisponibilidadeTest.coberturaForaDaJanela`
**Entrada:** intervalo que ultrapassa o fim da janela, começa antes dela ou cai em outro dia.
**Resultado esperado:** intervalo não coberto.

## Casos planejados (ainda sem implementação)

### CT-015 — Cadastro válido ✅
**Requisitos:** RF-001, CA-001
**Entrada:** professor, disciplina, sala ou turma com todos os campos obrigatórios.
**Resultado esperado:** registro salvo e exibido na listagem.

### CT-016 — Cadastro com campo obrigatório ausente ✅
**Requisitos:** RF-001
**Entrada:** disciplina sem nome.
**Resultado esperado:** sistema rejeita o cadastro e informa o campo com problema.

### CT-017 — Alteração de alocação com revalidação ✅
**Requisitos:** RF-005, CA-005
**Entrada:** mudança de uma aula para um horário que conflita com outra.
**Resultado esperado:** alteração rejeitada e aula original mantida.

### CT-018 — Login válido ✅
**Requisitos:** RF-007
**Entrada:** e-mail e senha corretos.
**Resultado esperado:** usuário autenticado com o perfil correspondente.

### CT-019 — Login com senha incorreta ✅
**Requisitos:** RF-007
**Entrada:** e-mail correto e senha errada.
**Resultado esperado:** acesso negado, com mensagem de erro.

### CT-020 — Professor tenta alterar a grade ✅
**Requisitos:** RF-007, RN-005, RNF-002, CA-007
**Entrada:** usuário com perfil Professor solicita criar ou alterar uma alocação.
**Resultado esperado:** acesso negado e grade inalterada.

### CT-021 — Persistência após reinício ✅
**Requisitos:** RNF-003, CA-009
**Entrada:** dados cadastrados e sistema reiniciado.
**Resultado esperado:** todos os dados continuam disponíveis.
