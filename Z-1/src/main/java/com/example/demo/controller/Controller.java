package com.example.demo.controller;

import com.example.demo.entity.Dog;
import com.example.demo.repository.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    @Autowired
    private DogService dogService;

    @PostMapping("/adddog")
    public Optional<Dog> addDog(
            @RequestParam String breed,
            @RequestParam int age) {
        return dogService.saveDog(breed, age);
    }

    @GetMapping("/getall")
    public List<Dog> getAll() {
        return dogService.findAll();
    }

    @GetMapping("/get/{id}")
    public Optional<Dog> getDog(
        @PathVariable("id") long id) {
        return dogService.findById(id);
    }

    @PutMapping("/put/{id}")
    public Optional<Dog> updateDog(
            @PathVariable("id") long id,
            @RequestParam String breed,
            @RequestParam int age) {
        return dogService.updateDog(id, breed, age);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") long id) {
        dogService.deleteDog(id);
    }
}
