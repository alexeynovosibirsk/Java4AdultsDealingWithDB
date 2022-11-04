package com.example.demo.controller;

import com.example.demo.entity.Dog;
import com.example.demo.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private Repository repository;

    @PostMapping("/adddog")
    public Dog addDog(
            @RequestParam String breed,
            @RequestParam int age) {
        Dog dog = new Dog(breed, age);
        repository.createDog(dog.breed, dog.age);
        return dog;
    }

    @GetMapping("/getall")
    public List<String> getAll() {
        return repository.findAll();
    }

    @GetMapping("/get/{id}")
    public Dog getDog(
        @PathVariable("id") long id) {
        String dogFromBD = repository.findById(id);
        String[] spliter = dogFromBD.split(" ");
        return new Dog(spliter[0], Integer.parseInt(spliter[1]));
    }

    @PutMapping("/put/{id}")
    public Dog updateDog(
            @PathVariable("id") long id,
            @RequestParam String breed,
            @RequestParam int age) {
        String dogFromDB = repository.findById(id);
        String[] spliter = dogFromDB.split(" ");
        Dog dog = new Dog(spliter[0], Integer.parseInt(spliter[1]));
        dog.setBreed(breed);
        dog.setAge(age);
        repository.update(id, dog.breed, dog.age);
        return dog;
    }
}
