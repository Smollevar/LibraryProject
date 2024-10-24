package ru.library.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.library.Models.Book;
import ru.library.dao.BookDAO;

@Controller
@RequestMapping("/books")
public class BooksController {
    private BookDAO bookDAO;

    @Autowired
    public BooksController(BookDAO bookDAO) {this.bookDAO = bookDAO;}

    @GetMapping()
    public String books(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook (@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create (@ModelAttribute("book") @Valid Book book,
                          BindingResult br) {
        if (br.hasErrors()) return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }

}
