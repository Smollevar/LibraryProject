package ru.library.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.library.Models.Book;
import ru.library.Models.Person;
import ru.library.repositories.BookRepository;
import ru.library.repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
todo rewrite BookDAO with finder by name and born data
 */

@Service
@Transactional
public class BookServices {

    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookServices(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    // index

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAllOrderById() {
        return bookRepository.findAllByOrderById();
    }

    // show
    public Book findById(int id) {
        return bookRepository.findById(id).orElse(null);
//        Optional<Book> book = bookRepository.findById(id);
//        return book.orElse(null);
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

    public void assignBook(int id, int personId) {
        System.out.println(id);
        System.out.println(personId);
        Person person = peopleRepository.findById(personId).get();
        if(person.getBooks() == null)
            person.setBooks(new ArrayList<>());
        Book book = bookRepository.findById(id).get();
        person.getBooks().add(book);
        book.setOwner(person);
        bookRepository.save(book);
        peopleRepository.save(person);
    }
}
