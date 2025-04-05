package com.hospital;
import java.sql.*;

public class SQLServerConnect {
    public static void main(String[] args) {
        String user = "johnnazizz";
        String password = "blabla1";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=HOSPITAL;encrypt=false";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to SQL Server successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}