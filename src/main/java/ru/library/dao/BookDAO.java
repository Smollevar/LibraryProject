package ru.library.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.library.Models.Book;
import ru.library.technical.IndexFinder;

import java.sql.ResultSet;
import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT id, person_id, title, author, year FROM Book ORDER BY id", new BeanPropertyRowMapper<>(Book.class)); // ORDER BY id
    }

    public void save(Book book) {
        List<Book> books = jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
        int id = IndexFinder.indexFinder(books);
        jdbcTemplate.update("INSERT INTO Book VALUES(?, ?, ?, ?)", id, book.getTitle(),
                book.getAuthor(), book.getYear());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

}
