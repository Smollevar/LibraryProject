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

    @Autowired
    public BooksController(BookServices bookServices, PeopleServices peopleServices, BookValidator bookValidator, PersonDAO personDAO) { // BookDAO bookDAO,
        this.peopleServices = peopleServices;
        this.bookServices = bookServices;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model) {
        System.out.println("Common index");
        model.addAttribute("books", bookServices.findAllOrderByYear());
        return "/books/index";
    }

//    @GetMapping("{page}&{books_per_page}")
    @GetMapping("/pagination")
    /*
    &{books_per_page}  {page}&{books_per_page}  ?page/{totalPages}&books_per_page/{size}   /page/{totalPages}&books_per_page/{size}
    ?page/{order}&books_per_page/{size} ?page={order}&books_per_page={size} page={order}&books_per_page={size}
    {order}&{size} ?{order}&{size} ?{page}&{books_per_page} ?{page}&{books_per_page}/p ?page={totalPages}&books_per_page={size}

     */
    public String index(@ModelAttribute("page") int order,
//                        @ModelAttribute("books_per_page") int size,
                        Model model) {
//        System.out.println("42 line " + order + " " + size);
        System.out.println("42 line " + order);
        return "redirect:/books";
//        return null;
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

}
