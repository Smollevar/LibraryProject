package ru.library.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.library.Models.Person;
import ru.library.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeopleServices {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleServices(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(int id) {
        Optional<Person> optional =  peopleRepository.findById(id);
        return optional.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatePerson) {
        updatePerson.setPerson_id(id);
        peopleRepository.save(updatePerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

}
