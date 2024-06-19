package com.devin.releaseradar.db;

import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.SQLException;

import org.springframework.stereotype.Controller;

@Controller
public class JDBCTemplate {
    static final String JDBC_DRIVER = "org.h2.Driver";   
    static final String DB_URL = "jdbc:h2:mem:dcbapp";  
    static final String USER = "sa"; 
    static final String PASS = "password"; 

    public Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}