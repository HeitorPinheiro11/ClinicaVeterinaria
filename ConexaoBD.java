import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBD {
    private static final String URL_SERVIDOR = "jdbc:mysql://localhost:3306/";
    private static final String URL_BANCO = URL_SERVIDOR + "clinica_veterinaria";
    private static final String USUARIO = "root";
    private static final String SENHA = "bancode20";

    public static Connection conectar() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conexaoServidor = DriverManager.getConnection(URL_SERVIDOR, USUARIO, SENHA);
             Statement comando = conexaoServidor.createStatement()) {
            comando.executeUpdate("CREATE DATABASE IF NOT EXISTS clinica_veterinaria");
        }

        return DriverManager.getConnection(URL_BANCO, USUARIO, SENHA);
    }
}
