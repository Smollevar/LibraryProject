package ru.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.library.Models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
