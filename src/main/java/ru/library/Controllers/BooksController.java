package ru.library.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.library.Models.Book;
import ru.library.Models.Person;
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
    private final BookValidator bookValidator;
    private int intPage;
    private int intBpp;

    @Autowired
    public BooksController(BookServices bookServices, PeopleServices peopleServices, BookValidator bookValidator, PersonDAO personDAO) { // BookDAO bookDAO,
        this.peopleServices = peopleServices;
        this.bookServices = bookServices;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) String page,
                        @RequestParam(value = "books_per_page", required = false) String bpp,
                        @RequestParam(value = "sort_by_year", required = false) boolean sby)
    {

        if ((page.isEmpty() || bpp.isEmpty()) && !sby) model.addAttribute(bookServices.findAll());
        else if ((page.isEmpty() || bpp.isEmpty()) && sby) model.addAttribute(bookServices.findAllOrderByYear());
        else if ((page == null && bpp == null) && !sby) model.addAttribute("books", bookServices.findAll());
        else if ((page == null && bpp == null) && sby) model.addAttribute("books", bookServices.findAllOrderByYear());
        else if (!(page == null && bpp == null) && sby) {
            parser(page, bpp);
            model.addAttribute(bookServices.findAll(intPage, intBpp));
        }
        else if (!(page == null && bpp == null) && !sby) {
            parser(page, bpp);
            model.addAttribute(bookServices.findAll(intPage, intBpp, sby));
        }
        return "/books/index";
    }

    @PatchMapping("/{id}/return")
    public String freeBook(@ModelAttribute("id") int id, @ModelAttribute("book") Book book) {
        bookServices.freeBook(id);
        return "redirect:/books";
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
        if (book.getOwner() != null) model.addAttribute("countDown", Person.countDown(book.getOwner()));
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
        bookServices.save(book.getId(), book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        bookServices.delete(id);
        return "redirect:/books";
    }

    private void parser(String page, String bpp) {
        intPage = Integer.parseInt(page);
        intBpp = Integer.parseInt(bpp);
    }

}
