package hr.tis.academy.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static Database instance;

    private final String url;
    private final String username;
    private final String password;
    private final String schema;

    private Database() {
        Properties properties = new Properties();
        try (InputStream input = Database.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new DatabaseException("application.properties not found on the classpath.");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        this.url = properties.getProperty("spring.datasource.url");
        this.username = properties.getProperty("spring.datasource.username");
        this.password = properties.getProperty("spring.datasource.password", "");
        this.schema = properties.getProperty("spring.datasource.hikari.schema");
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            if (schema != null) {
                connection.setSchema(schema);
            }
            return connection;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
