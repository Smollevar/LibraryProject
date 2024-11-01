package ru.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.library.Models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final SessionFactory sessionFactory;
//    private final JdbcTemplate jdbcTemplate;


    public PersonDAO(SessionFactory sessionFactory) { // JdbcTemplate jdbcTemplate,
        this.sessionFactory = sessionFactory;
//        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
//        return jdbcTemplate.query("SELECT * FROM Person ORDER BY person_id", new BeanPropertyRowMapper<>(Person.class));
        Session session = sessionFactory.getCurrentSession();
        List<Person> people = session.createQuery("select p from Person p", Person.class).getResultList();
        // common hibernate code

        return people;
    }

    public Person show(int id) {
//        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
//                .stream().findAny().orElse(null);
        return null;
    }

    public Optional<Person> show(String name, int born) {
//        return jdbcTemplate.query("SELECT * FROM Person WHERE fullName=? AND born = ?", new Object[]{name, born}, new BeanPropertyRowMapper<>(Person.class))
//                .stream().findAny();
        return null;
    }



   public void delete(int idForDelete) {
//       jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", idForDelete);
    }

    public void update(int id, Person updatedPerson) {
//        jdbcTemplate.update("UPDATE Person SET fullName=?, born=? WHERE person_id=?",
//                updatedPerson.getFullName(), updatedPerson.getBorn(), id);
//        jdbcTemplate.execute("SELECT * FROM Person ORDER BY person_id ASC");
    }

    public String save(Person person) {
//        List <Person> people = jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
//        int id = IndexFinder.indexFinder(people);
//        jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?)", id, person.getFullName(), person.getBorn());
//        return "/people";
        return null;
    }

    public void createPlaceholderPerson() {
//        if (jdbcTemplate.query("SELECT * FROM Person WHERE person_id =? ", new Object[] {-1}, new BeanPropertyRowMapper<>(Person.class))
//                .stream().findAny().orElse(null) == null) {
//            jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?)", -1, "Книга Свободна", 2024);
        }
    }

//}
