
## Levantamento dos Requisitos

Nossa equipe identificou alguns requisitos que devem ser implementados no sistema e suas funcionalidades.

Projeto: Sistema de Gestão e Conciliação de Horários Acadêmicos Instituição: FATEC Itu — Lab. Engenharia de Software (Atividade ProjetoA04) Problema: Irregularidades nos horários de laboratório da faculdade

Issue: [REQ] Levantar requisitos do sistema

Nossa equipe identificou os requisitos necessários para que o sistema resolva o problema apresentado: a dificuldade de organizar horários, conciliar a disponibilidade dos professores e evitar sobreposições de salas e turmas.


Requisitos Funcionais (RF)

RF-001	Gerenciar cadastros de professores, disciplinas, salas e turmas
RF-002	Permitir que o professor informe e atualize sua disponibilidade de horários
RF-003	Montar a grade horária associando professor, turma, disciplina e sala
RF-004	Detectar e bloquear automaticamente conflitos de horário (professor, sala ou turma em duplicidade)
RF-005	Alterar a grade horária com revalidação instantânea dos conflitos
RF-006	Consultar o calendário acadêmico consolidado por professor, sala e turma
RF-007	Autenticar usuários e controlar o acesso conforme o perfil

Regras de Negócio (RN)

RN-001	Um professor não pode ser alocado em duas turmas no mesmo dia e horário
RN-002	Uma sala não pode receber duas turmas no mesmo dia e horário
RN-003	Uma turma não pode ter duas aulas simultâneas
RN-004	O professor só pode ser alocado dentro dos horários em que informou disponibilidade
RN-005	Somente a Coordenação e a Secretaria podem criar ou alterar a grade horária
RN-006	Toda alocação deve ser validada antes de ser salva

Requisitos Não Funcionais (RNF)

RNF-001	A validação e a detecção de conflitos devem levar menos de 2,0 segundos
RNF-002	O acesso deve ser controlado por perfil (RBAC: Coordenação x Professor)
RNF-003	O sistema deverá possuir persistência dos dados
RNF-004	A interface deve ser simples e de fácil uso para professores e secretaria
RNF-005	O código deve ser versionado em repositório (GitHub/Gitea) e documentado no README

Restrições do Sistema (RS)

RS-001	O projeto deve ser desenvolvido e versionado em repositório GitHub/Gitea
RS-002	O sistema deve ser modelado em UML (casos de uso, classes, sequência e atividades)
RS-003	O desenvolvimento deve respeitar as entregas incrementais do semestre (v0.1 na semana 08, v0.2 na semana 13, v0.3 na semana 18 e v1.0 na semana 20)
RS-004	O projeto é desenvolvido por uma equipe de três integrantes, dentro do prazo acadêmico
RS-005	O escopo se limita à gestão de horários (cadastros, disponibilidade, grade e consulta); não inclui matrícula, notas ou frequência

Critérios de Aceitação (CA)

ID	Critério (Dado / Quando / Então)
CA-001 (RF-001)	Dado um usuário da Coordenação, quando ele cadastrar um professor, disciplina, sala ou turma, então o registro é salvo e aparece na listagem correspondente
CA-002 (RF-002, RN-004)	Dado um professor autenticado, quando ele registrar ou alterar sua disponibilidade, então os novos horários passam a valer para as próximas alocações
CA-003 (RF-003)	Dado professor, turma, disciplina e sala válidos, quando a Coordenação criar uma alocação sem conflito, então ela é gravada na grade
CA-004 (RF-004, RN-001, RN-002, RN-003)	Dado uma alocação que repete professor, sala ou turma em um mesmo dia e horário, quando a Coordenação tentar salvá-la, então o sistema bloqueia o registro e exibe o alerta de conflito
CA-005 (RF-005, RN-006)	Dado uma alocação existente, quando ela for alterada, então o sistema revalida os conflitos antes de salvar
CA-006 (RF-006)	Dado uma grade cadastrada, quando o usuário consultar por professor, sala ou turma, então são exibidas apenas as aulas correspondentes
CA-007 (RF-007, RN-005)	Dado um usuário com perfil de Professor, quando ele tentar criar ou alterar a grade, então o acesso é negado
CA-008 (RNF-001)	Dado qualquer alocação enviada para validação, quando o sistema verificar conflitos, então a resposta é retornada em menos de 2,0 segundos
CA-009 (RNF-003)	Dado dados cadastrados, quando o sistema for reiniciado, então todos os dados continuam disponíveis


