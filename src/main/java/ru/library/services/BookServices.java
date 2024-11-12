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

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book show(String title) {
//        return bookRepository.findByTitle(title);
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
//        System.out.println(id);
//        System.out.println(personId);
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
