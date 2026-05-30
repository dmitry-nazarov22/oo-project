package fi.nazarov.personapp.controllers;

import fi.nazarov.personapp.Person;
import fi.nazarov.personapp.lists.MyList;
import fi.nazarov.personapp.repository.Repository;

public class PersonController {
    private final Repository repository;

    /**
     * Constructor assigning repository.
     * @param repository repository to be used with the PersonController.
     */
    public PersonController(Repository repository) {
        this.repository = repository;
    }

    /**
     * Calls findAll method fetching all persons from the repository.
     * @return persons MyList.
     */
    public MyList<Person> fetchAllPersons() {
        return repository.findAll();
    }

    /**
     * Calls repository's save-method with Person object.
     * @param p Person to be saved.
     */
    public void createPerson(Person p) {
        repository.save(p);
    }

    /**
     * Calls delete-method to delete person from repository by index.
     * @param index index of a person in the list to be deleted.
     */
    public void deletePerson(int index) {
        repository.delete(index);
    }

    /**
     * Calls edit-method with index of the Person and the updated Person to update data in the repository.
     * @param index index of a person in the list to be edited.
     * @param updated new Person with the updated data.
     */
    public void editPerson(int index, Person updated) {
        repository.edit(index, updated);
    }
}
