package ru.library.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.library.Models.Book;
import ru.library.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookServices {

    private final BookRepository bookRepository;

    @Autowired
    public BookServices(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // index

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    // show
    public Book findById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    // delete
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public void save(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

}
