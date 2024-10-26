package ru.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.w3c.dom.ls.LSOutput;
import ru.library.Models.Person;
import ru.library.technical.IndexFinder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person ORDER BY person_id", new BeanPropertyRowMapper<>(Person.class));
    }

   public void delete(int idForDelete) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", idForDelete);
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET fullName=?, born=? WHERE person_id=?",
                updatedPerson.getFullName(), updatedPerson.getBorn(), id);
        jdbcTemplate.execute("SELECT * FROM Person ORDER BY person_id ASC");
    }

    public String save(Person person) {
        List <Person> people = jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
        int id = IndexFinder.indexFinder(people);
        jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?)", id, person.getFullName(), person.getBorn());
        return "/people";
    }

}
