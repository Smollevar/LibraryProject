package ru.library.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Table(name = "person")
@Entity
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;

    @Pattern(regexp = "[ЁА-Я][ёа-я]+ [ЁА-Я][ёа-я]+ [ЁА-Я][ёа-я]+")
    @Size(min = 6, max = 70, message = "Имя должно быть в формате:Иванов Иван Иванович")
    @Column(name = "fullName")
    private String fullName;

    @Min(value = 1940, message = "All people from DB age after 1940 year")
    @Max(value = 2024, message = "All people from DB age before 2025 year")
    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person() {}

    public Person(int person_id, String fullName, int age) {
        this.person_id = person_id;
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

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "person_id=" + person_id +
                ", fullName='" + fullName + '\'' +
                ", age=" + age +
                '}';
    }
}
