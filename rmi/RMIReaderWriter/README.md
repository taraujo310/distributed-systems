# RMI: Problema do Leitor - Escritor
Com prioridade para leitores.

Objetivo
---------
Escrever uma aplicação Cliente-Servidor em que distintos clientes solicitam (simultaneamente) ao servidor a leitura ou escrita em diversos arquivos.

O Problema
-----------
Este é um problema clássico de exclusão mútua entre diversos processos acessando um mesmo recurso (denominado _recurso crítico_ ou _região crítica_). Esses diversos processos se dividem em 2 classes:

- Leitores: acessam o recurso mas não o modificam;
- Escritores: acessam o recurso e o modificam.

Exemplo prático: a leitura e escrita em arquivo (utilizado nesta implementação).

Neste exemplo, a solução deve seguir as seguintes 3 regras:

1. Um conjunto de leitores podem acessar o mesmo recurso simultaneamente.
2. Apenas um escritor pode ter acesso ao recurso por vez.
3. Os leitores possuem prioridade sobre os escritores, isto é, enquanto houver leitor aguardando para usar o recurso os escritores devem ser mantidos em espera.

Nota-se que esta versão do problema permite o estado de _Starvation_ dos escritores. Por outro lado, é uma versão mais simples de implementar.

A Arquitetura da Solução
------------------------
Ao iniciar o projeto deparamos com algumas questões que dizem respeito à organização do nosso código. A primeira tentação foi a seguinte estrutura:
