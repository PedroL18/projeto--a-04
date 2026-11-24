# Responsabilidades das Classes

> Issue: `[DESIGN] Definir responsabilidades das classes`

Princípios adotados: **responsabilidade única** (cada classe tem um motivo para mudar), **separação entre regra de negócio e orquestração** e **dependência apenas para baixo** (ver [arquitetura](arquitetura.md) e DEC-003).

## Classe: AlocacaoHorario ✅

**Responsabilidades:**
- representar uma aula (professor, turma, disciplina, sala, dia e horário);
- garantir que os próprios dados são válidos (campos obrigatórios e início antes do fim);
- dizer se se sobrepõe a outra aula e qual o tipo de conflito.

**Informações que mantém:** professor, turma, disciplina, sala, dia da semana, horário de início e horário de fim. É imutável.

**Operações:** `sobrepoe(outra)`, `conflitaCom(outra)`, `tipoConflitoCom(outra)`.

**Não é responsabilidade de AlocacaoHorario:**
- conhecer as demais aulas da grade;
- decidir se a aula será salva;
- verificar a disponibilidade do professor.

---

## Classe: GradeHoraria ✅

**Responsabilidades:**
- reunir as alocações de um semestre;
- atender às consultas por professor, sala e turma.

**Informações que mantém:** semestre e lista de alocações.

**Operações:** `adicionarAlocacao`, `removerAlocacao`, `consultarPorProfessor`, `consultarPorSala`, `consultarPorTurma`.

**Não é responsabilidade de GradeHoraria:**
- validar conflitos antes de aceitar uma aula;
- acessar o banco de dados;
- formatar a exibição do calendário.

---

## Classe: GerenciadorGrade ✅

**Responsabilidades:**
- orquestrar a criação de alocações;
- aplicar as regras de negócio RN-001 a RN-004 e RN-006 antes de salvar;
- rejeitar a alocação com o motivo do conflito.

**Informações que mantém:** a grade horária com a qual trabalha.

**Operações:** `alocar`, `detectarConflito`, `validarDisponibilidade` (e `alterar`, ✅).

**Não é responsabilidade de GerenciadorGrade:**
- guardar os dados dos professores, turmas e salas;
- acessar diretamente o banco de dados;
- controlar a interface ou autenticar usuários.

---

## Classe: Professor ✅

**Responsabilidades:**
- representar o docente;
- guardar suas janelas de disponibilidade;
- informar se está livre em um horário.

**Informações que mantém:** id, nome, titulação e disponibilidades.

**Operações:** `cadastrarDisponibilidade`, `estaDisponivel`.

**Não é responsabilidade de Professor:**
- conhecer as aulas já alocadas;
- decidir se uma alocação é aceita;
- autenticar-se (papel de `Usuario`, ✅).

---

## Classe: Disponibilidade ✅

**Responsabilidades:** representar uma janela de horário em um dia e dizer se cobre um intervalo.
**Informações que mantém:** dia da semana, horário de início e de fim.
**Operações:** `cobre(dia, inicio, fim)`.
**Não é responsabilidade de Disponibilidade:** saber a quem pertence nem validar alocações.

---

## Classes: Disciplina, Turma e Sala ✅

**Responsabilidades:** representar, respectivamente, a disciplina, o grupo de alunos e o ambiente físico, validando os próprios dados.
**Informações que mantêm:**
- `Disciplina`: id, nome, carga horária;
- `Turma`: id, nome, período, quantidade de alunos;
- `Sala`: id, nome, tipo, capacidade.

**Operações:** nenhuma além da validação no construtor (são registros de dados).
**Não é responsabilidade dessas classes:** conhecer horários ou alocações.

---

## Classe: Usuario (abstrata) ✅

**Responsabilidades:** representar quem acessa o sistema e autenticá-lo conforme o perfil.
**Informações que mantém:** id, nome, e-mail, senha (somente em forma protegida) e perfil.
**Operações:** `autenticar()`.
**Não é responsabilidade de Usuario:** regras de montagem da grade nem armazenamento das senhas.

## Classe: Coordenador ✅

**Responsabilidades:** representar a Coordenação e a Secretaria (perfil administrativo) e solicitar as operações de cadastro e montagem da grade.
**Informações que mantém:** departamento.
**Operações:** `cadastrarProfessor`, `cadastrarDisciplina`, `cadastrarTurma`, `cadastrarSala`, `gerarGrade`, `resolverConflito`.
**Não é responsabilidade de Coordenador:** implementar a validação de conflitos, que pertence a `GerenciadorGrade` e `AlocacaoHorario`.
