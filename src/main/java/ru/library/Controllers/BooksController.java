package ru.library.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.library.Models.Book;
import ru.library.dao.BookDAO;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private BookDAO bookDAO;

    @Autowired
    public BooksController(BookDAO bookDAO) {this.bookDAO = bookDAO;}

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "/books/index";
    }

    @GetMapping("/{id}/changeFK")
    public String changeFK(@ModelAttribute("id") int id, Model model) {
        bookDAO.updateFK(id);
        model.addAttribute("book", bookDAO.show(id));
        return "redirect:/books/{id}";
    }

    @GetMapping("{id}")
    public String show(Model model, @PathVariable("id") int id) {
        Book book = null;
        book = bookDAO.show(id);
        if (book != null) {
            model.addAttribute("book", book);
        } else System.out.println("Book not found");
        return "/books/show";
    }

    @GetMapping("/new")
    public String newBook (@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                          BindingResult br) {
        if (br.hasErrors()) return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }


}
