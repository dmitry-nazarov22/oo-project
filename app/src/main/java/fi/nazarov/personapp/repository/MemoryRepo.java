package fi.nazarov.personapp.repository;

import fi.nazarov.personapp.lists.MyList;
import fi.nazarov.personapp.datatype.Person;

public class MemoryRepo implements Repository {
    MyList<Person> persons;

    /**
     * Constructor assigning persons MyList.
     *
     * @param persons MyList containing Person objects.
     */
    public MemoryRepo(MyList<Person> persons) {
        this.persons = persons;
    }

    /**
     * Find all person objects to display them.
     *
     * @return MyList of all Person objects
     */
    @Override
    public MyList<Person> findAll() {
        return persons;
    }

    /**
     * Save new person to the list.
     * @param person person object to be saved.
     * @return true if successful and false if error.
     */
    @Override
    public boolean save(Person person) {
        persons.add(person);
        return true;
    }

    /**
     * Delete a person from the list.
     * @return true if successful and false if error.
     */
    @Override
    public boolean delete(int index) {
        try {
            persons.remove(index);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Edit persons data in the list.
     *
     * @return true if successful and false if error.
     */
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
