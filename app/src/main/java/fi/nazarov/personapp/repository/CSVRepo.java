package fi.nazarov.personapp.repository;

import fi.nazarov.personapp.Person;
import fi.nazarov.personapp.lists.MyList;

public class CSVRepo implements Repository {
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
