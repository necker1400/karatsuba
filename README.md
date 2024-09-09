# Implementação do Algoritmo de Karatsuba - por Ruan Necker

## Descrição

Este programa implementa o Algoritmo de Karatsuba para multiplicação de números inteiros representados como strings binárias. O algoritmo é eficiente para multiplicar números grandes, utilizando a abordagem divide-and-conquer. Todos os cálculos são feitos bit a bit, sem o uso de tipos numéricos nativos para grandes inteiros, respeitando as restrições de usar exclusivamente números binários.
Como Usar

Compilar o Programa:
    Compile o arquivo Java com o seguinte comando:

    javac Karatsuba.java

Executar o Programa:
    (execute o programa passando dois números binários como argumentos pela linha de comando)

    java Karatsuba 1010 1111


O programa retornará o resultado da multiplicação binária:

    Resultado da multiplicação: 1001111110100110110


## Meus Desafios e Decisões Tomadas


## 1. Manter Operações em Binário

O principal desafio foi garantir que todas as operações fossem realizadas diretamente em binário. Isso exigiu a implementação das funções como addBinary e subtractBinary para operações aritméticas bit a bit, sem usar tipos nativos de números inteiros.


## 2. Limitar o Caso Base para Multiplicação Direta

Para pequenos números, a multiplicação direta é mais eficiente do que a recursão do Karatsuba. Decidi usar um limite de 16 bits para a multiplicação direta, embora esse valor possa ser ajustado para melhorar o desempenho dependendo do tamanho típico dos números a serem processados.


## 3. Correção de Subtração

A subtração de números binários apresentou desafios em relação ao gerenciamento de "borrow" (empréstimo) e a garantia de que o resultado fosse sempre não negativo. Decidi incluir uma verificação que impede subtrações que resultariam em números negativos, lançando uma exceção se isso ocorrer.


## 4. Remoção de Zeros à Esquerda

Após cada operação, zeros à esquerda podem se acumular nas strings binárias. Para garantir a precisão do resultado, implementei a função removeLeadingZeros para garantir que o resultado final não tenha zeros extras.