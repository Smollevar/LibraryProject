package ru.library.Models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Book extends Library{
    private int id;
    private int person_id;
    @NotEmpty(message = "Book must have title")
    private String title;
    @NotEmpty(message = "Book must have author")
    private String author;
    @Min(value = 1, message = "Set in rage from 1 to 2024")
    @Max(value = 2024, message = "Set in rage from 1 to 2024")
    private int year;
    private boolean isTaken;

    public Book() {}

    public Book(String title, String author, int year, boolean isTaken, int person_id) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.isTaken = isTaken;
        this.person_id = person_id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotEmpty String getAuthor() {
        return author;
    }

    public void setAuthor(@NotEmpty String author) {
        this.author = author;
    }

    @NotNull
    public int getYear() {
        return year;
    }

    public void setYear(@NotNull int year) {
        this.year = year;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

}
