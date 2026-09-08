public class AgendaManager {
    private static Consulta[] agenda = new Consulta[10];

    public static void listar()
    {
        boolean agendaVazia = true;
        System.out.println("\n--- Agenda de Consultas ---");
        for (int i = 0; i <agenda.length; i++)
        {
            int numeroHorario = i + 1;
            if (agenda[i] == null)
            {
                System.out.println("Horario " + numeroHorario + ": [Disponível]");
            }
            else
            {
                agendaVazia = false;
                Consulta consulta = agenda[i];
                Animal paciente = consulta.getPaciente();
                System.out.println("Horário " + numeroHorario + ": [OCUPADO]");
                System.out.println("  Horário: " + consulta.getHorario());
                System.out.println("  Paciente: " + paciente.getNome() + " (" + paciente.getClass().getSimpleName() + ")");
                System.out.println("  Idade: " + paciente.getIdade() + " anos");
                System.out.println("  Tutor: " + paciente.getNomeDono() + " | Tel: " + paciente.getTelefoneDono());
                System.out.println("  Sintomas: " + consulta.getSintomas());
                System.out.println("Som do Animal: " + paciente.emitirSom());
                System.out.println("----------------------------------------");
            }        }
        if (agendaVazia)
        {
            System.out.println("\n[AVISO] Nenhum atendimento foi agendado para o dia.");
        }
    }

    public static boolean agendar (int indice, Consulta novConsulta)
    {
        if (indice <0 || indice >= 10)
        {
            System.out.println("Erro: Horário inválido! Escolha um horário entre 1 e 10.");
            return false;
        }

        if (agenda[indice] != null)
        {
            System.out.println(" O horario "+ (indice + 1) + "está ocupado" );
        }
        agenda[indice] = novConsulta;
        System.out.println("Agendamento realizado com sucesso para o horário " + (indice + 1) + "!");
        return true; 
    }

    public static void excluir(int indice)
    {
        if (indice <0 || indice >= 10)
        {
            System.out.println("Erro: Horário inválido! Escolha um horário entre 1 e 10.");
            return;
        }
        if (agenda[indice] == null)
        {
            System.out.println("Este horario está vazio." );
            return;
        }
        agenda[indice] = null;
        System.out.println("Este horario foi excluido da agenda com sucesso.");
    }

    public static void editar (int indice, String novoNome, int novaIdade, String novoDono, String novoTel, String novosSintomas )
    {
        if (indice <0 || indice >= 10)
        {
            System.out.println("Erro: Horário inválido! Escolha um horário entre 1 e 10.");
            return;
        }
        if (agenda[indice] == null)
        {
            System.out.println("Este horario está vazio." );
            return;
        }
        Consulta consulta = agenda[indice];
        Animal paciente = consulta.getPaciente();
        paciente.setNome(novoNome);
        paciente.setIdade(novaIdade);
        paciente.setNomeDono(novoDono);
        paciente.setTelefoneDono(novoTel);
        consulta.setSintomas(novosSintomas);
        System.out.println("Consulta atualizada com sucesso");
    }
}
