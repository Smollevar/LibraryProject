package ru.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.library.Models.Book;
import ru.library.Models.Person;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByOrderById();

    List<Book> findAllByOrderByYear();

    Book findByTitle(String title);

}
