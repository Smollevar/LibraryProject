package ru.library.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.library.Models.Person;
import ru.library.services.PeopleServices;
import ru.library.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonValidator personValidator;
    private final PeopleServices peopleServices;

    @Autowired
    public PeopleController(PersonValidator personValidator, PeopleServices peopleServices) {
        this.peopleServices = peopleServices;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleServices.findAll());
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
                         BindingResult br, Model model) {
        personValidator.validate(person, br);
        if (br.hasErrors()) {
            model.addAttribute("person", person);
            return "people/new";
        }
        peopleServices.save(person);
        return "redirect:/people";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleServices.findById(id));
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult br, @PathVariable("id") int id,
                         Model model) {
        personValidator.validate(person, br);
        person.setPersonId(id);
        if (br.hasErrors()) {
            model.addAttribute("person", person);
            return "/people/edit";
        }
        peopleServices.update(person.getPersonId(), person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Person person = null;
        person = peopleServices.show(id);
        model.addAttribute("person", person);
        model.addAttribute("books", peopleServices.getAllBooksOfPerson(person));
        return "/people/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleServices.delete(id);
        return "redirect:/people";
    }
}
