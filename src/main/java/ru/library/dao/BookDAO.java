package ru.library.dao;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.library.Models.Book;
import ru.library.Models.Person;

import java.util.List;
import java.util.Optional;


@Component
public class BookDAO {
//    private final JdbcTemplate jdbcTemplate;
//    private boolean firstTime = true;
    private SessionFactory sessionFactory;

    public BookDAO() {
    }

    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Book> index() {
//        if (firstTime) {
//            List <Integer> ids = jdbcTemplate.queryForList("SELECT id FROM Book ORDER BY id", Integer.class);
//            Collections.sort(ids);
//            for(Integer id : ids) jdbcTemplate.update("UPDATE Book SET person_id=-1 WHERE id = ?", id);
//            firstTime = false;
//        }
//        return jdbcTemplate.query("SELECT id, person_id, title, author, year FROM Book ORDER BY id", new BeanPropertyRowMapper<>(Book.class)); // ORDER BY id
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Book").getResultList();
    }

    @Transactional
    public void save(Book book) {
//        List<Book> books = jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
//        int id = 1;
//        if (!books.isEmpty()) {id = IndexFinder.indexFinder(books);}
//        jdbcTemplate.update("INSERT INTO Book VALUES(?, ?, ?, ?, ?)"
//                ,id, -1, book.getTitle(), book.getAuthor(), book.getYear());
    }

    @Transactional
    public void delete(int id) {
//        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }

    @Transactional
    public Optional<Book> show(int id) {
//        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
//                .stream().findAny();
        return null;
    }

    @Transactional
    public Optional<Book> show(String title) {
//        return jdbcTemplate.query("SELECT * FROM Book WHERE title = ?", new Object[]{title}, new BeanPropertyRowMapper<>(Book.class))
//                .stream().findAny();
        return null;
    }

    @Transactional
    public void update(int id, Book book) {
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
