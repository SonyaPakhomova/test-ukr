package com.example.testurk.shopTest.util;

import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class ConnectionUtil {
    private static final String URL = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "0660106493";
    private static final String JDBC_DRIVER = "jdbc:mysql://localhost:3306/shop_test";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find SQL Driver", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.setProperty("user", USERNAME);
        dbProperties.setProperty("password", PASSWORD);
        try {
            return DriverManager.getConnection(URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB ", e);
        }
    }
}
