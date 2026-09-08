public class Consulta {
    private Animal paciente;
    private String horario;
    private String simtomas;

    public Consulta(Animal paciente, String horario, String simtomas) {
        this.paciente = paciente;
        this.horario = horario;
        this.simtomas = simtomas;
    }

    public Animal getPaciente() {
        return paciente;
    }
    public void setPaciente(Animal paciente) {
        this.paciente = paciente;
    }
    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }
    public String getSintomas() {
        return simtomas;
    }
    public void setSintomas(String sintomas) {
        this.simtomas = sintomas;
    }
}
