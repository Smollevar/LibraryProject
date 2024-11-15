package ru.library.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.library.Models.Book;
import ru.library.Models.Person;
import ru.library.repositories.BookRepository;
import ru.library.repositories.PeopleRepository;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAll(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Book show(String title) {
        List<Book> books = findAll();
        return books.stream().filter(b -> b.getTitle().equals(title)).findFirst().orElse(null);
    }

    public List<Book> findAllOrderById() {
        return bookRepository.findAllByOrderById();
    }

    public List<Book> findAllOrderByYear() {return bookRepository.findAllByOrderByYear();}

    public Book findById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

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
        Person person = peopleRepository.findById(personId).get();
        if(person.getBooks() == null)
            person.setBooks(new ArrayList<>());
        Book book = bookRepository.findById(id).get();
        person.getBooks().add(book);
        person.setAssigned(new Date());
        book.setOwner(person);
        bookRepository.save(book);
        peopleRepository.save(person);
    }

    public void freeBook(int id) {
        Book book = bookRepository.findById(id).get();
        Person person = book.getOwner();
        person.getBooks().remove(book);
        book.setOwner(null);
        bookRepository.save(book);
        peopleRepository.save(person);
    }

}