## Identificação dos Atores


Issue: [REQ] Identificar atores do sistema

Ator: Coordenação e Secretaria

Descrição: Responsáveis diretos pela elaboração, organização, validação e manutenção da grade horária. Possuem privilégios administrativos.

Objetivo: Montar e manter uma grade horária sem conflitos, conciliando disciplinas, turmas, professores e salas.

Principais interações:

cadastrar professores, disciplinas, salas e turmas;
montar a grade horária;
alterar alocações existentes;
resolver conflitos de agendamento;
consultar o calendário por professor, sala e turma.
Ator: Professor (Corpo Docente)

Descrição: Docente que ministra as aulas e precisa de transparência sobre sua agenda e suas alterações.

Objetivo: Informar sua disponibilidade e acompanhar suas aulas de forma clara e atualizada.

Principais interações:

autenticar-se no sistema;
cadastrar e atualizar sua disponibilidade de horários;
consultar sua agenda de aulas;
acompanhar atualizações do calendário.
Ator de sistema: Motor de Validação de Conflitos

Descrição: Componente interno que verifica automaticamente as alocações antes de gravá-las.

Objetivo: Garantir que nenhuma alocação conflitante seja registrada.

Principais interações:

receber a alocação enviada pela Coordenação;
varrer a base de horários ocupados;
bloquear a alocação e retornar o alerta de conflito, ou aprová-la.


## Priorização dos Requisitos


Issue: [REQ] Priorizar requisitos

Classificação: Essencial (sem ele o sistema não resolve o problema), Importante (agrega valor significativo, mas o sistema funciona sem ele) e Desejável (melhora a experiência, pode ficar para uma versão futura).

ID	Requisito	Prioridade
RF-001	Gerenciar cadastros	Essencial
RF-002	Informar disponibilidade	Essencial
RF-003	Montar a grade horária	Essencial
RF-004	Detectar e bloquear conflitos	Essencial
RF-005	Alterar a grade com revalidação	Importante
RF-006	Consultar o calendário	Importante
RF-007	Autenticar e controlar acesso	Importante
RN-001 a RN-004, RN-006	Regras de conflito, disponibilidade e validação	Essencial
RN-005	Somente Coordenação e Secretaria alteram a grade	Importante
RNF-001	Validação em menos de 2,0 s	Importante
RNF-002	Controle de acesso por perfil (RBAC)	Importante
RNF-003	Persistência dos dados	Essencial
RNF-004	Interface simples	Desejável
RNF-005	Versionamento e README	Importante
Justificativa das principais decisões
RF-001, RF-002 e RF-003 são essenciais: sem os cadastros, a disponibilidade e a montagem da grade, não existe base para alocar horários.
RF-004 e as regras RN-001 a RN-004 e RN-006 são essenciais: a detecção de conflitos é o núcleo do sistema e a resposta direta ao problema (choques de professor, sala e turma).
RNF-003 é essencial: sem persistência, a grade se perderia a cada reinício e o sistema não teria utilidade prática.
RF-005 é importante e não essencial: é possível usar o sistema montando a grade do zero, mas o ajuste rápido reduz o retrabalho da gestão.
RF-006 é importante: a consulta é o que entrega valor aos professores, mas o sistema já resolve os conflitos mesmo sem ela.
RF-007, RN-005 e RNF-002 são importantes: protegem a grade contra alterações indevidas. Podem entrar logo depois do núcleo, já que o protótipo (v0.3) foca na validação.
RNF-001 é importante: a validação rápida melhora o uso, mas o sistema continua correto se ela for mais lenta.
RNF-004 é desejável: a interface pode ser refinada depois, com o núcleo já funcionando.
RNF-005 é importante: o versionamento e o README são exigidos pelas entregas da disciplina e facilitam o trabalho em equipe.
