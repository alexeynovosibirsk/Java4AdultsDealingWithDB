package com.example.demo.controller;

import com.example.demo.entity.Dog;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @GetMapping("/getdog")
    public Dog getDog() {
        return new Dog("Malamut", 1);
    }

    @PostMapping("/adddog")
    public Dog addDog(
            @RequestParam String breed,
            @RequestParam int age) {
        return new Dog(breed, age);
    }
}
