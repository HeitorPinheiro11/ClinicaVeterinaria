public class Gato extends Animal {
    private String temperamento;

    public Gato(String temperamento, String nome, int idade, String nomeDono, String telefoneDono) {
        super(nome, idade, nomeDono, telefoneDono);
        this.temperamento = temperamento;
    }
    @Override
    public String emitirSom() {
        return "Miau!";
    }

    public String getTemperamento() {
        return temperamento;
    }
    public void setTemperamento(String temperamento)
    {
        this.temperamento = temperamento;
    }
}
