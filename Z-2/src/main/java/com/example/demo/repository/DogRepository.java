package com.example.demo.repository;

import com.example.demo.entity.Dog;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepository {

    public Iterable<Dog> findAll();
}
