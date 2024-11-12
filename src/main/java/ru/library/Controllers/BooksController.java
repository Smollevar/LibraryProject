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
import ru.library.services.BookServices;
import ru.library.services.PeopleServices;
import ru.library.util.BookValidator;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookServices bookServices;
    private final PeopleServices peopleServices;
    private BookValidator bookValidator;
    private int local;

    @Autowired
    public BooksController(BookServices bookServices, PeopleServices peopleServices, BookValidator bookValidator, PersonDAO personDAO) { // BookDAO bookDAO,
        this.peopleServices = peopleServices;
        this.bookServices = bookServices;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookServices.findAllOrderById());
        return "/books/index";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person") Book book, @PathVariable("id") int id) {
        bookServices.assignBook(id, book.getOwner().getPersonId());
        return "redirect:/books";
    }

    @GetMapping("{id}")
    public String show(Model model, @PathVariable("id") int id) {
        Book book = bookServices.findById(id);
        List<Person> people = peopleServices.findAll();
        System.out.println(people);
        model.addAttribute("book", book);
        model.addAttribute("people", people);
        return "/books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookServices.findById(id));
        return "/books/edit";
    }

    @PatchMapping("/{id}")
        public String patch(@ModelAttribute("book") @Valid Book book,
                            BindingResult br, @PathVariable("id") int id) {
        bookValidator.validate(book, br);
        if (br.hasErrors()) return "/books/edit";
        bookServices.update(id, book);
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
//        System.out.println(book);
        bookServices.save(book.getId(), book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        bookServices.delete(id);
        return "redirect:/books";
    }

}
