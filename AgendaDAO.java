import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AgendaDAO {
    private static final int TOTAL_HORARIOS = 10;

    private Connection conectarComTabela() throws SQLException, ClassNotFoundException {
        Connection conexao = ConexaoBD.conectar();
        try (Statement comando = conexao.createStatement()) {
            comando.executeUpdate(
                "CREATE TABLE IF NOT EXISTS consultas ("
                + "horario_index INT PRIMARY KEY, "
                + "horario VARCHAR(10) NOT NULL, "
                + "tipo VARCHAR(20) NOT NULL, "
                + "nome_animal VARCHAR(100) NOT NULL, "
                + "idade INT NOT NULL, "
                + "nome_dono VARCHAR(100) NOT NULL, "
                + "telefone_dono VARCHAR(30) NOT NULL, "
                + "caracteristica VARCHAR(50) NOT NULL, "
                + "sintomas VARCHAR(500) NOT NULL)"
            );
        } catch (SQLException erro) {
            conexao.close();
            throw erro;
        }
        return conexao;
    }

    public Consulta[] buscarAgenda() throws SQLException, ClassNotFoundException {
        Consulta[] agenda = new Consulta[TOTAL_HORARIOS];
        String sql = "SELECT horario_index, horario, tipo, nome_animal, idade, "
                   + "nome_dono, telefone_dono, caracteristica, sintomas "
                   + "FROM consultas ORDER BY horario_index";

        try (Connection conexao = conectarComTabela();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
            while (resultado.next()) {
                int indice = resultado.getInt("horario_index");
                if (indice >= 0 && indice < TOTAL_HORARIOS) {
                    agenda[indice] = criarConsulta(resultado);
                }
            }
        }
        return agenda;
    }

    public boolean salvar(int indice, Consulta consulta) throws SQLException, ClassNotFoundException {
        if (!indiceValido(indice)) return false;
        String sql = "INSERT INTO consultas (horario_index, horario, tipo, nome_animal, idade, "
                   + "nome_dono, telefone_dono, caracteristica, sintomas) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = conectarComTabela()) {
            if (horarioOcupado(conexao, indice)) return false;
            try (PreparedStatement comando = conexao.prepareStatement(sql)) {
                preencherDados(comando, indice, consulta);
                comando.executeUpdate();
                return true;
            }
        }
    }

    public boolean editar(int indice, Consulta consulta) throws SQLException, ClassNotFoundException {
        if (!indiceValido(indice)) return false;
        String sql = "UPDATE consultas SET horario=?, tipo=?, nome_animal=?, idade=?, nome_dono=?, "
                   + "telefone_dono=?, caracteristica=?, sintomas=? WHERE horario_index=?";

        try (Connection conexao = conectarComTabela();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            Animal animal = consulta.getPaciente();
            comando.setString(1, consulta.getHorario());
            comando.setString(2, tipoAnimal(animal));
            comando.setString(3, animal.getNome());
            comando.setInt(4, animal.getIdade());
            comando.setString(5, animal.getNomeDono());
            comando.setString(6, animal.getTelefoneDono());
            comando.setString(7, caracteristica(animal));
            comando.setString(8, consulta.getSintomas());
            comando.setInt(9, indice);
            return comando.executeUpdate() > 0;
        }
    }

    public boolean cancelar(int indice) throws SQLException, ClassNotFoundException {
        if (!indiceValido(indice)) return false;
        String sql = "DELETE FROM consultas WHERE horario_index=?";
        try (Connection conexao = conectarComTabela();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, indice);
            return comando.executeUpdate() > 0;
        }
    }

    private boolean horarioOcupado(Connection conexao, int indice) throws SQLException {
        String sql = "SELECT horario_index FROM consultas WHERE horario_index=?";
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, indice);
            try (ResultSet resultado = comando.executeQuery()) {
                return resultado.next();
            }
        }
    }

    private void preencherDados(PreparedStatement comando, int indice, Consulta consulta) throws SQLException {
        Animal animal = consulta.getPaciente();
        comando.setInt(1, indice);
        comando.setString(2, consulta.getHorario());
        comando.setString(3, tipoAnimal(animal));
        comando.setString(4, animal.getNome());
        comando.setInt(5, animal.getIdade());
        comando.setString(6, animal.getNomeDono());
        comando.setString(7, animal.getTelefoneDono());
        comando.setString(8, caracteristica(animal));
        comando.setString(9, consulta.getSintomas());
    }

    private Consulta criarConsulta(ResultSet resultado) throws SQLException {
        String tipo = resultado.getString("tipo");
        String nome = resultado.getString("nome_animal");
        int idade = resultado.getInt("idade");
        String dono = resultado.getString("nome_dono");
        String telefone = resultado.getString("telefone_dono");
        String caracteristica = resultado.getString("caracteristica");
        Animal animal;

        if ("Cachorro".equals(tipo)) {
            animal = new Cachorro(caracteristica, nome, idade, dono, telefone);
        } else {
            animal = new Gato(caracteristica, nome, idade, dono, telefone);
        }
        return new Consulta(animal, resultado.getString("horario"), resultado.getString("sintomas"));
    }

    private String tipoAnimal(Animal animal) {
        return animal instanceof Cachorro ? "Cachorro" : "Gato";
    }

    private String caracteristica(Animal animal) {
        if (animal instanceof Cachorro) return ((Cachorro) animal).getPorte();
        return ((Gato) animal).getTemperamento();
    }

    private boolean indiceValido(int indice) {
        return indice >= 0 && indice < TOTAL_HORARIOS;
    }
}
