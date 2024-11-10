package ru.library.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.library.Models.Person;
import ru.library.dao.PersonDAO;
import ru.library.services.PeopleServices;

@Component
public class PersonValidator implements Validator {

//    private final PersonDAO personDAO;
    private final PeopleServices peopleServices;

    public PersonValidator(PeopleServices peopleServices) { //PersonDAO personDAO,
        this.peopleServices = peopleServices;
//        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        // while create or edit person if we detect same name and age - error.
        if (peopleServices.validatorMethodByNameAndAge(person.getFullName(), person.getAge()) != null) {
            errors.rejectValue("fullName", "", "Duplicate name");
            errors.rejectValue("age", "", "Duplicate year of age");
        }
//        if (personDAO.show(person.getFullName(), person.getAge()).isPresent()) { // .isPresent()
//            errors.rejectValue("fullName", "", "Duplicate name");
//            errors.rejectValue("age", "", "Duplicate year of age");
//        }
    }
}
