package ru.library.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Table(name = "person")
@Entity
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;

    @Pattern(regexp = "[ЁА-Я][ёа-я]+ [ЁА-Я][ёа-я]+ [ЁА-Я][ёа-я]+", message = "Имя должно быть в формате:Иванов Иван Иванович")
    @Column(name = "fullName")
    private String fullName;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "assigned")
    @Temporal(TemporalType.DATE)
    private Date assigned;

    @Transient
    private String prepareDate;

    public Person() {}

    public Person(int personId, String fullName, int age) {
        this.personId = personId;
        this.fullName = fullName;
        this.age = age;
    }

    public Date getAssigned() {
        return assigned;
    }

    public void setAssigned(Date assigned) {
        this.assigned = assigned;
    }

    public String getPrepareDate() {
        return prepareDate;
    }

    public void setPrepareDate(String prepareDate) {
        this.prepareDate = prepareDate;
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

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", fullName='" + fullName + '\'' +
                ", age=" + age +
                '}';
    }

    public static int countDown(Person person) {
        int days;
        Date current = new Date();
        long diff = current.getTime() - person.getAssigned().getTime();
        days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personId == person.personId && age == person.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, age);
    }
}
