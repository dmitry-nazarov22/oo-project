package fi.nazarov.personapp.controllers;

import fi.nazarov.personapp.Person;
import fi.nazarov.personapp.lists.MyList;
import fi.nazarov.personapp.repository.Repository;

public class PersonController {
    private final Repository repository;

    public PersonController(Repository repository) {
        this.repository = repository;
    }

    public MyList<Person> fetchAllPersons() {
        return repository.findAll();
    }

    public void createPerson(Person p) {
        repository.save(p);
    }

    public void deletePerson(int index) {
        repository.delete(index);
    }

    public void editPerson(int index, Person updated) {
        repository.edit(index, updated);
    }
}
