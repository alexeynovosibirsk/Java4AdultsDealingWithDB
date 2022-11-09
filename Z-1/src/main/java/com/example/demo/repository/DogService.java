package com.example.demo.repository;

import com.example.demo.entity.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DogService implements DogRepository {

    @Autowired
    Dog dog;
    @Autowired
    ApplicationContext applicationContext;

    private String urlDB = "jdbc:postgresql://localhost:5432/mydb";
    private String user = "postgres";
    private String password = "123";

    private PreparedStatement statement;
    private ResultSet resultSet;

    @Override
    public Optional<Dog> saveDog(String breed, int age) {
        Connection connection = null;
        Optional<Dog> dogOptional = Optional.empty();

        try {
            connection = DriverManager.getConnection(urlDB, user, password);
            statement = connection.prepareStatement("insert into myschema.dog(breed, age) values(?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, breed);
            statement.setInt(2, age);
            int rowsInserted = statement.executeUpdate();
            try(ResultSet genKeys = statement.getGeneratedKeys()) {
                if(genKeys.next())
                dogOptional = Optional.of(new Dog(
                        genKeys.getLong(1),
                        breed,
                        age
                ));
                if (rowsInserted > 0) {
                    Dog d = dogOptional.get();

                }
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
        return dogOptional;
    }
    @Override
    public List<Dog> findAll() {
        List<Dog> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(urlDB, user, password)) {
            statement = connection.prepareStatement("select * from myschema.dog");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                 dog = new Dog(
                        resultSet.getLong("id"),
                        resultSet.getString("breed"),
                        resultSet.getInt("age"));
                result.add(dog);
            }
        } catch (SQLException e) {
           e.printStackTrace();
            }
        return result;
    }

    @Override
    public Optional<Dog> findById(long id) {
        Optional<Dog> dog = Optional.empty();

        try (Connection connection = DriverManager.getConnection(urlDB, user, password)) {
            statement = connection.prepareStatement("select * from myschema.dog where id=?");
            statement.setLong(1, id);  // Указывается номер столбца(нумерация с 1), затем переменная.
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                 dog = Optional.of(new Dog(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            }
        return dog;
    }
    @Override
    public Optional<Dog> updateDog(long id, String breed, int age) {
        Optional<Dog> dog = Optional.empty();

        try (Connection conn = DriverManager.getConnection(urlDB, user, password)) {
            statement = conn.prepareStatement("UPDATE myschema.dog SET breed=?, age=? WHERE id=?");
            statement.setString(1, breed);
            statement.setInt(2, age);
            statement.setLong(3, id); // Здесь указывается не номер столбца, а номер переменной в запросе!
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                        dog = Optional.of(new Dog(id, breed, age));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dog;
    }
    @Override
    public void deleteDog(long id) {
        try(Connection conn = DriverManager.getConnection(urlDB, user, password)) {
            statement = conn.prepareStatement("DELETE FROM myschema.dog WHERE id=?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
