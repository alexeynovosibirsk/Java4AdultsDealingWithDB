package com.example.demo.controller;

import com.example.demo.entity.Dog;
import com.example.demo.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    @Autowired
    private Repository repository;

    @PostMapping("/adddog")
    public Optional<Dog> addDog(
            @RequestParam String breed,
            @RequestParam int age) {
        return repository.createDog(breed, age);
    }

    @GetMapping("/getall")
    public List<Dog> getAll() {
        return repository.findAll();
    }

    @GetMapping("/get/{id}")
    public Optional<Dog> getDog(
        @PathVariable("id") long id) {
        return repository.findById(id);
    }

    @PutMapping("/put/{id}")
    public Optional<Dog> updateDog(
            @PathVariable("id") long id,
            @RequestParam String breed,
            @RequestParam int age) {
        return repository.update(id, breed, age);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") long id) {
        repository.delete(id);
    }
}
