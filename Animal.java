public abstract class Animal {
    protected String nome;
    protected int idade;
    protected String nomeDono;
    protected String telefoneDono;

    public Animal(String nome, int idade, String nomeDono, String telefoneDono) {
        this.nome = nome;
        this.idade = idade;
        this.nomeDono = nomeDono;
        this.telefoneDono = telefoneDono;
    }

    public abstract String emitirSom();

    public String getNome()
    {
        return nome;
    }
    public void setNome(String nome)
    {
        this.nome =nome;
    }

    public int getIdade()
    {
        return idade;
    }
    public void setIdade(int idade)
    {
        this.idade = idade;
    }

    public String getNomeDono()
    {
        return nomeDono;
    }
    public void setNomeDono(String nomeDono)
    {
        this.nomeDono = nomeDono;
    }

    public String getTelefoneDono()
    {
        return telefoneDono;
    }
    public void setTelefoneDono(String telefoneDono)
    {
        this.telefoneDono = telefoneDono;
    }
}

