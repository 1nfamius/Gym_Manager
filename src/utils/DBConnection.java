package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL      = "jdbc:postgresql://localhost:5432/gymmanager";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "1";

    private static Connection connection = null;

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("[DB] Conexión establecida con PostgreSQL.");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver PostgreSQL no encontrado. Añade postgresql.jar al classpath.", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("[DB] Conexión cerrada.");
                }
            } catch (SQLException e) {
                System.err.println("[DB] Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}