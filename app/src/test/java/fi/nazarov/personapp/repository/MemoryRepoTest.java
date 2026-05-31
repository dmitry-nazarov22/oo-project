package fi.nazarov.personapp.repository;

import fi.nazarov.personapp.datatype.Person;
import fi.nazarov.personapp.lists.MyLinkedList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MemoryRepoTest {
    private MemoryRepo createRepo() {
        return new MemoryRepo(new MyLinkedList<>());
    }

    @Test
    void saveShouldAddPerson() {
        MemoryRepo repo = createRepo();

        Person p = new Person("testfirst", "testlast", 123, "");

        repo.save(p);

        assertEquals(1, repo.findAll().size());
        assertEquals(p, repo.findAll().get(0));
    }

    @Test
    void findAllShouldNotBeNull() {
        MemoryRepo repo = createRepo();

        assertNotNull(repo.findAll());
        assertEquals(0, repo.findAll().size());
    }

    @Test
    void deleteShouldRemovePerson() {
        MemoryRepo repo = createRepo();

        Person p = new Person("fn", "ln", 1, "");
        repo.save(p);

        boolean result = repo.delete(0);
        assertTrue(result);
        assertEquals(0, repo.findAll().size());
    }

    @Test
    void invalidIndexShouldFailDelete() {
        MemoryRepo repo = createRepo();

        boolean result = repo.delete(1000);
        assertFalse(result);
    }

    @Test
    void editShouldUpdatePerson() {
        MemoryRepo repo = createRepo();

        Person old = new Person("fn", "ln", 1, "");
        repo.save(old);

        Person updated = new Person("updatedfn", "updatedln", 123, "updated@test.com");

        boolean result = repo.edit(0, updated);
        assertTrue(result);
        assertEquals(updated, repo.findAll().get(0));
    }

    @Test
    void invalidIndexShouldFailEdit() {
        MemoryRepo repo = createRepo();

        Person updated = new Person("updatedfn", "updatedln", 123, "updated@test.com");

        boolean result = repo.edit(1000, updated);
        assertFalse(result);
    }
}
