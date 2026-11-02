# Clínica Veterinária

## Pré-requisitos

- JDK instalado.
- MySQL Server 8.0 ou superior iniciado em `localhost`, porta `3306`.
- O driver MySQL Connector/J está incluído em `lib/mysql-connector-j-26.10.0.jar`.

Na primeira execução, a aplicação cria o banco `clinica_veterinaria` e a tabela `consultas`.
O usuário configurado precisa ter permissão para criar o banco. A agenda guarda dez horários,
identificados pelos números de 1 a 10 na tela.

## Configurar o acesso

Em `ConexaoBD.java`, ajuste `USUARIO` e `SENHA` para os dados do seu MySQL. O exemplo inicial usa
usuário `root` e senha vazia. Esse usuário também precisa de permissão para criar o banco.

## Compilar e executar no Windows

Abra o PowerShell ou Prompt de Comando nesta pasta e execute:

```text
javac -encoding UTF-8 -cp ".;lib\mysql-connector-j-26.10.0.jar" *.java
java -cp ".;lib\mysql-connector-j-26.10.0.jar" Main
```

O código usa Swing (`JOptionPane`) na interface e JDBC (`DriverManager`, `Connection`,
`PreparedStatement` e `ResultSet`) para acessar o MySQL. As operações de banco ficam em
`AgendaDAO.java`.

Se preferir criar os dados de exemplo antes de executar a aplicação, importe `banco.sql` no MySQL Workbench.
