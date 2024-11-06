package ru.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.library.Models.Book;
import ru.library.Models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private SessionFactory sessionFactory;
    private JdbcTemplate jdbcTemplate;
    private boolean first_time;

    public PersonDAO() {}

    @Autowired
    public PersonDAO(SessionFactory sessionFactory, JdbcTemplate jdbcTemplate) {
        this.sessionFactory = sessionFactory;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Person p ORDER BY person_id", Person.class).getResultList();
    }

    @Transactional
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        System.out.println(person.getBooks());
        return person;
    }

    public Optional<Person> show(String name, int born) { // Person
        return jdbcTemplate.query("SELECT * FROM Person WHERE fullName = ? and age = ?", new Object[]{name, born}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    @Transactional
    public void delete(int idForDelete) {
        Session session = sessionFactory.getCurrentSession();
        Person personForDelete = session.get(Person.class, idForDelete);
        Person defaultPerson = session.get(Person.class, -1);
        List<Book> books = personForDelete.getBooks();
        for (Book book : books) {
            book.setOwner(defaultPerson);
            session.save(book);
        }
        personForDelete.getBooks().clear();
        session.save(personForDelete);
        session.remove(personForDelete);
    }

    @Transactional
    public void update(Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        session.update(updatedPerson);
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void createPlaceholderPerson() {
        if (jdbcTemplate.query("SELECT * FROM Person WHERE person_id =? ", new Object[] {-1}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null) == null) {
            jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?)", -1, "Книга Свободна", 2024);
        }
}

}
