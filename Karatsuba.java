/*
    Autor: @Ruan Necker
    Link do Repositório: 
    Como Usar

    Compilar o Programa:
        Compile o arquivo Java com o seguinte comando:

        javac Karatsuba.java

    Executar o Programa:
        (execute o programa passando dois números binários como argumentos pela linha de comando)

        java Karatsuba 1010 1111


    O programa retornará o resultado da multiplicação binária:

        Resultado da multiplicação: 1001111110100110110
    */

    public class Karatsuba {

    public static void main(String[] args) {
        // Verifica se foram passados dois argumentos
        if (args.length != 2) {
            System.out.println("\nPor favor, forneça dois números binários como argumentos.");
            System.out.println("Exemplo: java Karatsuba 1010 1111\n");
            return;
        }

        String num1 = args[0];
        String num2 = args[1];

        if (!isBinary(num1) || !isBinary(num2)) {
            System.out.println("Os números fornecidos não são binários. Por favor, insira apenas 0 ou 1.");
            return;
        }

        // Multiplicação usando Karatsuba
        String result = karatsubaMultiply(num1, num2);
        result = removeLeadingZeros(result);
        System.out.println("\nResultado da multiplicação: " + result);
    }

    // Função para remover zeros à esquerda
    private static String removeLeadingZeros(String s) {
        int i = 0;
            while (i < s.length() && s.charAt(i) == '0') {
                i++;
            }
        return (i == s.length()) ? "0" : s.substring(i);
    }

    // Função  para verificar se contém apenas 0 e 1
    private static boolean isBinary(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != '0' && c != '1') {
                return false;
            }
        }
        return true;
    }

    // Função principal de multiplicação usando o algoritmo de Karatsuba
    public static String karatsubaMultiply(String x, String y) {
        // Limite para multiplicação direta quando os números forem pequenos
        if (x.length() <= 16 || y.length() <= 16) {
            return multiplyDirectly(x, y);
        }

        // Ajusta as strings para terem o mesmo comprimento
        int maxLength = Math.max(x.length(), y.length());
        x = padLeft(x, maxLength);
        y = padLeft(y, maxLength);

        // Divide as strings no meio (high e low)
        int halfLength = maxLength / 2;
        String xHigh = x.substring(0, halfLength);
        String xLow = x.substring(halfLength);
        String yHigh = y.substring(0, halfLength);
        String yLow = y.substring(halfLength);

        // Recursivamente calcular os três produtos
        String z2 = karatsubaMultiply(xHigh, yHigh); // Produto das partes "high"
        String z0 = karatsubaMultiply(xLow, yLow);   // Produto das partes "low"
        String z1 = karatsubaMultiply(addBinary(xHigh, xLow), addBinary(yHigh, yLow)); // Produto das somas

        // Calcula z1 - z2 - z0
        z1 = subtractBinary(z1, z2);
        z1 = subtractBinary(z1, z0);

        // Combina os resultados: z2 * 2^(2*halfLength) + z1 * 2^halfLength + z0
        return addBinary(addBinary(shiftLeft(z2, 2 * (maxLength - halfLength)), shiftLeft(z1, maxLength - halfLength)), z0);
    }

        // Função de multiplicação bit a bit sem usar tipos numéricos internos
    private static String multiplyDirectly(String x, String y) {
        String result = "0";  // Inicialmente o resultado é 0

        // Percorre cada bit de y, de trás para frente
        for (int i = y.length() - 1; i >= 0; i--) {
            if (y.charAt(i) == '1') {
                // Se o bit de y for 1, adiciona x deslocado para a esquerda pela posição correta
                String shiftedX = shiftLeft(x, y.length() - 1 - i);
                result = addBinary(result, shiftedX);  // Soma o resultado parcial ao resultado total
            }
        }

        return result;
    }

    // Função auxiliar para preencher uma string com zeros à esquerda
    private static String padLeft(String s, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n - s.length(); i++) {
            sb.append('0');
        }
        sb.append(s);
        return sb.toString();
    }

    // Função auxiliar para adicionar dois números binários representados como strings
    private static String addBinary(String a, String b) {
        StringBuilder result = new StringBuilder();
        int carry = 0;

        // Garante que as strings tenham o mesmo comprimento
        int maxLength = Math.max(a.length(), b.length());
        a = padLeft(a, maxLength);
        b = padLeft(b, maxLength);

        // Adiciona bit a bit da direita para a esquerda
        for (int i = maxLength - 1; i >= 0; i--) {
            int bitA = a.charAt(i) - '0';
            int bitB = b.charAt(i) - '0';
            int sum = bitA + bitB + carry;
            carry = sum / 2;
            result.insert(0, sum % 2);
        }

        // Se houver carry restante, adicione no início
        if (carry > 0) {
            result.insert(0, carry);
        }

        return result.toString();
    }

    // Função auxiliar para subtrair dois números binários representados como strings
    private static String subtractBinary(String a, String b) {
        StringBuilder result = new StringBuilder();
        int borrow = 0;

        // Garante que as strings tenham o mesmo comprimento
        int maxLength = Math.max(a.length(), b.length());
        a = padLeft(a, maxLength);
        b = padLeft(b, maxLength);

        // Subtrai bit a bit da direita para a esquerda
        for (int i = maxLength - 1; i >= 0; i--) {
            int bitA = a.charAt(i) - '0';
            int bitB = b.charAt(i) - '0';
            int sub = bitA - bitB - borrow;
            if (sub < 0) {
                sub += 2;
                borrow = 1;
            } else {
                borrow = 0;
            }
            result.insert(0, sub);
        }

        // Remove zeros à esquerda
        while (result.length() > 1 && result.charAt(0) == '0') {
            result.deleteCharAt(0);
        }

        return result.toString();
    }

    // Função auxiliar para deslocar uma string binária à esquerda (equivalente a multiplicar por 2^n)
    private static String shiftLeft(String s, int n) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < n; i++) {
            sb.append('0');
        }
        return sb.toString();
    }
}