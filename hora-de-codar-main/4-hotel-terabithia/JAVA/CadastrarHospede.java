/*
   Em Java, Arrays são tipos de dados não-mutáveis, ou seja, o tamanho do array é fixo e não podemos adicionar diretamente um novo elemento. No entanto, existem técnicas para burlar esta restroção. Vamos supor que temos um array chamado exemplo e precisamos adicionar elementos a ele.

  Podemos usar os seguintes métodos para adicionar elementos ao exemplo:
    1) Ao criar um array de tamanho maior que exemplo. 
    2) Usando ArrayList 
    3) Ao deslocar o elemento para ajustar o tamanho de exemplo.
   
  Neste exemplo vamos utilizar ArrayList que são muito parecidos com os   Arrays, mas possuem mutabilidade.
*/

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;

public class CadastrarHospede {

    // Classe interna para representação do hóspede cadastrado
    public static class Hospede {
        private String nome;
        private LocalDateTime dataHoraCadastro;

        public Hospede(String nome) {
            this.nome = nome;
            this.dataHoraCadastro = LocalDateTime.now();
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public LocalDateTime getDataHoraCadastro() {
            return dataHoraCadastro;
        }

        public String getDataHoraFormatada() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return dataHoraCadastro.format(formatter);
        }
    }

    private static final int CAPACIDADE_MAXIMA = 15;
    private static final List<Hospede> listaHospedes = new ArrayList<>();
    private static boolean dadosCarregados = false;

    // Inicializa dados para testes operacionais
    public static void carregarDadosIniciais() {
        if (!dadosCarregados) {
            String[] nomesIniciais = {
                "Fernando Netto", "Gabriel Augusto Azevedo", "Fernanda Monteiro",
                "Eleanor Neves", "Gabriel Paiva", "Débora Menezes",
                "Michael B Jordan", "Priscila Gabriel", "Noelia Vasquez",
                "Carla Octaviano Azevedo"
            };
            for (String nome : nomesIniciais) {
                listaHospedes.add(new Hospede(nome));
            }
            dadosCarregados = true;
        }
    }

    public static int getQuantidadeHospedes() {
        carregarDadosIniciais();
        return listaHospedes.size();
    }

    // 1. Cadastrar
    private static void cadastrar() {
        if (listaHospedes.size() >= CAPACIDADE_MAXIMA) {
            System.out.println("Máximo de cadastros atingido");
            return;
        }

        String nome = Funções.lerTexto("Nome do hóspede: ");

        for (Hospede h : listaHospedes) {
            if (h.getNome().equalsIgnoreCase(nome)) {
                System.out.println("Hóspede já cadastrado");
                return;
            }
        }

        listaHospedes.add(new Hospede(nome));
        System.out.println("Hóspede cadastrado com sucesso.");
    }

    // 2. Pesquisar por nome exato
    private static void pesquisarExato() {
        String nome = Funções.lerTexto("Nome do hóspede para pesquisa exata: ");
        boolean encontrado = false;

        for (Hospede h : listaHospedes) {
            if (h.getNome().equalsIgnoreCase(nome)) {
                System.out.println("Hóspede " + h.getNome() + " foi encontrado.");
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Hóspede não encontrado");
        }
    }

    // 3. Pesquisar por prefixo
    private static void pesquisarPrefixo() {
        String prefixo = Funções.lerTexto("Prefixo: ");
        System.out.println("Resultados:");
        boolean algum = false;

        for (int i = 0; i < listaHospedes.size(); i++) {
            Hospede h = listaHospedes.get(i);
            if (h.getNome().toLowerCase().startsWith(prefixo.toLowerCase())) {
                System.out.println("[" + (i + 1) + "] " + h.getNome());
                algum = true;
            }
        }

        if (!algum) {
            System.out.println("Nenhum hóspede encontrado com o prefixo informado.");
        }
    }

    // 4. Listar ordenado (A-Z)
    private static void listarOrdenado() {
        if (listaHospedes.isEmpty()) {
            System.out.println("Nenhum hóspede cadastrado no momento.");
            return;
        }

        List<Hospede> copia = new ArrayList<>(listaHospedes);
        copia.sort(Comparator.comparing(Hospede::getNome, String.CASE_INSENSITIVE_ORDER));

        System.out.println("\n--- LISTA DE HÓSPEDES (A-Z) ---");
        for (int i = 0; i < copia.size(); i++) {
            Hospede h = copia.get(i);
            System.out.println("[" + (i + 1) + "] " + h.getNome() + " - Cadastrado em: " + h.getDataHoraFormatada());
        }
    }

    // 5. Atualizar cadastro
    private static void atualizar() {
        listarAtual();
        if (listaHospedes.isEmpty()) return;

        int indice = Funções.lerIntFaixa("Informe o índice do hóspede que deseja atualizar: ", 1, listaHospedes.size()) - 1;
        String novoNome = Funções.lerTexto("Informe o novo nome: ");

        // Verifica duplicidade
        for (int i = 0; i < listaHospedes.size(); i++) {
            if (i != indice && listaHospedes.get(i).getNome().equalsIgnoreCase(novoNome)) {
                System.out.println("Hóspede já cadastrado");
                return;
            }
        }

        listaHospedes.get(indice).setNome(novoNome);
        System.out.println("Operação realizada com sucesso");
    }

    // 6. Remover cadastro
    private static void remover() {
        listarAtual();
        if (listaHospedes.isEmpty()) return;

        int indice = Funções.lerIntFaixa("Informe o índice do hóspede a ser removido: ", 1, listaHospedes.size()) - 1;
        Hospede removido = listaHospedes.remove(indice);
        System.out.println("Hóspede '" + removido.getNome() + "' removido. Operação realizada com sucesso");
    }

    private static void listarAtual() {
        if (listaHospedes.isEmpty()) {
            System.out.println("Nenhum hóspede cadastrado.");
            return;
        }
        System.out.println("\nListagem por índice atual:");
        for (int i = 0; i < listaHospedes.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + listaHospedes.get(i).getNome());
        }
    }

    // Menu do subprograma de Hóspedes
    public static void executarMenu() {
        carregarDadosIniciais();
        boolean emExecucao = true;

        while (emExecucao) {
            Funções.exibirCabecalho("Cadastro de Hóspedes");
            System.out.println("1- Cadastrar");
            System.out.println("2- Pesquisar exato");
            System.out.println("3- Pesquisar prefixo");
            System.out.println("4- Listar ordenado (A-Z)");
            System.out.println("5- Atualizar");
            System.out.println("6- Remover");
            System.out.println("7- Voltar");

            int opcao = Funções.lerInt("Opção: ");

            switch (opcao) {
                case 1: cadastrar(); break;
                case 2: pesquisarExato(); break;
                case 3: pesquisarPrefixo(); break;
                case 4: listarOrdenado(); break;
                case 5: atualizar(); break;
                case 6: remover(); break;
                case 7: emExecucao = false; break;
                default: System.out.println("[ERRO] Opção inválida!"); break;
            }
        }
    }
}