package ru.library.dao;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.library.Models.Book;
import ru.library.Models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private JdbcTemplate jdbcTemplate;
    private PersonDAO personDAO;
    private boolean firstTime = true;
    private SessionFactory sessionFactory;

    public BookDAO() {
    }

    @Autowired
    public BookDAO(SessionFactory sessionFactory, JdbcTemplate jdbcTemplate, PersonDAO personDAO) {
        this.personDAO = personDAO;
        this.sessionFactory = sessionFactory;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public List<Book> index() {
        Session session = sessionFactory.getCurrentSession();
        List <Book> books = session.createQuery("SELECT b FROM Book b ORDER BY id", Book.class).getResultList();
        if (firstTime) {
//            Person placeHolderPerson = session.get(Person.class, -1);
            for(Book book : books) {
                book.setOwner(session.get(Person.class, -1));
            }
            firstTime = false;
        }
        return books;
    }

    @Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        book.setOwner(session.get(Person.class, -1));
        session.saveOrUpdate(book);
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(Book.class, id));
//        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }

    @Transactional
    public Book show(int id) {
//        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
//                .stream().findAny();
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Transactional
    public Optional<Book> show(String title) {
//        return jdbcTemplate.query("SELECT * FROM Book WHERE title = ?", new Object[]{title}, new BeanPropertyRowMapper<>(Book.class))
//                .stream().findAny();
//        Session session = sessionFactory.getCurrentSession();
        return jdbcTemplate.query("SELECT * FROM Book where title = ?", new Object[] {title}, new BeanPropertyRowMapper<Book>(Book.class))
                .stream().findAny();
    }

    @Transactional
    public void update(int id, Book book) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        person.getBooks().add(book);
        session.saveOrUpdate(person);
        session.update(book);
//        jdbcTemplate.update("UPDATE Book SET title = ?, author = ?, year = ? WHERE id = ?"
//                , book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    @Transactional
    public void updateFK(int id) {
//        jdbcTemplate.update("UPDATE Book SET person_id = 4 WHERE id = ?", id);
    }

    @Transactional
    public void assignBook(int id, Person person) {
//        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ?", person.getPerson_id(), id);
    }

    @Transactional
    public void assignBook(int bookId) {
//        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ?", -1, bookId);
    }

    // jdbcTemplate.update("UPDATE Book SET person_id=9999 WHERE id = ?", id);
}
