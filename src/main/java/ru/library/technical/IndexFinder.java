package ru.library.technical;

import ru.library.Models.Book;
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
        } else if (entity.getFirst() instanceof Book) {
            listBook = entity;
            for (Book p : listBook) indexes.add(p.getId());
        }

        Collections.sort(indexes);

        int id = 0;
        int current_index = 1;
        boolean found = false;
        do {
            if (current_index < indexes.get(id)) {
                found = true;
            } else if (current_index == indexes.get(id)) current_index++;
            else if (id == indexes.size() - 1) found = true;
            id++;
        } while(id < indexes.size() && !found);
        return current_index;
    }
}
