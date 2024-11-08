package ru.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.library.Models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
