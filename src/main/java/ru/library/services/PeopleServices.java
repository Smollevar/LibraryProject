package ru.library.services;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.library.Models.Book;
import ru.library.Models.Person;
import ru.library.repositories.BookRepository;
import ru.library.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeopleServices {

    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleServices(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll(Sort.by("personId"));
    }

    public Person findById(int id) {
        Optional<Person> optional =  peopleRepository.findById(id);
        return optional.orElse(null);
    }

    public void save(Person person) {
        person.setCreatedAt(new Date());
        peopleRepository.save(person);
    }

    public Person show(int id) {
        Person person = peopleRepository.findById(id).get();
        List<Book> books = person.getBooks();
        Hibernate.initialize(books);
        Hibernate.initialize(person);
        return person;
    }

    public List<Book> getAllBooksOfPerson(Person person) {
        return person.getBooks();
    }

    public void update(int id, Person updatePerson) {
        updatePerson.setCreatedAt(new Date());
        updatePerson.setPersonId(id);
        peopleRepository.save(updatePerson);
    }

    public void delete(int id) {
        List <Book> books = peopleRepository.findById(id).get().getBooks();
        for(Book book : books) {
            book.setOwner(null);
            bookRepository.save(book);
        }
        books.clear();
        peopleRepository.deleteById(id);
    }

    public Person validatorMethodByNameAndAge(String name, int age) {
        return peopleRepository.findAllByFullNameAndAge(name, age);
    }

}
