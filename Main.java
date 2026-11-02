import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Main {
    private static final int TOTAL_HORARIOS = 10;
    private static final AgendaDAO agendaDAO = new AgendaDAO();

    public static void main(String[] args) {
        String[] opcoes = {
            "Agendar Consulta", "Ver Agenda Diária", "Editar Agendamento",
            "Cancelar Agendamento", "Sair"
        };

        while (true) {
            int opcao = JOptionPane.showOptionDialog(
                null,
                "Selecione uma opção:",
                "Sistema da Clínica Veterinária",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
            );

            try {
                if (opcao == 0) agendar();
                else if (opcao == 1) mostrarAgenda();
                else if (opcao == 2) editar();
                else if (opcao == 3) cancelar();
                else break;
            } catch (ClassNotFoundException erro) {
                JOptionPane.showMessageDialog(
                    null,
                    "Driver JDBC do MySQL não encontrado. Adicione o MySQL Connector/J ao projeto.",
                    "Driver não encontrado",
                    JOptionPane.ERROR_MESSAGE
                );
                break;
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível acessar o banco de dados. Confira se o MySQL está iniciado e "
                    + "se usuário e senha estão corretos.\n\nDetalhe: " + erro.getMessage(),
                    "Erro no banco de dados",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private static void agendar() throws SQLException, ClassNotFoundException {
        Consulta[] agenda = agendaDAO.buscarAgenda();
        boolean lotada = true;
        for (Consulta consulta : agenda) {
            if (consulta == null) {
                lotada = false;
                break;
            }
        }
        if (lotada) {
            JOptionPane.showMessageDialog(null, "A agenda do dia está lotada.", "Agenda lotada",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer indice = lerHorario("Digite o número do horário (1 a 10):");
        if (indice == null) return;
        if (agenda[indice] != null) {
            JOptionPane.showMessageDialog(null, "Esse horário já está ocupado.", "Horário ocupado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String horario = lerTexto("Informe a hora da consulta (ex.: 09:00):");
        if (horario == null) return;

        int tipo = JOptionPane.showOptionDialog(
            null, "Selecione a espécie do paciente:", "Tipo de paciente",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
            new String[] {"Cachorro", "Gato", "Cancelar"}, "Cachorro"
        );
        if (tipo < 0 || tipo > 1) return;

        String nome = lerTexto("Nome do animal:");
        if (nome == null) return;
        Integer idade = lerInteiro("Idade do animal:", 0, 100);
        if (idade == null) return;
        String dono = lerTexto("Nome do tutor:");
        if (dono == null) return;
        String telefone = lerTexto("Telefone do tutor:");
        if (telefone == null) return;
        String caracteristica;
        Animal paciente;

        if (tipo == 0) {
            caracteristica = lerTexto("Porte do cachorro (Pequeno, Médio ou Grande):");
            if (caracteristica == null) return;
            paciente = new Cachorro(caracteristica, nome, idade, dono, telefone);
        } else {
            caracteristica = lerTexto("Temperamento do gato (Dócil ou Arredio):");
            if (caracteristica == null) return;
            paciente = new Gato(caracteristica, nome, idade, dono, telefone);
        }

        String sintomas = lerTexto("Sintomas ou queixa principal:");
        if (sintomas == null) return;

        Consulta consulta = new Consulta(paciente, horario, sintomas);
        if (agendaDAO.salvar(indice, consulta)) {
            JOptionPane.showMessageDialog(null, "Consulta agendada com sucesso.", "Agendamento",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Esse horário já está ocupado.", "Horário ocupado",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void mostrarAgenda() throws SQLException, ClassNotFoundException {
        Consulta[] agenda = agendaDAO.buscarAgenda();
        StringBuilder texto = new StringBuilder("AGENDA DIÁRIA\n\n");
        for (int i = 0; i < TOTAL_HORARIOS; i++) {
            Consulta consulta = agenda[i];
            texto.append("Horário ").append(i + 1).append(": ");
            if (consulta == null) {
                texto.append("[Disponível]\n\n");
            } else {
                Animal paciente = consulta.getPaciente();
                texto.append("[OCUPADO] - ").append(consulta.getHorario()).append('\n')
                    .append("Paciente: ").append(paciente.getNome()).append(" (")
                    .append(paciente instanceof Cachorro ? "Cachorro" : "Gato").append(")\n")
                    .append("Idade: ").append(paciente.getIdade()).append(" anos\n")
                    .append("Tutor: ").append(paciente.getNomeDono()).append(" | Telefone: ")
                    .append(paciente.getTelefoneDono()).append('\n')
                    .append("Som: ").append(paciente.emitirSom()).append('\n')
                    .append("Sintomas: ").append(consulta.getSintomas()).append("\n\n");
            }
        }
        JOptionPane.showMessageDialog(null, texto.toString(), "Agenda diária",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private static void editar() throws SQLException, ClassNotFoundException {
        Consulta[] agenda = agendaDAO.buscarAgenda();
        Integer indice = lerHorario("Digite o número do horário que deseja editar (1 a 10):");
        if (indice == null) return;
        Consulta atual = agenda[indice];
        if (atual == null) {
            JOptionPane.showMessageDialog(null, "Esse horário está vazio.", "Horário disponível",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Animal paciente = atual.getPaciente();
        String nome = lerTexto("Novo nome do animal:");
        if (nome == null) return;
        Integer idade = lerInteiro("Nova idade:", 0, 100);
        if (idade == null) return;
        String dono = lerTexto("Novo nome do tutor:");
        if (dono == null) return;
        String telefone = lerTexto("Novo telefone do tutor:");
        if (telefone == null) return;
        String sintomas = lerTexto("Novos sintomas ou queixa principal:");
        if (sintomas == null) return;

        paciente.setNome(nome);
        paciente.setIdade(idade);
        paciente.setNomeDono(dono);
        paciente.setTelefoneDono(telefone);
        atual.setSintomas(sintomas);
        if (agendaDAO.editar(indice, atual)) {
            JOptionPane.showMessageDialog(null, "Agendamento atualizado com sucesso.", "Agendamento",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void cancelar() throws SQLException, ClassNotFoundException {
        Integer indice = lerHorario("Digite o número do horário que deseja cancelar (1 a 10):");
        if (indice == null) return;
        if (agendaDAO.cancelar(indice)) {
            JOptionPane.showMessageDialog(null, "Consulta cancelada. O horário está disponível.", "Cancelamento",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Esse horário já está vazio.", "Horário disponível",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private static Integer lerHorario(String mensagem) {
        Integer horario = lerInteiro(mensagem, 1, TOTAL_HORARIOS);
        return horario == null ? null : horario - 1;
    }

    private static Integer lerInteiro(String mensagem, int minimo, int maximo) {
        String texto = lerTexto(mensagem);
        if (texto == null) return null;
        try {
            int numero = Integer.parseInt(texto);
            if (numero < minimo || numero > maximo) {
                JOptionPane.showMessageDialog(null, "Digite um número entre " + minimo + " e " + maximo + ".",
                    "Valor inválido", JOptionPane.WARNING_MESSAGE);
                return lerInteiro(mensagem, minimo, maximo);
            }
            return numero;
        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(null, "Digite um número inteiro válido.", "Valor inválido",
                JOptionPane.WARNING_MESSAGE);
            return lerInteiro(mensagem, minimo, maximo);
        }
    }

    private static String lerTexto(String mensagem) {
        String texto = JOptionPane.showInputDialog(null, mensagem);
        if (texto == null) return null;
        texto = texto.trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo não pode ficar vazio.", "Campo obrigatório",
                JOptionPane.WARNING_MESSAGE);
            return lerTexto(mensagem);
        }
        return texto;
    }
}
