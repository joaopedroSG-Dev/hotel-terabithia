class Contato {
    String nome;
    String telefone;
    
    Contato(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }
    
    @Override
    public String toString() {
        return nome + " - " + telefone;
    }
}