public class Cachorro extends Animal {
    private String porte;

    public Cachorro(String porte, String nome, int idade, String nomeDono, String telefoneDono) {
        super(nome, idade, nomeDono, telefoneDono);
        this.porte = porte;
    }
    @Override
    public String emitirSom() {
        return "Au Au!";
    }

    public String getPorte() {
        return porte;
    }
    public void setPorte(String porte)
    {
        this.porte = porte;
    }
}
