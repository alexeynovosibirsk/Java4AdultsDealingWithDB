//package com.example.demo.utils;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class CreateDB {
//    public static void main(String[] args) {
//        try {
//            Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "123");
//            Statement s = c.createStatement();
//            s.executeUpdate("DROP DATABASE IF EXISTS mydb");
//            s.executeUpdate("CREATE DATABASE mydb");
//            Connection  connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb", "postgres", "123");
//            Statement statement = connection.createStatement();
//            statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS myschema");
//            statement.executeUpdate("create table IF NOT EXISTS myschema.dog(id BIGSERIAL PRIMARY KEY, breed varchar, age int)");
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//}
