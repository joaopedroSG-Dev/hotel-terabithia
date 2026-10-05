import java.util.ArrayList;
import java.util.Scanner;

public class AgendaContatos {
    public static void main(String[] args) {
        
        ArrayList<Contato> contatos = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== AGENDA DE CONTATOS ===");
        System.out.println("Comandos: ADICIONAR, BUSCAR, REMOVER, LISTAR, SAIR");
        
        while (true) {
            System.out.print("\nDigite um comando: ");
            String comando = scanner.nextLine().toUpperCase();
            
            switch (comando) {
                case "ADICIONAR":
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    contatos.add(new Contato(nome, telefone));
                    System.out.println("Contato adicionado!");
                    break;
                    
                case "BUSCAR":
                    System.out.print("Nome: ");
                    String nomeBusca = scanner.nextLine();
                    Contato encontrado = null;
                    for (Contato c : contatos) {
                        if (c.nome.equalsIgnoreCase(nomeBusca)) {
                            encontrado = c;
                            break;
                        }
                    }
                    if (encontrado != null) {
                        System.out.println("Telefone: " + encontrado.telefone);
                    } else {
                        System.out.println("Contato não encontrado");
                    }
                    break;
                    
                case "REMOVER":
                    System.out.print("Nome: ");
                    String nomeRemover = scanner.nextLine();
                    boolean removido = false;
                    for (int i = 0; i < contatos.size(); i++) {
                        if (contatos.get(i).nome.equalsIgnoreCase(nomeRemover)) {
                            contatos.remove(i);
                            removido = true;
                            System.out.println("Contato removido!");
                            break;
                        }
                    }
                    if (!removido) {
                        System.out.println("Contato não encontrado");
                    }
                    break;
                    
                case "LISTAR":
                    if (contatos.isEmpty()) {
                        System.out.println("Nenhum contato cadastrado.");
                    } else {
                        System.out.println("=== LISTA DE CONTATOS ===");
                        for (Contato c : contatos) {
                            System.out.println(c);
                        }
                    }
                    break;
                    
                case "SAIR":
                    System.out.println("Agenda encerrada. Total de contatos: " + contatos.size());
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Comando inválido! Use: ADICIONAR, BUSCAR, REMOVER, LISTAR ou SAIR");
            }
        }
    }
}