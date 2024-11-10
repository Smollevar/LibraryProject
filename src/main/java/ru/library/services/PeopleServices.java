package ru.library.services;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
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
//        return peopleRepository.findAllByOrderByPerson_id();
        return peopleRepository.findAll();
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
        for(Book book : books) {
            System.out.println(book.getOwner().getPerson_id());
        }
//        System.out.println(person.getBooks().size());
        return person;
    }

    public void update(int id, Person updatePerson) {
        updatePerson.setCreatedAt(new Date());
        updatePerson.setPerson_id(id);
        peopleRepository.save(updatePerson);
    }

    public void delete(int id) {
        List <Book> books = peopleRepository.findById(id).get().getBooks();
        for(Book book : books) {
            book.setOwner(peopleRepository.findById(-1).get());
            bookRepository.save(book);
        }
        books.clear();
        peopleRepository.deleteById(id);
    }

    public Person validatorMethodByNameAndAge(String name, int age) {
        return peopleRepository.findAllByFullNameAndAge(name, age);
    }

}
