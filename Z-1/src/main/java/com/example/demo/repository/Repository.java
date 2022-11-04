package com.example.demo.repository;

import com.example.demo.entity.Dog;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PutMapping;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class Repository {

    private String urlDB = "jdbc:postgresql://localhost:5432/mydb";
    private String user = "postgres";
    private String password = "123";

    private PreparedStatement statement;
    private ResultSet resultSet;

    public void createDog(String breedString, int ageInt) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(urlDB, user, password);
            statement = connection.prepareStatement("insert into myschema.dog(breed, age) values(?, ?)");
            statement.setString(1, breedString);
            statement.setInt(2, ageInt);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Insert completed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public List<String> findAll() {
        List<String> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(urlDB, user, password)) {
            statement = connection.prepareStatement("select * from myschema.dog");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
               result.add(
                        resultSet.getString("breed") + " " +
                        resultSet.getInt("age"));
            }
        } catch (SQLException e) {
           e.printStackTrace();
            }
        return result;
    }

    public String findById(long id) {
        StringBuilder stringBuilder = new StringBuilder();

        try (Connection connection = DriverManager.getConnection(urlDB, user, password)) {
            statement = connection.prepareStatement("select * from myschema.dog where id=?");
            statement.setLong(1, id);  // Указывается номер столбца(нумерация с 1), затем переменная.
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                stringBuilder.append(resultSet.getString(2));
                stringBuilder.append(" ");
                stringBuilder.append(resultSet.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            }
        return stringBuilder.toString();
    }

    public void update(long id, String breed, int age) {

        try (Connection conn = DriverManager.getConnection(urlDB, user, password)) {
            statement = conn.prepareStatement("UPDATE myschema.dog SET breed=?, age=? WHERE id=?");
            statement.setString(1, breed);
            statement.setInt(2, age);
            statement.setLong(3, id); // Здесь указывается не номер столбца, а номер переменной в запросе!

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update completed!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}
