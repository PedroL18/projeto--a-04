# Arquitetura Inicial

> Issue: `[ARCH] Definir arquitetura inicial`

## Arquitetura escolhida

**Arquitetura em camadas, em um único aplicativo (monolito) Java.** Cada camada só depende da camada imediatamente abaixo:

```text
Interface
   ↓
Serviços
   ↓
Domínio
   ↓
Persistência
```

## Componentes e responsabilidades

| Camada | Pacote | Responsabilidade | Principais elementos | Status |
|--------|--------|------------------|----------------------|--------|
| Interface | `br.edu.fatec.tatui.horarios` | Receber as ações do usuário e exibir resultados. Não contém regra de negócio. | `Main` (demonstração em linha de comando) | Inicial |
| Serviços | `...horarios.servico` | Orquestrar os casos de uso e aplicar as regras de validação antes de salvar. | `GerenciadorGrade`, `ConflitoHorarioException` | Implementada |
| Domínio | `...horarios.dominio` | Representar os conceitos do problema e as regras que pertencem a cada um. | `AlocacaoHorario`, `GradeHoraria`, `Professor`, `Disponibilidade`, `Disciplina`, `Turma`, `Sala`, `DiaSemana`, `TipoConflito` | Implementada |
| Persistência | `...horarios.persistencia` | Gravar e recuperar os dados. | Interfaces de repositório (ver DEC-002) | Planejada |

**Regras de dependência**
- O domínio não conhece serviços, interface nem persistência.
- A interface não acessa a persistência diretamente.
- A persistência é acessada pelos serviços por meio de interfaces, o que permite trocar a implementação.

## Por que foi escolhida

- **O problema está concentrado em regras de negócio** (conflitos de professor, sala e turma, e disponibilidade). Em camadas, essas regras ficam no domínio e podem ser testadas sem interface nem banco de dados.
- **O prazo e a equipe são pequenos** (três integrantes, um semestre). Um único aplicativo evita o custo de distribuir o sistema.
- **Separa o que muda em velocidades diferentes:** a interface e a persistência devem evoluir (linha de comando, depois interface gráfica ou web; memória, depois banco) sem mexer nas regras.
- **Permite dividir o trabalho** entre os integrantes por camada ou por caso de uso, com menos conflitos no Git.

## Alternativas consideradas

| Alternativa | Por que não foi escolhida |
|-------------|---------------------------|
| MVC simples (modelo, visão, controlador) | Não deixa claro onde ficam as regras de validação; elas tendem a ir para o controlador, dificultando os testes. |
| Arquitetura hexagonal (portas e adaptadores) | Adequada, mas mais cerimônia do que o projeto precisa agora. A separação por interfaces de repositório já traz parte do benefício. |
| Microsserviços | Excessiva para uma equipe de três e um sistema de escopo reduzido; exigiria infraestrutura e comunicação entre serviços. |

## Vantagens esperadas

- Regras de conflito testáveis isoladamente (já demonstrado: os testes de `GerenciadorGrade` não precisam de banco nem de interface).
- Troca de persistência e de interface sem alterar o domínio.
- Responsabilidades claras, alinhadas ao diagrama de classes e ao arquivo de [responsabilidades](responsabilidades-classes.md).

## Limitações e consequências

- Mais classes e arquivos do que uma solução mínima.
- Risco de a camada de serviços virar um "depósito de regras" se o domínio não for mantido rico. Para evitar isso, regras que pertencem a uma entidade (por exemplo, `AlocacaoHorario.conflitaCom`) ficam nela.
- Como é um monolito, o sistema sobe e é implantado como uma unidade só.
- A camada de persistência ainda não existe: hoje os dados vivem apenas em memória, o que **ainda não atende ao RNF-003**.

## Estado atual x modelo completo

O diagrama de classes ([`diagrama-classes.png`](../uml/diagrama-classes.png)) mostra o modelo completo. Já implementados: `AlocacaoHorario`, `GradeHoraria`, `GerenciadorGrade`, `Professor`, `Disponibilidade`, `Disciplina`, `Turma`, `Sala`. Pendentes: `Usuario`, `Coordenador` (herança e autenticação, BL-14) e a camada de persistência (BL-17). Hoje `Professor` ainda não herda de `Usuario`.

Decisões relacionadas: [DEC-001](../decisoes/DEC-001-arquitetura.md), [DEC-002](../decisoes/DEC-002-persistencia.md) e [DEC-003](../decisoes/DEC-003-organizacao-das-classes.md).
