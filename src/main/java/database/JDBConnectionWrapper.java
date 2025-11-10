package database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBConnectionWrapper {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost/";
//    private static final String USER = "root";
//    private static final String PASSWORD = "Dan1690323";
    private static final int TIMEOUT = 5;

    private Connection connection;
    public JDBConnectionWrapper(String schema) {
        try {
            Class.forName(JDBC_DRIVER);


            Properties props = new Properties();
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    throw new RuntimeException("db.properties nu a fost găsit!");
                }
                props.load(input);
            }

            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            String url = "jdbc:mysql://localhost/" + schema + "?allowMultiQueries=true";

            // Creează conexiunea
            connection = DriverManager.getConnection(url, user, password);

            createTables();

        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    private void createTables() throws SQLException {
        Statement statement = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS book(" +
                " id bigint NOT NULL AUTO_INCREMENT," +
                " author varchar(500) NOT NULL," +
                " title varchar(500) NOT NULL," +
                " publishedDate datetime DEFAULT NULL," +
                " PRIMARY KEY(id)," +
                " UNIQUE KEY id_UNIQUE(id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";

        statement.execute(sql);
    }

    public boolean testConnection() throws SQLException {
        return connection.isValid(TIMEOUT);
    }

    public Connection getConnection(){
        return connection;
    }
}
