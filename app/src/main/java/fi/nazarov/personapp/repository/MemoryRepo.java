package fi.nazarov.personapp.repository;

import fi.nazarov.personapp.lists.MyArrayList;
import fi.nazarov.personapp.lists.MyList;
import fi.nazarov.personapp.Person;

public class MemoryRepo implements Repository {
    MyList<Person> persons = new MyArrayList<Person>();

    @Override
    public MyList<Person> findAll() {
        return null;
    }

    @Override
    public boolean create(Person person) {
        return false;
    }

    @Override
    public boolean delete(int index) {
        return false;
    }

    @Override
    public boolean edit(int index, Person updated) {
        return false;
    }
}
