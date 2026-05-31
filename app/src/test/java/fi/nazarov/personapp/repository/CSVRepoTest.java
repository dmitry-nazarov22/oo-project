package fi.nazarov.personapp.repository;

import fi.nazarov.personapp.datatype.Person;
import fi.nazarov.personapp.lists.MyLinkedList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class CSVRepoTest {
    @TempDir
    Path tempDir;

    private CSVRepo createRepo() {
        String file = tempDir.resolve("test.csv").toString();
        return new CSVRepo(new MyLinkedList<>(), file);
    }

    @Test
    void saveShouldAddPerson() {
        CSVRepo repo = createRepo();

        Person p = new Person("testfirst", "testlast", 123, "");

        repo.save(p);

        assertEquals(1, repo.findAll().size());
        assertEquals(p, repo.findAll().get(0));
    }

    @Test
    void findAllShouldNotBeNull() {
        CSVRepo repo = createRepo();

        assertNotNull(repo.findAll());
        assertEquals(0, repo.findAll().size());
    }

    @Test
    void deleteShouldRemovePerson() {
        CSVRepo repo = createRepo();

        Person p = new Person("fn", "ln", 1, "");
        repo.save(p);

        boolean result = repo.delete(0);
        assertTrue(result);
        assertEquals(0, repo.findAll().size());
    }

    @Test
    void invalidIndexShouldFailDelete() {
        CSVRepo repo = createRepo();

        boolean result = repo.delete(1000);
        assertFalse(result);
    }

    @Test
    void editShouldUpdatePerson() {
        CSVRepo repo = createRepo();

        Person old = new Person("fn", "ln", 1, "");
        repo.save(old);

        Person updated = new Person("updatedfn", "updatedln", 123, "updated@test.com");

        boolean result = repo.edit(0, updated);
        assertTrue(result);
        assertEquals(updated, repo.findAll().get(0));
    }

    @Test
    void invalidIndexShouldFailEdit() {
        CSVRepo repo = createRepo();

        Person updated = new Person("updatedfn", "updatedln", 123, "updated@test.com");

        boolean result = repo.edit(1000, updated);
        assertFalse(result);
    }
}
