package ru.library.Models;

import jakarta.validation.constraints.*;

public class Person extends Library{
    private int person_id;
    @Pattern(regexp = "[ЁА-Я][ёа-я]+ [ЁА-Я][ёа-я]+ [ЁА-Я][ёа-я]+")
    @Size(min = 6, max = 70, message = "Имя должно быть в формате:Иванов Иван Иванович")
    private String fullName;

    @Min(value = 1940, message = "All people from DB born after 1940 year")
    @Max(value = 2024, message = "All people from DB born before 2025 year")
    private int born;

    public Person() {}

    public Person(int person_id ,String fullName, int born) {
        this.person_id = person_id;
        this.fullName = fullName;
        this.born = born;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBorn() {
        return born;
    }

    public void setBorn(int born) {
        this.born = born;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int id) {
        this.person_id = id;
    }
}
