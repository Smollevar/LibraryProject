package ru.library.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.library.Models.Book;
import ru.library.Models.Person;
import ru.library.repositories.BookRepository;
import ru.library.repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<Book> findAll(int page, int size, boolean sortByYear) {
        return bookRepository.findAll(PageRequest.of(page, size, Sort.by("year"))).getContent();
    }

    public List<Book> findAll(boolean sortByYear) {
        return bookRepository.findAll(Sort.by("year"));
    }

    public Book show(String title) {
        List<Book> books = findAll();
        return books.stream().filter(b -> b.getTitle().equals(title)).findFirst().orElse(null);
    }

    public List<Book> findAllOrderById() {
        return bookRepository.findAllByOrderById();
    }

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
        if (person != null) {
            person.getBooks().remove(book);
            peopleRepository.save(person);
        }
        book.setOwner(null);
        bookRepository.save(book);
    }

}
