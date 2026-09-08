import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    // Método auxiliar para ler números inteiros com segurança contra letras
    private static int lerInteiroSeguro(Scanner scanner, String mensagem) {
        int numero = 0;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.print(mensagem);
                numero = scanner.nextInt();
                entradaValida = true; // Entrada válida, sai do laço
            } catch (InputMismatchException e) {
                System.out.println("❌ [ERRO] Digite apenas números inteiros! Tente novamente.");
            } finally {
                scanner.nextLine(); // Limpa o buffer do teclado
            }
        }
        return numero;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("\n========== SISTEMA VETERINÁRIO ==========");
            System.out.println("1 - Agendar Consulta");
            System.out.println("2 - Consultar Agenda");
            System.out.println("3 - Editar Agendamento");
            System.out.println("4 - Cancelar Consulta");
            System.out.println("5 - Sair");
            
            // Leitura protegida da opção do menu
            opcao = lerInteiroSeguro(scanner, "Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    System.out.println("\n--- NOVO AGENDAMENTO ---");
                    
                    // Leitura protegida do número do horário (1 a 10)
                    int slot = lerInteiroSeguro(scanner, "Informe o número do horário (1 a 10): ") - 1;

                    System.out.print("Informe a hora da consulta (ex: 09:00): ");
                    String hora = scanner.nextLine();

                    System.out.println("Tipo de Paciente:");
                    System.out.println("1 - Cachorro");
                    System.out.println("2 - Gato");
                    
                    // Leitura protegida do tipo de paciente
                    int tipo = lerInteiroSeguro(scanner, "Escolha o tipo (1 ou 2): ");

                    System.out.print("Nome do animal: ");
                    String nome = scanner.nextLine();

                    // Leitura protegida da idade
                    int idade = lerInteiroSeguro(scanner, "Idade do animal: ");

                    System.out.print("Nome do tutor: ");
                    String dono = scanner.nextLine();

                    System.out.print("Telefone do tutor: ");
                    String tel = scanner.nextLine();

                    System.out.print("Sintomas/Queixa principal: ");
                    String sintomas = scanner.nextLine();

                    Animal paciente = null;

                    if (tipo == 1) {
                        System.out.print("Porte do cachorro (Pequeno/Médio/Grande): ");
                        String porte = scanner.nextLine();
                        paciente = new Cachorro(porte, nome, idade, dono, tel);
                    } else if (tipo == 2) {
                        System.out.print("Temperamento do gato (Dócil/Arredio): ");
                        String temperamento = scanner.nextLine();
                        paciente = new Gato(temperamento, nome, idade, dono, tel);
                    } else {
                        System.out.println("⚠️ Tipo de paciente inválido! Agendamento cancelado.");
                        break;
                    }

                    Consulta novaConsulta = new Consulta(paciente, hora, sintomas);
                    AgendaManager.agendar(slot, novaConsulta);
                    break;

                case 2:
                    AgendaManager.listar();
                    break;

                case 3:
                    System.out.println("\n--- EDITAR AGENDAMENTO ---");
                    
                    // Leitura protegida do horário a ser editado
                    int slotEdit = lerInteiroSeguro(scanner, "Informe o número do horário que deseja editar (1 a 10): ") - 1;

                    System.out.print("Novo nome do animal: ");
                    String nNome = scanner.nextLine();

                    // Leitura protegida da nova idade
                    int nIdade = lerInteiroSeguro(scanner, "Nova idade: ");

                    System.out.print("Novo nome do tutor: ");
                    String nDono = scanner.nextLine();

                    System.out.print("Novo telefone do tutor: ");
                    String nTel = scanner.nextLine();

                    System.out.print("Novos sintomas/queixa: ");
                    String nSintomas = scanner.nextLine();

                    AgendaManager.editar(slotEdit, nNome, nIdade, nDono, nTel, nSintomas);
                    break;

                case 4:
                    System.out.println("\n--- CANCELAR CONSULTA ---");
                    
                    // Leitura protegida do horário a ser cancelado
                    int slotExcluir = lerInteiroSeguro(scanner, "Informe o número do horário que deseja cancelar (1 a 10): ") - 1;

                    AgendaManager.excluir(slotExcluir);
                    break;

                case 5:
                    System.out.println("Encerrando o sistema da clínica. Até logo!");
                    break;

                default:
                    System.out.println("⚠️ Opção inválida! Escolha um número entre 1 e 5.");
            }

        } while (opcao != 5);

        scanner.close();
    }
}