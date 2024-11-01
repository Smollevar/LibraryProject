package ru.library.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.library.Models.Book;
import ru.library.Models.Person;
import ru.library.technical.IndexFinder;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Component
public class BookDAO {
//    private final JdbcTemplate jdbcTemplate;
    private boolean firstTime = true;

//    public BookDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    public List<Book> index() {
//        if (firstTime) {
//            List <Integer> ids = jdbcTemplate.queryForList("SELECT id FROM Book ORDER BY id", Integer.class);
//            Collections.sort(ids);
//            for(Integer id : ids) jdbcTemplate.update("UPDATE Book SET person_id=-1 WHERE id = ?", id);
//            firstTime = false;
//        }
//        return jdbcTemplate.query("SELECT id, person_id, title, author, year FROM Book ORDER BY id", new BeanPropertyRowMapper<>(Book.class)); // ORDER BY id
        return null;
    }

    public void save(Book book) {
//        List<Book> books = jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
//        int id = 1;
//        if (!books.isEmpty()) {id = IndexFinder.indexFinder(books);}
//        jdbcTemplate.update("INSERT INTO Book VALUES(?, ?, ?, ?, ?)"
//                ,id, -1, book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void delete(int id) {
//        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }

    public Optional<Book> show(int id) {
//        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
//                .stream().findAny();
        return null;
    }

    public Optional<Book> show(String title) {
//        return jdbcTemplate.query("SELECT * FROM Book WHERE title = ?", new Object[]{title}, new BeanPropertyRowMapper<>(Book.class))
//                .stream().findAny();
        return null;
    }

    public void update(int id, Book book) {
//        jdbcTemplate.update("UPDATE Book SET title = ?, author = ?, year = ? WHERE id = ?"
//                , book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void updateFK(int id) {
//        jdbcTemplate.update("UPDATE Book SET person_id = 4 WHERE id = ?", id);
    }

    public void assignBook(int id, Person person) {
//        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ?", person.getPerson_id(), id);
    }

    public void assignBook(int bookId) {
//        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ?", -1, bookId);
    }

    // jdbcTemplate.update("UPDATE Book SET person_id=9999 WHERE id = ?", id);
}
