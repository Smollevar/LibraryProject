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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private JdbcTemplate jdbcTemplate;
//    private PersonDAO personDAO;
    private boolean firstTime = true;
    private SessionFactory sessionFactory;

    public BookDAO() {
    }

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, PersonDAO personDAO) { // SessionFactory sessionFactory,
//        this.personDAO = personDAO;
//        this.sessionFactory = sessionFactory;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public List<Book> index() {
        Session session = sessionFactory.getCurrentSession();
        List <Book> books = session.createQuery("SELECT b FROM Book b ORDER BY id", Book.class).getResultList();
        if (firstTime) {
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
    }

    @Transactional
    public Book show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Transactional
    public Optional<Book> show(String title) {
        return jdbcTemplate.query("SELECT * FROM Book where title = ?", new Object[] {title}, new BeanPropertyRowMapper<Book>(Book.class))
                .stream().findAny();
    }

    @Transactional
    public void update(int id, Book book) {
        Session session = sessionFactory.getCurrentSession();
        book.setOwner(session.get(Person.class, id));
        session.update(book);
    }

    @Transactional
    public void assignBook(int id, int personId) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, personId);
        if(person.getBooks() == null)
            person.setBooks(new ArrayList<>());
        Book book = session.get(Book.class, id);
        person.getBooks().add(book);
        book.setOwner(person);
        session.saveOrUpdate(book);
        session.saveOrUpdate(person);

//
//        if (person.getBooks() == null)
//            person.setBooks(new ArrayList<>());
//        person.getBooks().add(session.get(Book.class, id));
//        session.saveOrUpdate(person);
//        Book book = session.get(Book.class, id);
//        book.setOwner(person);
//        session.save(book);

//        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ?", person.getPerson_id(), id);
    }

}
