data class Contato(val nome: String, val telefone: String)

fun main() {
    val contatos = mutableListOf<Contato>()
    
    println("=== AGENDA DE CONTATOS ===")
    println("Comandos: ADICIONAR, BUSCAR, REMOVER, LISTAR, SAIR")
    
    while (true) {
        print("\nDigite um comando: ")
        val comando = readLine()?.uppercase() ?: ""
        
        when (comando) {
            "ADICIONAR" -> {
                print("Nome: ")
                val nome = readLine() ?: ""
                print("Telefone: ")
                val telefone = readLine() ?: ""
                contatos.add(Contato(nome, telefone))
                println("Contato adicionado!")
            }
            
            "BUSCAR" -> {
                print("Nome: ")
                val nomeBusca = readLine() ?: ""
                val encontrado = contatos.find { it.nome.equals(nomeBusca, ignoreCase = true) }
                if (encontrado != null) {
                    println("Telefone: ${encontrado.telefone}")
                } else {
                    println("Contato não encontrado")
                }
            }
            
            "REMOVER" -> {
                print("Nome: ")
                val nomeRemover = readLine() ?: ""
                val removido = contatos.removeIf { it.nome.equals(nomeRemover, ignoreCase = true) }
                if (removido) {
                    println("Contato removido!")
                } else {
                    println("Contato não encontrado")
                }
            }
            
            "LISTAR" -> {
                if (contatos.isEmpty()) {
                    println("Nenhum contato cadastrado.")
                } else {
                    println("=== LISTA DE CONTATOS ===")
                    contatos.forEach { println("${it.nome} - ${it.telefone}") }
                }
            }
            
            "SAIR" -> {
                println("Agenda encerrada. Total de contatos: ${contatos.size}")
                return
            }
            
            else -> println("Comando inválido! Use: ADICIONAR, BUSCAR, REMOVER, LISTAR ou SAIR")
        }
    }
}