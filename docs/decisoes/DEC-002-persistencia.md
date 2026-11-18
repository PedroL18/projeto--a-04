# DEC-002 — Persistência por meio de interfaces de repositório

## Problema
O RNF-003 exige persistência dos dados. É preciso decidir como guardá-los sem acoplar as regras de negócio a uma tecnologia específica e sem atrasar o desenvolvimento do núcleo do sistema.

## Alternativas
1. Manter os dados só em memória.
2. Arquivos (JSON ou CSV).
3. Banco relacional embarcado (H2 ou SQLite) via JDBC.
4. Banco relacional em servidor (PostgreSQL ou MySQL).

## Decisão
Definir **interfaces de repositório** na camada de persistência (por exemplo, `GradeRepository`). Começar com uma **implementação em memória**.

## Justificativa
- As interfaces mantêm as regras de negócio independentes da tecnologia de armazenamento.
- A implementação em memória permitiu entregar e testar o núcleo (UC-004 e UC-005) antes de decidir o banco.
- Um banco embarcado atende ao RNF-003 sem exigir instalar e administrar um servidor, o que é adequado a um projeto acadêmico.
- Arquivos tornam as consultas por professor, sala e turma mais trabalhosas; um banco em servidor é mais custoso para a equipe configurar e demonstrar.

## Consequências
- Enquanto só existir a versão em memória, **o RNF-003 não está atendido**.
- Será preciso escrever e manter duas implementações de cada repositório.
- Os serviços passam a receber os repositórios por construtor (injeção manual de dependências).
- Troca futura para outro banco afeta apenas a camada de persistência.
