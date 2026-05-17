package fi.nazarov.personapp.repository;

import fi.nazarov.personapp.lists.MyArrayList;
import fi.nazarov.personapp.lists.MyList;
import fi.nazarov.personapp.Person;

public class MemoryRepo implements Repository {
    MyList<Person> persons;

    public MemoryRepo(MyList<Person> persons) {
        this.persons = persons;
    }

    @Override
    public MyList<Person> findAll() {
        return persons;
    }

    @Override
    public boolean save(Person person) {
        persons.add(person);
        return true;
    }

    @Override
    public boolean delete(int index) {
        try {
            persons.remove(index);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public boolean edit(int index, Person updated) {
        try {
            persons.set(index, updated);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}
