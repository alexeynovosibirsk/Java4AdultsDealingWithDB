package com.example.demo.repository;

import com.example.demo.entity.Dog;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DogRepository {

    public Iterable<Dog> findAll();

    public Optional<Dog> findById(long id);

    public Optional<Dog> saveDog(String breed, int age);

    public Optional<Dog> updateDog(long id, String breed, int age);

    public void deleteDog(long id);
}

