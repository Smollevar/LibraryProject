package ru.library.util;

import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.unbescape.json.JsonEscapeType;
import ru.library.Models.Person;
import ru.library.dao.PersonDAO;
import ru.library.services.PeopleServices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
        if (peopleServices.validatorMethodByNameAndAge(person.getFullName(), person.getAge()) != null) {
            errors.rejectValue("fullName", "", "Duplicate name");
            errors.rejectValue("age", "", "Duplicate year of age");
        }
        try {
            if (dataParser(person.getPrepareDate())) {
                errors.rejectValue("prepareDate", "", "Date format should be yyyy-MM-dd");
            } else person.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(person.getPrepareDate()));
        } catch (java.text.ParseException e) {
            errors.rejectValue("prepareDate", "", "Invalid date format");
            System.out.println(e.getMessage());
        }
    }

    public boolean dataParser(String date) {
        boolean result = false;
        StringBuilder digit = new StringBuilder();
        int i = date.length() - 1;
        String part = "Year";
        int year = 0;
        int month = 0;
        int comparableDigit;
        while (i > -1 && !result) {
            if (Character.isDigit(date.charAt(i))) {
                System.out.println(date.charAt(i));
                digit.append(date.charAt(i));
            } else if (date.charAt(i) == '/' && part.equals("Year")) {
                digit.reverse();
                year = Integer.parseInt(digit.toString());
                System.out.println("Year is " + year);
                if (year > 2024 || year < 1940) result = true;
                digit = new StringBuilder();
                part = "Month";
            } else if (date.charAt(i) == '/' && part.equals("Month")) {
                digit.reverse();
                month = Integer.parseInt(digit.toString());
                System.out.println("moght is " + month);
                if (month > 12 || month == 0) result = true;
                digit = new StringBuilder();
                part = "Day";
            }
            if (i == 0) {
                digit.reverse();
                comparableDigit = Integer.parseInt(digit.toString());
                System.out.println("day is " + digit);
                System.out.println("day is " + comparableDigit);
                if (comparableDigit > 31 || comparableDigit == 0) result = true;
                if ((month == 4 || month == 6 || month == 9 || month == 10) &&
                        comparableDigit > 30) result = true;
                if (month == 2 && (year % 4 == 0) && comparableDigit > 29 ||
                        month == 2 && (year % 4 != 0) && comparableDigit > 28)
                    result = true;
            }
            i--;
        }
        return result;
    }
}
