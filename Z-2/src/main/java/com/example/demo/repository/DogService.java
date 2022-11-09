package com.example.demo.repository;

import com.example.demo.entity.Dog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class DogService implements DogRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public Iterable<Dog> findAll() {
        return  jdbcTemplate.query("select * from myschema.dog", new RowMapper<Dog>() {
            @Override
            public Dog mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Dog(
                        rs.getLong("id"),
                        rs.getString("breed"),
                        rs.getInt("age"));
            }
        });
    }

    private Dog mapRowToDog(ResultSet row, int rowNum) throws SQLException {
        return new Dog(row.getLong("id"),
                       row.getString("breed"),
                       row.getInt("age")
        );
    }

    @Override
    public Optional<Dog> findById(long id) {
        List<Dog> resultList = jdbcTemplate.query("select * from myschema.dog where id=?", this::mapRowToDog, id);
        return resultList.size() == 0 ? Optional.empty() : Optional.of(resultList.get(0));
    }

    @Override
    public Optional<Dog> saveDog(String breed, int age) {
        Optional<Dog> dog = Optional.empty();
      //   jdbcTemplate.update("insert into myschema.dog (breed, age) values(?,?)", breed, age); Для записи достаточно этой строки. Однако чтобы вернуть id присвоенный БД придется проделать:
        KeyHolder holder = new GeneratedKeyHolder(); // Создаем холдер, в который будут положены все значения Дог в том числе и id=....
        jdbcTemplate.update(new PreparedStatementCreator() {   // Создаем новый Стейтменткриэйтор и в нем переопределяем PreparedStatement
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("insert into myschema.dog (breed, age) values(?,?)", Statement.RETURN_GENERATED_KEYS); // выполняем запрос через стейтмент
                        ps.setString(1, breed);
                        ps.setInt(2, age);
                return ps;
            }
        }, holder);

        dog = Optional.of(new Dog(
                      Long.parseLong(holder.getKeys().get("id").toString()), // извлекаем из холдера айди, кастуем к лонг
                      breed,
                      age));
         return dog;
    }

    @Override
    public Optional<Dog> updateDog(long id, String breed, int age) {
        Optional<Dog> dog = Optional.empty();
       int result = jdbcTemplate.update("insert into myschema.dog(breed, age) values (?, ?) where id=?", breed, age, id);
       if(result > 0) {
                dog = Optional.of(new Dog(id, breed, age));
            }
        return dog;

    }

    @Override
    public void deleteDog(long id) {
        jdbcTemplate.update("delete from myschema.dog where id=?", id);
    }
}
