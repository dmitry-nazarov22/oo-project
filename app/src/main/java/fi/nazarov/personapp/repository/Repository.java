package fi.nazarov.personapp.repository;

import fi.nazarov.personapp.datatype.Person;
import fi.nazarov.personapp.lists.MyList;

public interface Repository {
    /**
     * Find all person objects to display them.
     *
     * @return MyList of all Person objects
     */
    MyList<Person> findAll();

    /**
     * Save new person to the list.
     * @param person person object to be saved.
     * @return true if successful and false if error.
     */
    boolean save(Person person);

    /**
     * Delete a person from the list.
     * @return true if successful and false if error.
     */
    boolean delete(int index);

    /**
     * Edit persons data in the list.
     *
     * @return true if successful and false if error.
     */
    boolean edit(int index, Person updated);
}
