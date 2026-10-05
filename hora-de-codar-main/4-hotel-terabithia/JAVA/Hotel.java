import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private static final String NOME_HOTEL = "Continental";
    private static final String SENHA_CORRETA = "2678";
    private static final int MAX_TENTATIVAS = 3;

    // Estruturas do hotel mantidas em memória
    private static final String[] quartos = new String[20]; // null = Livre, String = Nome do Hospede
    private static int totalReservasConfirmadas = 0;
    private static double receitaHospedagem = 0.0;

    private static int totalEventosConfirmados = 0;
    private static double receitaEventos = 0.0;

    // Subprograma 1: Reservas de Quartos
    private static void subprogramaReservas(String usuarioSessao) {
        Funções.exibirCabecalho("Reservas de Quartos");

        double diaria = Funções.lerDoublePositivo("Informe o valor da diária: ");
        if (diaria <= 0) {
            System.out.println("Valor inválido, " + usuarioSessao);
            return;
        }

        int dias = Funções.lerInt("Informe a quantidade de diárias (1-30): ");
        if (dias < 1 || dias > 30) {
            System.out.println("Valor inválido, " + usuarioSessao);
            return;
        }

        String nomeHospede = Funções.lerTexto("Informe o nome do hóspede: ");

        String tipoQuarto = "";
        double fator = 1.00;
        String descTipo = "";

        while (true) {
            tipoQuarto = Funções.lerTexto("Tipo de quarto (S - Standard / E - Executivo / L - Luxo): ").toUpperCase();
            if (tipoQuarto.equals("S")) {
                fator = 1.00;
                descTipo = "Standard";
                break;
            } else if (tipoQuarto.equals("E")) {
                fator = 1.35;
                descTipo = "Executivo";
                break;
            } else if (tipoQuarto.equals("L")) {
                fator = 1.65;
                descTipo = "Luxo";
                break;
            }
            System.out.println("[ERRO] Tipo inválido! Informe S, E ou L.");
        }

        int numQuarto = -1;
        while (true) {
            numQuarto = Funções.lerIntFaixa("Escolha um quarto (1-20): ", 1, 20);
            if (quartos[numQuarto - 1] == null) {
                break;
            } else {
                System.out.println("Quarto já está ocupado!");
                exibirMapaQuartos();
            }
        }

        double subtotal = diaria * dias * fator;
        double taxaServico = subtotal * 0.10;
        double totalFinal = subtotal + taxaServico;

        System.out.println("\nResumo:");
        System.out.println("Hóspede: " + nomeHospede);
        System.out.println("Quarto: " + numQuarto + " (" + descTipo + ")");
        System.out.println("Subtotal: " + Funções.formatarMoeda(subtotal));
        System.out.println("Taxa de serviço (10%): " + Funções.formatarMoeda(taxaServico));
        System.out.println("Total: " + Funções.formatarMoeda(totalFinal));

        if (Funções.confirmar("\n" + usuarioSessao + ", confirma a reserva? (S/N): ")) {
            quartos[numQuarto - 1] = nomeHospede;
            totalReservasConfirmadas++;
            receitaHospedagem += totalFinal;
            System.out.println("Reserva efetuada com sucesso.");
            exibirMapaQuartos();
        } else {
            System.out.println("Reserva não efetuada.");
        }
    }

    // Exibe mapa de quartos em matriz 4x5
    private static void exibirMapaQuartos() {
        System.out.println("\n--- MAPA DE QUARTOS ---");
        for (int i = 0; i < 20; i++) {
            String status = (quartos[i] == null) ? "L" : "O";
            System.out.printf("[%02d: %s]  ", (i + 1), status);
            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
    }

    // Subprograma 3: Eventos
    private static void subprogramaEventos() {
        Funções.exibirCabecalho("Agendamento de Eventos");

        int convidados = Funções.lerInt("Convidados: ");
        if (convidados < 0 || convidados > 350) {
            System.out.println("Número de convidados inválido");
            return;
        }

        String auditorio = "";
        int cadeirasExtras = 0;

        if (convidados <= 220) {
    auditorio = "Laranja";
    if (convidados > 150) {
        cadeirasExtras = convidados - 150;
    }
} else {
    auditorio = "Colorado";
}

// Imprime usando a variável auditorio
System.out.println("Auditório selecionado: " + auditorio + (cadeirasExtras > 0 ? " (inclui " + cadeirasExtras + " cadeiras adicionais)" : ""));
        String dia = Funções.lerTexto("Dia (ex: segunda, sabado): ").toLowerCase().trim();
        int horaInicio = Funções.lerInt("Hora inicial (0-23): ");
        int duracao = Funções.lerIntFaixa("Duração em horas (1-12): ", 1, 12);

        // Validação da Janela de Funcionamento
        boolean ehFimDeSemana = dia.startsWith("sab") || dia.startsWith("sáb") || dia.startsWith("dom");
        int horaLimite = ehFimDeSemana ? 15 : 23;

        if (horaInicio < 7 || (horaInicio + duracao) > horaLimite) {
            System.out.println("Auditório indisponível para o horário/dia informado.");
            return;
        }

       String empresa = Funções.lerTexto("Empresa: ");
System.out.println("Status: Auditório reservado para " + empresa + ".");

        // Cálculos de Garçons
        int garconsBase = (int) Math.ceil((double) convidados / 12.0);
        int garconsReforco = (int) Math.floor((double) duracao / 2.0);
        int totalGarcons = garconsBase + garconsReforco;
        double custoGarcons = totalGarcons * duracao * 10.50;

        // Cálculos do Buffet
        double cafeL = convidados * 0.2;
        double aguaL = convidados * 0.5;
        int salgadosUn = convidados * 7;

        double custoCafe = cafeL * 0.80;
        double custoAgua = aguaL * 0.40;
        double custoSalgados = (salgadosUn / 100.0) * 34.00;
        double custoBuffet = custoCafe + custoAgua + custoSalgados;

        double totalEvento = custoGarcons + custoBuffet;

        System.out.println("\nGarçons necessários: " + totalGarcons);
        System.out.println("Custo com garçons: " + Funções.formatarMoeda(custoGarcons));
        System.out.println("\nBuffet:");
        System.out.printf("Café: %.1f L\n", cafeL);
        System.out.printf("Água: %.1f L\n", aguaL);
        System.out.println("Salgados: " + salgadosUn + " un");
        System.out.println("Custo buffet: " + Funções.formatarMoeda(custoBuffet));
        System.out.println("\nTotal do evento: " + Funções.formatarMoeda(totalEvento));

        if (Funções.confirmar("Confirmar reserva? (S/N): ")) {
            totalEventosConfirmados++;
            receitaEventos += totalEvento;
            System.out.println("Reserva efetuada com sucesso.");
        } else {
            System.out.println("Reserva não efetuada.");
        }
    }

    // Subprograma 4: Ar-Condicionado
    private static void subprogramaArCondicionado(String usuarioSessao) {
        Funções.exibirCabecalho("Orçamentos de Ar-Condicionado");

        List<String> empresas = new ArrayList<>();
        List<Double> totais = new ArrayList<>();

        while (true) {
            String nomeEmpresa = Funções.lerTexto("Empresa: ");
            double valorAparelho = Funções.lerDoublePositivo("Valor por aparelho: ");
            int qtdAparelhos = Funções.lerInt("Quantidade: ");
            double pctDesconto = Funções.lerDoublePositivo("Desconto (%): ");
            int minDesconto = Funções.lerInt("Mínimo para desconto: ");
            double deslocamento = Funções.lerDoublePositivo("Deslocamento: ");

            double bruto = valorAparelho * qtdAparelhos;
            double desconto = 0.0;
            if (qtdAparelhos >= minDesconto) {
                desconto = bruto * (pctDesconto / 100.0);
            }
            double total = bruto - desconto + deslocamento;

            empresas.add(nomeEmpresa);
            totais.add(total);

            System.out.println("O serviço de " + nomeEmpresa + " custará " + Funções.formatarMoeda(total));

            if (!Funções.confirmar("\nDeseja informar novos dados, " + usuarioSessao + "? (S/N): ")) {
                break;
            }
        }

        if (empresas.size() < 2) {
            System.out.println("[AVISO] Para comparação completa são necessárias pelo menos 2 empresas.");
        }

        // Análise e Destaques
        int idxMelhor = 0;
        int idxPior = 0;
        for (int i = 1; i < totais.size(); i++) {
            if (totais.get(i) < totais.get(idxMelhor)) idxMelhor = i;
            if (totais.get(i) > totais.get(idxPior)) idxPior = i;
        }

        System.out.println("\nMelhor orçamento: " + empresas.get(idxMelhor) + " — " + Funções.formatarMoeda(totais.get(idxMelhor)));
        if (empresas.size() >= 2) {
            double difPct = ((totais.get(idxPior) - totais.get(idxMelhor)) / totais.get(idxMelhor)) * 100.0;
            System.out.printf("Maior orçamento: %s — %s (Diferença de %.2f%% em relação ao menor)\n",
                    empresas.get(idxPior), Funções.formatarMoeda(totais.get(idxPior)), difPct);
        }
    }

    // Subprograma 5: Abastecimento
    private static void subprogramaAbastecimento(String usuarioSessao) {
        Funções.exibirCabecalho("Análise Econômica de Abastecimento");

        System.out.println("Posto Wayne Oil:");
        double alcWayne = Funções.lerDoublePositivo("Preço Álcool: ");
        double gasWayne = Funções.lerDoublePositivo("Preço Gasolina: ");

        System.out.println("\nPosto Stark Petrol:");
        double alcStark = Funções.lerDoublePositivo("Preço Álcool: ");
        double gasStark = Funções.lerDoublePositivo("Preço Gasolina: ");

        // Regra do Etanol (30% mais barato = até 70% do preço da gasolina)
        String opcWayne = (alcWayne <= gasWayne * 0.70) ? "Álcool" : "Gasolina";
        double totalWayne = 42 * (opcWayne.equals("Álcool") ? alcWayne : gasWayne);

        String opcStark = (alcStark <= gasStark * 0.70) ? "Álcool" : "Gasolina";
        double totalStark = 42 * (opcStark.equals("Álcool") ? alcStark : gasStark);

        System.out.println("\nWayne Oil: melhor opção = " + opcWayne + " | Total (42L) = " + Funções.formatarMoeda(totalWayne));
        System.out.println("Stark Petrol: melhor opção = " + opcStark + " | Total (42L) = " + Funções.formatarMoeda(totalStark));

        if (totalWayne < totalStark) {
            System.out.println("\n" + usuarioSessao + ", é mais barato abastecer com " + opcWayne.toLowerCase() + " no posto Wayne Oil.");
        } else if (totalStark < totalWayne) {
            System.out.println("\n" + usuarioSessao + ", é mais barato abastecer com " + opcStark.toLowerCase() + " no posto Stark Petrol.");
        } else {
            System.out.println("\n" + usuarioSessao + ", ambos os postos apresentam exatamente o mesmo custo total.");
        }
    }

    // Subprograma 6: Relatórios Operacionais
    private static void subprogramaRelatorios() {
        Funções.exibirCabecalho("Relatórios Operacionais Consolidados");

        int ocupados = 0;
        for (String q : quartos) {
            if (q != null) ocupados++;
        }
        double taxaOcupacao = (ocupados / 20.0) * 100.0;
        int totalHospedes = CadastrarHospede.getQuantidadeHospedes();
        double receitaTotal = receitaHospedagem + receitaEventos;

        System.out.println("+---------------------------------------+--------------------+");
        System.out.println("| Métrica Operacional                   | Valor Consolidado  |");
        System.out.println("+---------------------------------------+--------------------+");
        System.out.printf("| Reservas de Quartos Confirmadas       | %-18d |\n", totalReservasConfirmadas);
        System.out.printf("| Quartos Ocupados / Taxa de Ocupação   | %d/20 (%.1f%%)     |\n", ocupados, taxaOcupacao);
        System.out.printf("| Hóspedes Cadastrados na Memória       | %-18d |\n", totalHospedes);
        System.out.printf("| Eventos Confirmados                   | %-18d |\n", totalEventosConfirmados);
        System.out.printf("| Receita de Hospedagem                 | %-18s |\n", Funções.formatarMoeda(receitaHospedagem));
        System.out.printf("| Receita de Eventos                    | %-18s |\n", Funções.formatarMoeda(receitaEventos));
        System.out.println("+---------------------------------------+--------------------+");
        System.out.printf("| RECEITA TOTAL ACUMULADA               | %-18s |\n", Funções.formatarMoeda(receitaTotal));
        System.out.println("+---------------------------------------+--------------------+");
    }

    private static void tratarOpcaoInvalida() {
        System.out.println("\n[ERRO] Opção inválida! Selecione um número entre 1 e 7.");
    }

    private static String autenticarUsuario() {
        System.out.println("Bem-vindo ao " + NOME_HOTEL + "\n");
        String nomeUsuario = Funções.lerTexto("Informe o seu nome: ");
        int tentativas = 0;

        while (tentativas < MAX_TENTATIVAS) {
            String senha = Funções.lerTexto("Informe a senha de acesso: ");

            if (senha.equals(SENHA_CORRETA)) {
                System.out.println("\nBem-vindo ao Hotel " + NOME_HOTEL + ", " + nomeUsuario + ". É um imenso prazer ter você por aqui!");
                return nomeUsuario;
            }

            tentativas++;
            int restantes = MAX_TENTATIVAS - tentativas;

            if (restantes > 0) {
                System.out.println("[ERRO] Senha incorreta! Tentativas restantes: " + restantes + "\n");
            }
        }

        System.out.println("\n[SISTEMA BLOQUEADO] Número máximo de tentativas excedido. O sistema será encerrado.");
        return null;
    }

    public static void main(String[] args) {
        String usuarioAutenticado = autenticarUsuario();

        if (usuarioAutenticado == null) {
            return;
        }

        boolean sistemaAtivo = true;

        while (sistemaAtivo) {
            Funções.exibirCabecalho("Menu Principal - Hotel " + NOME_HOTEL);
            System.out.println("1. Reservas de Quartos");
            System.out.println("2. Cadastro de Hóspedes");
            System.out.println("3. Eventos");
            System.out.println("4. Ar-Condicionado");
            System.out.println("5. Abastecimento");
            System.out.println("6. Relatórios Operacionais");
            System.out.println("7. Sair");

            int opcao = Funções.lerInt("Opção: ");

            switch (opcao) {
                case 1: subprogramaReservas(usuarioAutenticado); break;
                case 2: CadastrarHospede.executarMenu(); break;
                case 3: subprogramaEventos(); break;
                case 4: subprogramaArCondicionado(usuarioAutenticado); break;
                case 5: subprogramaAbastecimento(usuarioAutenticado); break;
                case 6: subprogramaRelatorios(); break;
                case 7:
                    System.out.println("\nMuito obrigado e até logo, " + usuarioAutenticado + ".");
                    sistemaAtivo = false;
                    break;
                default:
                    tratarOpcaoInvalida();
                    break;
            }
        }
    }
}