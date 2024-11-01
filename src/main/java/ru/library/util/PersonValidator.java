package ru.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.library.Models.Person;
import ru.library.dao.PersonDAO;

@Component
public class PersonValidator implements Validator {

    private PersonDAO personDAO;

    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personDAO.show(person.getFullName(), person.getAge()).isPresent()) {
            errors.rejectValue("fullName", "", "Duplicate name");
            errors.rejectValue("born", "", "Duplicate year of born");
        }

    }
}
