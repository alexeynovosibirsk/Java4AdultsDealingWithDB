package com.example.demo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "123");
            System.out.println("Соединение с СУБД успешно!");
            connection.close();
            System.out.println("Отключение от СУБД завершено.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка!");
        }
    }
}
