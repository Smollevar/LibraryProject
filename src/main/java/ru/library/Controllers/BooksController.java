package ru.library.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.library.Models.Book;
import ru.library.Models.Person;
import ru.library.dao.BookDAO;
import ru.library.dao.PersonDAO;
import ru.library.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BooksController {
    private BookDAO bookDAO;
    private BookValidator bookValidator;
    private PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.personDAO = personDAO;
    }

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

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDAO.assignBook(id, person);
        return "redirect:/books";
    }

    @GetMapping("{id}")
    public String show(Model model, @PathVariable("id") int id) {
        Book book = null;
        if (bookDAO.show(id).isPresent()) {
            book = bookDAO.show(id).get();
            model.addAttribute("book", book);
            model.addAttribute("people", personDAO.index());
        } else System.out.println("Book not found");
        return "/books/show";
    }

        @GetMapping("/{id}/edit")
        public String edit(@PathVariable("id") int id, Model model) {
        if (bookDAO.show(id).isPresent()) model.addAttribute("book", bookDAO.show(id).get());
        return "/books/edit";
    }

    @PatchMapping("/{id}")
        public String patch(@ModelAttribute("book") @Valid Book book,
                            BindingResult br, @PathVariable("id") int id) {
//        bookValidator.validate(book, br);
        if (br.hasErrors()) {
            return "/books/edit";
        }
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String newBook (@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                          BindingResult br) {
        bookValidator.validate(book, br);
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
