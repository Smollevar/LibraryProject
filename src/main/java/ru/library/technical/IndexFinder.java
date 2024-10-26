package ru.library.technical;

import ru.library.Models.Book;
import ru.library.Models.Library;
import ru.library.Models.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IndexFinder {
    public static int indexFinder(List entity) {
        List <Person> listPerson = null;
        List <Book> listBook = null;
        ArrayList<Integer> indexes = new ArrayList <>();

        if (entity.getFirst() instanceof Person) {
            listPerson = entity;
            for (Person p : listPerson) indexes.add(p.getPerson_id());
        } else if (entity.getFirst() instanceof Book) {
            listBook = entity;
            for (Book p : listBook) indexes.add(p.getId());
        }
        int id = 0;

        Collections.sort(indexes);
        int i = 0;
        boolean found = false;
        do {
            // Check lowest id from database and if it more than one - we delete first row from db.
            if (indexes.get(i) > 1 && i == 0) {
                id = 1;
                found = true;
            }
            if ((indexes.get(i + 1) - indexes.get(i)) > 1) {
                id = (indexes.get(i) + 1);
                found = true;
            }
            else i++;
            if (i == indexes.size() - 1) {
                id = indexes.get(i) + 1;
                found = true;
            }
        } while(i < indexes.size() && !found);
        return id;
    }
}
