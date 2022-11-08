package com.example.demo.repository;

import com.example.demo.entity.Dog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DogService implements DogRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    DataSource dataSource;

    @Override
    public Iterable<Dog> findAll() {
        List<Dog> dogList = new ArrayList<>();

       List<Dog> n = jdbcTemplate.query("select id, breed, age from myschema.dog", this::mapRowToIngredient);
       for(Dog d : n) {
           System.out.println(">>>> " + d);
       }


        return  jdbcTemplate.query("select * from myschema.dog", this::mapRowToIngredient);
    }

    private Dog mapRowToIngredient(ResultSet row, int rowNum)    throws SQLException {
        return new Dog( row.getLong("id"),      row.getString("breed"), row.getInt("age"));
    }


}
