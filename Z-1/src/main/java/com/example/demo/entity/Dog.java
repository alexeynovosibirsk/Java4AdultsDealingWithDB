package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor  //Lombok - создает конструктор со всеми (3) аргументами
@NoArgsConstructor   //Lombok - создает конструктор без аргументов
public class Dog {

    public Long id;

    public String breed;
    public int age;

    public Dog(String breed, int age) { // такой конструктор Ломбок не создаст, а нам он нужен.
        this.breed = breed;
        this.age = age;
    }
}
