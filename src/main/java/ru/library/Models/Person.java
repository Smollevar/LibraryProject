package ru.library.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Person")
public class Person extends Library{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Pattern(regexp = "[ЁА-Я][ёа-я]+ [ЁА-Я][ёа-я]+ [ЁА-Я][ёа-я]+")
    @Size(min = 6, max = 70, message = "Имя должно быть в формате:Иванов Иван Иванович")
    @Column(name = "fullname")
    private String fullName;
    
    @Min(value = 1940, message = "All people from DB age after 1940 year")
    @Max(value = 2024, message = "All people from DB age before 2025 year")
    @Column(name = "age")
    private int age;

    public Person() {}

    public Person(String fullName, int age) {
        this.fullName = fullName;
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
