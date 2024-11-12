package ru.library.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.library.Models.Book;
import ru.library.dao.BookDAO;
import ru.library.services.BookServices;

@Component
public class BookValidator implements Validator {

    private final BookServices bookServices;

    public BookValidator(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (bookServices.show(book.getTitle()) != null) {
            errors.rejectValue("title", null, "Title already exists");
        }
    }
}
