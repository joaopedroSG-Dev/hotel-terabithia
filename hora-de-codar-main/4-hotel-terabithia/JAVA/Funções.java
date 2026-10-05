import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Funções {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Locale LOCALE_BR = new Locale("pt", "BR");
    private static final NumberFormat FORMATO_MOEDA = NumberFormat.getCurrencyInstance(LOCALE_BR);

    // Formata valores double para moeda brasileira (R$)
    public static String formatarMoeda(double valor) {
        return FORMATO_MOEDA.format(valor);
    }

    // Leitura validada de inteiro simples
    public static int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Entrada inválida! Digite apenas um número inteiro.");
            }
        }
    }

    // Leitura validada de inteiro com faixa de valores (mínimo e máximo)
    public static int lerIntFaixa(String mensagem, int min, int max) {
        while (true) {
            int valor = lerInt(mensagem);
            if (valor >= min && valor <= max) {
                return valor;
            }
            System.out.println("[ERRO] Por favor, informe um valor entre " + min + " e " + max + ".");
        }
    }

    // Leitura validada de double (valor monetário/decimal)
    public static double lerDoublePositivo(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                double valor = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
                if (valor >= 0) {
                    return valor;
                }
                System.out.println("[ERRO] O valor não pode ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Entrada inválida! Digite um número decimal válido.");
            }
        }
    }

    // Leitura de texto não vazio
    public static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        String entrada = scanner.nextLine().trim();
        while (entrada.isEmpty()) {
            System.out.print("[ERRO] O campo não pode ser vazio. Digite novamente: ");
            entrada = scanner.nextLine().trim();
        }
        return entrada;
    }

    // Validação de confirmação Sim/Não (retorna true para S)
    public static boolean confirmar(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            if (entrada.equalsIgnoreCase("S")) {
                return true;
            } else if (entrada.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("[ERRO] Opção inválida! Digite 'S' para Sim ou 'N' para Não.");
        }
    }

    // Cabeçalho padronizado
    public static void exibirCabecalho(String titulo) {
        System.out.println("\n==================================================");
        System.out.println("   " + titulo.toUpperCase());
        System.out.println("==================================================");
    }
}