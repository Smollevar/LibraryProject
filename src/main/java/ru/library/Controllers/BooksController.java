package ru.library.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

}
