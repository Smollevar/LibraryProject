package ru.library.dao;

import jakarta.persistence.Tuple;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.library.Models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private SessionFactory sessionFactory;
    private JdbcTemplate jdbcTemplate;

    public PersonDAO() {}

    @Autowired
    public PersonDAO(SessionFactory sessionFactory, JdbcTemplate jdbcTemplate) {
        this.sessionFactory = sessionFactory;
//        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Person p", Person.class).getResultList();
    }

    @Transactional
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
//        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
//                .stream().findAny().orElse(null);
    }

    @Transactional // todo continue later with adding new person and edit...
    public Optional<Person> show(String name, int born) { // Person
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.createQuery());
    }

    @Transactional
    public void delete(int idForDelete) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, idForDelete));
    }

    @Transactional
    public void update(Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        session.update(updatedPerson);
//        jdbcTemplate.update("UPDATE Person SET fullName=?, born=? WHERE person_id=?",
//                updatedPerson.getFullName(), updatedPerson.getBorn(), id);
//        jdbcTemplate.execute("SELECT * FROM Person ORDER BY person_id ASC");
    }

    @Transactional
    public String save(Person person) {
//        List <Person> people = jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
//        int id = IndexFinder.indexFinder(people);
//        jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?)", id, person.getFullName(), person.getBorn());
//        return "/people";
        return null;
    }

    @Transactional
    public void createPlaceholderPerson() {
//        if (jdbcTemplate.query("SELECT * FROM Person WHERE person_id =? ", new Object[] {-1}, new BeanPropertyRowMapper<>(Person.class))
//                .stream().findAny().orElse(null) == null) {
//            jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?)", -1, "Книга Свободна", 2024);
        }
    }

//}
