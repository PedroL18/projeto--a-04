# DEC-001 — Arquitetura em camadas

## Problema
Era preciso definir como organizar o sistema de forma que as regras de conflito de horário fossem fáceis de testar e que a equipe de três integrantes conseguisse trabalhar em paralelo.

#zAlternativas
MVC simples.
Arquitetura em camadas (Interface, Serviços, Domínio, Persistência).
Arquitetura hexagonal.
Microsserviços.

## Decisão
Arquitetura em camadas, em um único aplicativo Java.

## Justificativa
As regras de negócio são o centro do problema e precisam ser testadas sem interface nem banco. As camadas isolam essas regras no domínio.

## Consequências
- Cada camada depende apenas da inferior; o domínio não depende de nenhuma.
- Mais classes e pacotes que uma solução mínima.
- Exige disciplina para não deixar regras de domínio escaparem para a camada de serviços.
- O sistema é implantado como uma única unidade.
