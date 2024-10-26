package ru.library.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.library.Models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();

        person.setPerson_id(rs.getInt("person_id"));
        person.setFullName(rs.getString("fullName"));
        person.setBorn(rs.getInt("born"));
        return person;
    }
}
