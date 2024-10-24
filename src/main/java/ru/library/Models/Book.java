package ru.library.Models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Book extends Library{
    private int id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @NotNull
    private int year;
    private boolean isTaken;

    public Book() {}

    public Book(String title, String author, int year, boolean isTaken) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.isTaken = isTaken;
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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
