package controllers;

import java.sql.*;
import java.util.Properties;

public class DataBaseController {
    private static DataBaseController instance;

    private Connection connection;

    public static DataBaseController getInstance() {
        if (instance == null) {
            instance = new DataBaseController();
        }
        return instance;
    }

    public void connect(String host, int port, String database, String user, String password) throws SQLException {
        String url = getUrl(host, port, database);
        Properties props = getProperties(user, password);

        this.connection = DriverManager.getConnection(url, props);

        System.out.println("Database connected");
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(query);
    }

    private String getUrl(String host, int port, String database) {
        return "jdbc:postgresql://" + host + ":" + port + "/" + database;
    }

    private Properties getProperties(String user, String password) {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        return props;
    }
}
