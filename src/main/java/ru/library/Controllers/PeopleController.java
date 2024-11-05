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
import ru.library.util.PersonValidator;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final BookDAO bookDAO;
    private PersonValidator personValidator;
    private PersonDAO personDAO;
//    private boolean firstTime = true;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO , PersonValidator personValidator) { //
        this.personDAO = personDAO;
        this.personValidator = personValidator;
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String index(Model model) {
//        if (firstTime) {
//            personDAO.createPlaceholderPerson();
//            firstTime = false;
//        }
        List<Person> people = personDAO.index();
        model.addAttribute("people", personDAO.index());
        return "/people/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person,
                            Model model) {
        model.addAttribute("person", person);
        return "/people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult br) {
        System.out.println(person);
        personValidator.validate(person, br);
        if (br.hasErrors()) {
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult br, @PathVariable("id") int id) {
        personValidator.validate(person, br);
        if (br.hasErrors()) {
            person.setPerson_id(id);
            return "/people/edit";
        }
        personDAO.update(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Person person = null;
        person = personDAO.show(id);
//        System.out.println(person.getBooks() == null);
//        for(Book book : person.getBooks()) {
//            System.out.println(book);
//        }
        // get list of books, transfer as second attribute,
        // th:each on books, and if it equal with id of current user: print name of book.
//        List<Book> books = bookDAO.index();
//        int i = 0;
//        int counter = 0;
//        while(books.size() > i) {
//            if (person.getPerson_id() == books.get(i).getPerson_id()) counter++;
//            i++;
//        }
        if (person != null) {
            model.addAttribute("person", person);
//            model.addAttribute("books", books);
//            model.addAttribute("counter", counter);
        } else System.out.println("Person not found");
        return "/people/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
//        List<Book> books = bookDAO.index();
//        List<Integer> taken = new ArrayList<>(30);
//        int i = 0;
//        while(books.size() > i) {
//            if (books.get(i).getPerson_id() == id) {
//                taken.add(books.get(i).getId());
//            }
//            i++;
//        }
//
//        personDAO.delete(id);
//
//        i = 0;
//        while(taken.size() > i) {
//            bookDAO.assignBook(taken.get(i));
//            i++;
//        }
//
        return "redirect:/people";
//    return null;
    }
}
