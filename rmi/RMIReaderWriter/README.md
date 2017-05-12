# RMI: Problema do Leitor - Escritor
Com prioridade para leitores.

## Objetivo
Escrever uma aplicação Cliente-Servidor em que distintos clientes solicitam (simultaneamente) ao servidor a leitura ou escrita em diversos arquivos.

## O Problema
Este é um problema clássico de exclusão mútua entre diversos processos acessando um mesmo recurso (denominado _recurso crítico_ ou _região crítica_). Esses diversos processos se dividem em 2 classes:

- Leitores: acessam o recurso mas não o modificam;
- Escritores: acessam o recurso e o modificam.

Exemplo prático: a leitura e escrita em arquivo (utilizado nesta implementação).

Neste exemplo, a solução deve seguir as seguintes 3 regras:

1. Um conjunto de leitores podem acessar o mesmo recurso simultaneamente.
2. Apenas um escritor pode ter acesso ao recurso por vez.
3. Os leitores possuem prioridade sobre os escritores, isto é, enquanto houver leitor aguardando para usar o recurso os escritores devem ser mantidos em espera.

Nota-se que esta versão do problema permite o estado de _Starvation_ dos escritores. Por outro lado, é uma versão mais simples de implementar.

## A Arquitetura da Solução
Ao iniciar o projeto deparamos com algumas questões que dizem respeito à organização do nosso código. A primeira tentação foi a seguinte estrutura:
- Cliente faz requisição ao servidor de leitura ou escrita;
- Servidor inicia uma thread de leitura ou escrita que, então, definirá o acesso ao arquivo certo.

Essa arquitetura funciona porém possui um problema de acoplamento, isto é, o servidor está acoplado à aplicação. Problemas de acoplamento causam várias dependências por não ser flexível. Por exemplo, sempre teríamos que testar a aplicação através do servidor; qualquer modificação na aplicação teríamos que modificar o servidor, sua interface, etc. Tudo isto causaria um declínio na produtividade. Então arquitetamos o seguinte:

- Cliente faz requisição de leitura ou escrita ao servidor;
- Servidor delega a requisição e seu tipo a um Controlador;
- O Controlador, recebendo o tipo, inicia a o tipo correto de Thread;
- Quando a Thread finalizar ele responderá ao Servidor o resultado (se houver);
- O Servidor responde o resultado para o cliente.

O mesmo problema encontramos em relação à definição de acesso ao arquivo e o arquivo de fato. Então definimos um _DataManager_ que irá gerenciar o acesso ao arquivo e um _Resource_ que terá os métodos de acesso ao arquivo (abrir, fechar, ler e escrever).

Com as funções bem definidas podemos explicar cada uma delas em separado.

## Protocolo Cliente-Servidor
Os clientes possui um tipo de _Job_ que será Leitura ou Escrita. Terá também uma referência de arquivo e, caso seja escritor, a mensagem a ser escrita (nesta implementação decidimos trabalhar apenas com inteiros para simplificação).

Então ele chama o método referente ao seu _Job_ passando os argumentos coerentes (leitura => o arquivo; escrita => o arquivo e a mensagem a ser escrita). A resposta do servidor, nesse caso, só é recebida pelo leitor.

O Servidor, por sua vez, apenas delega a requisição à aplicação e propaga a resposta.

## A Aplicação
#### O Controlador
