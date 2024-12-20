package ru.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.library.Models.Person;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Person findAllByFullNameAndAge(String fullName, int age);

    List<Person> findAllByOrderByPersonId();

}
